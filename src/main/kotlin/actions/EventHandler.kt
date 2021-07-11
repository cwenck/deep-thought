package actions

import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.reactive.asFlow

interface EventHandler<E: Event> : BotAction {
    val eventType: Class<E>

    override suspend fun execute(client: GatewayDiscordClient) {
        client.on(eventType)
            .asFlow()
            .filter(::filterEvent)
            .collect(::handleEvent)
    }

    suspend fun filterEvent(event: E): Boolean {
        // By default, allow all events
        return true;
    }

    suspend fun handleEvent(event: E)
}
