package actions

import actions.conversion.temperature.TemperatureConversionAction
import discord4j.core.GatewayDiscordClient
import kotlinx.coroutines.reactor.mono
import reactor.core.publisher.Mono

object BotActionRegistry {
    private val botActions: List<BotAction> = listOf(
        TemperatureConversionAction()
    )

    fun executeBotActions(client: GatewayDiscordClient): Mono<Unit> = mono {
        botActions.forEach { it.execute(client) }
    }
}
