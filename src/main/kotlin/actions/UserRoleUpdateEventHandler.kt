package actions

import core.BotContext
import discord4j.core.event.domain.guild.MemberUpdateEvent
import reactor.core.publisher.Mono

class UserRoleUpdateEventHandler: EventHandler<MemberUpdateEvent> {
    override val eventType: Class<MemberUpdateEvent>
        get() = MemberUpdateEvent::class.java

    override fun handleEvent(event: MemberUpdateEvent, botContext: BotContext): Mono<Void> =
        botContext.userRolesCache.updateUserRoles(event.guildId, event.memberId, event.currentRoles)
}
