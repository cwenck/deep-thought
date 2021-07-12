package core

import discord4j.common.util.Snowflake
import discord4j.core.GatewayDiscordClient
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

typealias UserId = Snowflake
typealias RoleId = Snowflake
typealias GuildId = Snowflake

class UserRolesCache(private val client: GatewayDiscordClient) {
    private val roleCache = ConcurrentHashMap<MemberId, Set<RoleId>>()

    fun updateUserRoles(guildId: GuildId, userId: UserId, roleIds: Set<RoleId>): Mono<Void> {
        roleCache[MemberId(guildId, userId)] = roleIds
        return Mono.empty()
    }

    fun userRoles(guildId: GuildId, userId: UserId): Mono<Set<RoleId>> {
        val memberId = MemberId(guildId, userId)
        val cachedMemberRoles = roleCache[memberId]
        if (cachedMemberRoles != null) {
            return Mono.just(cachedMemberRoles)
        }

        return client.getMemberById(guildId, userId)
            .map { member ->
                roleCache[memberId] = member.roleIds
                member.roleIds
            }
    }

    private data class MemberId(val guildId: GuildId, val userId: UserId)
}
