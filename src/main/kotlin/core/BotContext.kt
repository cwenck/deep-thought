package core

import discord4j.core.GatewayDiscordClient
import roles.RoleRegistry

data class BotContext(
    val client: GatewayDiscordClient,
    val userRolesCache: UserRolesCache,
    val roleRegistry: RoleRegistry,
)
