package actions.conversion.temperature

import actions.EventHandler
import actions.conversion.temperature.units.Temperature
import actions.conversion.temperature.units.TemperatureUnit
import actions.filtering.filters.CompositeFilterBuilder
import actions.filtering.filters.message.MessageNotFromBotFilter
import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.reactive.asFlow
import reactor.core.publisher.Mono
import java.math.BigDecimal

class TemperatureConversionAction : EventHandler<MessageCreateEvent> {
    private val temperatureGroupName = "temp"
    private val unitGroupName = "unit"
    private val degreesWithUnitRegex: Regex = Regex("(?i:(?<![_0-9a-z.])(?<temp>[-+]?\\d+(?:\\.\\d+)?)\\s*(?:°|deg|degrees?)?\\s*(?<unit>[fck]|fahrenheit|celsius|kelvin)(?![_0-9a-z]))+");

    override val eventType: Class<MessageCreateEvent>
        get() = MessageCreateEvent::class.java

    override suspend fun filterEvent(event: MessageCreateEvent): Boolean = CompositeFilterBuilder<Message>()
        .addFilter(MessageNotFromBotFilter())
        .apply(event.message)

    override suspend fun handleEvent(event: MessageCreateEvent) {
        val temperatures: List<Temperature> = extractTemperatures(event.message.content)
        if (temperatures.isEmpty()) {
            return
        }

        for (temperature in temperatures) {
            event.message.channel.asFlow().collect { channel ->
                channel.createEmbed { reply ->
                    reply.addField("Fahrenheit", formatDecimal(temperature.asFahrenheit()), true)
                    reply.addField("Celsius", formatDecimal(temperature.asCelsius()), true)
                    reply.addField("Kelvin", formatDecimal(temperature.asKelvin()), true)
                }.onErrorResume {
                    Mono.empty()
                }.asFlow().collect()
            }
        }
    }

    private fun formatDecimal(value: BigDecimal): String = "%.2f°".format(value)

    private fun extractTemperatures(messageContent: String): List<Temperature> {
        val matches = degreesWithUnitRegex.findAll(messageContent)
        return matches.map {
            val value = it.groups[temperatureGroupName]?.value?.toBigDecimalOrNull() ?: return@map null
            val unitName = it.groups[unitGroupName]?.value ?: return@map null
            val unit = TemperatureUnit.fromUnitName(unitName) ?: return@map null

            Temperature.of(value, unit)
        }.filterNotNull().toList()
    }
}
