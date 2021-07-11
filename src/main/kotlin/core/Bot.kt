package core

import actions.BotActionRegistry
import discord4j.core.DiscordClient
import roles.Role
import roles.RoleRegistry
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val botToken = Configuration.botToken ?: stop("The ${Configuration.BOT_TOKEN} environment variable must be set")
    val roleRegistry = initRoleRegistry()

    DiscordClient.create(botToken)
        .withGateway {
            val botContext = BotContext(it, UserRolesCache(it), roleRegistry)
            BotActionRegistry.executeBotActions(botContext)
        }
        .block()
}

private fun initRoleRegistry(): RoleRegistry {
    val unitConversionImperialRoleId = Configuration.unitConversionImperialRoleId
        ?: stop("The ${Configuration.UNIT_CONVERSION_IMPERIAL_ROLE_ID} environment variable must be set")
    val unitConversionMetricRoleId = Configuration.unitConversionMetricRoleId
        ?: stop("The ${Configuration.UNIT_CONVERSION_METRIC_ROLE_ID} environment variable must be set")

    return RoleRegistry.Builder
        .register(Role.UNITS_IMPERIAL, unitConversionImperialRoleId)
        .register(Role.UNITS_METRIC, unitConversionMetricRoleId)
        .build()
}

fun stop(message: String): Nothing {
    System.err.println(message)
    exitProcess(1)
}
