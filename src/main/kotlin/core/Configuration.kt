package core

import discord4j.common.util.Snowflake

object Configuration {
    val BOT_TOKEN = "BOT_TOKEN"
    val UNIT_CONVERSION_METRIC_ROLE_ID = "UNIT_CONVERSION_METRIC_ROLE_ID"
    val UNIT_CONVERSION_IMPERIAL_ROLE_ID = "UNIT_CONVERSION_IMPERIAL_ROLE_ID"

    val botToken: String?
        get() = System.getenv(BOT_TOKEN)

    val unitConversionMetricRoleId: Snowflake?
            get() {
                val id = System.getenv(UNIT_CONVERSION_METRIC_ROLE_ID).toLongOrNull() ?: return null
                return Snowflake.of(id)
            }

    val unitConversionImperialRoleId: Snowflake?
        get() {
            val id = System.getenv(UNIT_CONVERSION_IMPERIAL_ROLE_ID).toLongOrNull() ?: return null
            return Snowflake.of(id)
        }
}
