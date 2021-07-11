package actions

import discord4j.core.GatewayDiscordClient

interface BotAction {
    suspend fun execute(client: GatewayDiscordClient);
}
