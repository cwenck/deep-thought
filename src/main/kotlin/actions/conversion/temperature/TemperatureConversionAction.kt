package actions.conversion.temperature

import actions.EventHandler
import actions.conversion.temperature.units.Temperature
import actions.conversion.temperature.units.TemperatureUnit
import actions.filtering.filters.CompositeFilterBuilder
import actions.filtering.filters.message.MessageNotFromBotFilter
import core.BotContext
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.whenComplete
import roles.Role
import roles.RoleGroup
import java.math.BigDecimal

class TemperatureConversionAction : EventHandler<MessageCreateEvent> {
    private val temperatureGroupName = "temp"
    private val unitGroupName = "unit"

    private val degreesWithUnitRegex: Regex =
        Regex("(?i:(?<![_0-9a-z.])(?<temp>[-+]?\\d+(?:\\.\\d+)?)\\s*(?:°|deg|degrees?)?\\s*(?<unit>[fck]|fahrenheit|celsius|kelvin)(?![_0-9a-z]))+")
    private val degreesWithoutUnitsRegex: Regex =
        Regex("(?i:(?<![_0-9a-z.])(?<temp>[-+]?\\d+(?:\\.\\d+)?)\\s*(?:°|deg|degrees?)(?![_0-9a-z]))+")

    private val roleToDefaultUnitMap: Map<Role, TemperatureUnit> = mapOf(
        Role.UNITS_METRIC to TemperatureUnit.CELSIUS,
        Role.UNITS_IMPERIAL to TemperatureUnit.FAHRENHEIT,
    )

    override val eventType: Class<MessageCreateEvent>
        get() = MessageCreateEvent::class.java

    override fun filterEvent(event: MessageCreateEvent, botContext: BotContext): Boolean =
        CompositeFilterBuilder<Message>()
            .addFilter(MessageNotFromBotFilter())
            .apply(event.message)

    override fun handleEvent(event: MessageCreateEvent, botContext: BotContext): Mono<Void> {
        val temperaturesWithUnits: List<Temperature> = extractTemperaturesWithUnits(event.message.content)

        val member = event.member.orElse(null)
        val temperaturesWithoutUnits: List<Temperature> = member?.let {
            extractTemperaturesWithoutUnits(event.message.content, it, botContext)
        } ?: emptyList()

        val temperatures = sequenceOf(temperaturesWithUnits, temperaturesWithoutUnits)
            .flatMap { it.asSequence() }
            .distinct()
            .take(4)
            .toList()

        if (temperatures.isEmpty()) {
            return Mono.empty()
        }

        return event.message.channel.flatMap { channel ->
            temperatures.asIterable().map {
                channel.createEmbed { reply ->
                    reply.addField("Fahrenheit", formatDecimal(it.fahrenheit), true)
                    reply.addField("Celsius", formatDecimal(it.celsius), true)
                    reply.addField("Kelvin", formatDecimal(it.kelvin), true)
                }.onErrorResume {
                    Mono.empty()
                }
            }.whenComplete()
        }
    }

    private fun formatDecimal(value: BigDecimal): String = "%.2f°".format(value)

    private fun extractTemperaturesWithoutUnits(
        messageContent: String,
        member: Member,
        botContext: BotContext,
    ): List<Temperature> {
        val role = botContext.roleRegistry.memberRoleFromRoleGroup(RoleGroup.UNIT_CONVERSION, member.roleIds)
        val unit = roleToDefaultUnitMap[role] ?: return emptyList()

        val matches = degreesWithoutUnitsRegex.findAll(messageContent)
        return matches.map {
            val value = it.groups[temperatureGroupName]?.value?.toBigDecimalOrNull() ?: return@map null
            Temperature.of(value, unit)
        }.filterNotNull().toList()
    }

    private fun extractTemperaturesWithUnits(messageContent: String): List<Temperature> {
        val matches = degreesWithUnitRegex.findAll(messageContent)
        return matches.map {
            val value = it.groups[temperatureGroupName]?.value?.toBigDecimalOrNull() ?: return@map null
            val unitName = it.groups[unitGroupName]?.value ?: return@map null
            val unit = TemperatureUnit.fromUnitName(unitName) ?: return@map null

            Temperature.of(value, unit)
        }.filterNotNull().toList()
    }
}
