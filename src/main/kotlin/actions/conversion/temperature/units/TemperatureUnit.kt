package actions.conversion.temperature.units

enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    companion object {
        private val unitNameLookup: List<UnitName> = listOf(
            UnitName(CELSIUS, Regex("(?i:c|celsius)")),
            UnitName(FAHRENHEIT, Regex("(?i:f|fahrenheit)")),
            UnitName(KELVIN, Regex("(?i:k|kelvin)"))
        )

        fun fromUnitName(name: String): TemperatureUnit? {
           return unitNameLookup.filter {
               it.regex.containsMatchIn(name)
           }.map(UnitName::unit).firstOrNull()
        }
    }

    data class UnitName(val unit: TemperatureUnit, val regex: Regex)
}
