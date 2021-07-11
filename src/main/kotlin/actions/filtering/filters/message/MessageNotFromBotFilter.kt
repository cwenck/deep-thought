package actions.filtering.filters.message

import actions.filtering.MessageFilteringUtils.sentByBot
import actions.filtering.filters.Filter
import discord4j.core.`object`.entity.Message

class MessageNotFromBotFilter : Filter<Message> {
    override fun accept(value: Message): Boolean = !value.sentByBot()
}
