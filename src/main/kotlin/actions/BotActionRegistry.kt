package actions

import actions.conversion.temperature.TemperatureConversionAction
import core.BotContext
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.whenComplete

object BotActionRegistry {
    private val botActions: List<BotAction> = listOf(
        TemperatureConversionAction(),
        UserRoleUpdateEventHandler(),
    )

    fun executeBotActions(botContext: BotContext): Mono<Void> {
        return botActions.map { it.execute(botContext) }.whenComplete()
    }
}
