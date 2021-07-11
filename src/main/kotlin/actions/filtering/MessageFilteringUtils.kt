package actions.filtering

import discord4j.core.`object`.entity.Message
import discord4j.core.`object`.entity.User

object MessageFilteringUtils {

    fun Message.sentByBot(): Boolean {
        return this.author.map(User::isBot).orElse(false)
    }
}
