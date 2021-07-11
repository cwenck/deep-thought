package roles

import core.RoleId

class RoleRegistry private constructor(private val roles: Map<Role, RoleId>){

    fun hasRole(role: Role, roleIds: Set<RoleId>) : Boolean {
        val roleId = roles[role]
        return roleIds.contains(roleId)
    }

    fun hasRoleFromGroup(roleGroup: RoleGroup, roleIds: Set<RoleId>): Boolean {
        val roles = Role.rolesForGroup(roleGroup)
        return roles.map { hasRole(it, roleIds) }.reduceOrNull(Boolean::or) ?: false
    }

    fun memberRoleFromRoleGroup(roleGroup: RoleGroup, roleIds: Set<RoleId>): Role? {
        val roles = Role.rolesForGroup(roleGroup)

        var matchedRole: Role? = null
        for (role in roles) {
            if (hasRole(role, roleIds)) {
                if (matchedRole == null) {
                    matchedRole = role
                } else {
                    // User has multiple roles from the same role group
                    // TODO : log a warning
                    return null
                }
            }
        }

        return matchedRole
    }

    object Builder {
        private val roles: MutableMap<Role, RoleId> = mutableMapOf()

        fun register(role: Role, roleId: RoleId): Builder {
            roles[role] = roleId
            return this
        }

        fun build(): RoleRegistry = RoleRegistry(roles)
    }
}
