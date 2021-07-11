package actions

import discord4j.core.GatewayDiscordClient
import org.reactivestreams.Publisher

interface BotAction {
    fun execute(client: GatewayDiscordClient): Publisher<Any>
}
