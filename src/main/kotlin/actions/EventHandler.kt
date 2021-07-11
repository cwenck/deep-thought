package actions

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.Event
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

interface EventHandler<E: Event> : BotAction {
    val eventType: Class<E>

    override fun execute(client: GatewayDiscordClient): Publisher<Any> {
        return client.on(eventType)
            .filter(::filterEvent)
            .flatMap(::handleEvent)
    }

    fun filterEvent(event: E): Boolean {
        // By default, allow all events
        return true;
    }

    fun handleEvent(event: E) : Mono<Void>
}
