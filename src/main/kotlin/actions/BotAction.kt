package actions

import core.BotContext
import org.reactivestreams.Publisher

interface BotAction {
    fun execute(botContext: BotContext): Publisher<Any>
}
