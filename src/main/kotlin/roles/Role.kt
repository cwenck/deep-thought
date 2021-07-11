package roles

enum class Role(val group: RoleGroup) {
    UNITS_IMPERIAL(RoleGroup.UNIT_CONVERSION),
    UNITS_METRIC(RoleGroup.UNIT_CONVERSION);

    companion object {
        private val ROLES_PER_GROUP: Map<RoleGroup, Set<Role>> =
            Role.values().asSequence().groupBy(Role::group).mapValues { it.value.toSet() }

        fun rolesForGroup(group: RoleGroup): Set<Role> = ROLES_PER_GROUP[group] ?: setOf()
    }
}
