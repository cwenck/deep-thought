package core

import actions.BotActionRegistry
import discord4j.core.DiscordClient
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val botToken = Configuration.botToken ?: stop("The BOT_TOKEN environment variable must be set")
    DiscordClient.create(botToken)
        .withGateway(BotActionRegistry::executeBotActions)
        .block()
}

fun stop(message: String): Nothing {
    System.err.println(message)
    exitProcess(1)
}
