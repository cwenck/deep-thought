package actions

import actions.conversion.temperature.TemperatureConversionAction
import discord4j.core.GatewayDiscordClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.whenComplete

object BotActionRegistry {
    private val botActions: List<BotAction> = listOf(
        TemperatureConversionAction()
    )

    fun executeBotActions(client: GatewayDiscordClient): Mono<Void> {
        return botActions.map { it.execute(client) }.whenComplete()
    }
}
