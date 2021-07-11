package actions

import core.BotContext
import discord4j.core.event.domain.Event
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

interface EventHandler<E : Event> : BotAction {
    val eventType: Class<E>

    override fun execute(botContext: BotContext): Publisher<Any> {
        return botContext.client.on(eventType)
            .filter { event -> filterEvent(event, botContext) }
            .flatMap { event -> handleEvent(event, botContext) }
    }

    fun filterEvent(event: E, botContext: BotContext): Boolean {
        // By default, allow all events
        return true;
    }

    fun handleEvent(event: E, botContext: BotContext): Mono<Void>
}
