package actions.conversion.temperature.units

import actions.conversion.temperature.units.TemperatureUnit.*
import java.math.BigDecimal

class Temperature private constructor(temperature: BigDecimal, unit: TemperatureUnit) {
    private val temperatureCelsius: BigDecimal = when(unit) {
        CELSIUS -> {
            temperature
        }
        FAHRENHEIT -> {
            (temperature - CELSIUS_FAHRENHEIT_OFFSET) / CELSIUS_FAHRENHEIT_MULTIPLIER
        }
        KELVIN -> {
            temperature - CELSIUS_KELVIN_OFFSET
        }
    }

    fun asCelsius(): BigDecimal = temperatureCelsius
    fun asFahrenheit(): BigDecimal = (temperatureCelsius * CELSIUS_FAHRENHEIT_MULTIPLIER) + CELSIUS_FAHRENHEIT_OFFSET
    fun asKelvin(): BigDecimal = temperatureCelsius + CELSIUS_KELVIN_OFFSET

    companion object {
        private val CELSIUS_FAHRENHEIT_MULTIPLIER = BigDecimal("1.8")
        private val CELSIUS_FAHRENHEIT_OFFSET = BigDecimal("32.0")
        private val CELSIUS_KELVIN_OFFSET = BigDecimal("273.15")

        fun ofKelvin(temperature: BigDecimal): Temperature? =
            withinRange(Temperature(temperature, KELVIN))
        fun ofCelsius(temperature: BigDecimal): Temperature? =
            withinRange(Temperature(temperature, CELSIUS))
        fun ofFahrenheit(temperature: BigDecimal): Temperature? =
            withinRange(Temperature(temperature, FAHRENHEIT))
        fun of(temperature: BigDecimal, unit: TemperatureUnit): Temperature? =
            withinRange(Temperature(temperature, unit))

        private fun withinRange(temperature: Temperature): Temperature? {
            val absoluteTemperature = temperature.asKelvin();
            return if (absoluteTemperature >= BigDecimal.ZERO) {
                temperature
            } else {
                null
            }
        }
    }
}
