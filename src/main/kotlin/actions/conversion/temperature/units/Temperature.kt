package actions.conversion.temperature.units

import actions.conversion.temperature.units.TemperatureUnit.*
import java.math.BigDecimal

class Temperature private constructor(temperature: BigDecimal, unit: TemperatureUnit) {
    val celsius: BigDecimal = when (unit) {
        CELSIUS -> {
            temperature
        }
        FAHRENHEIT -> {
            TemperatureConverter.fahrenheitToCelsius(temperature)
        }
        KELVIN -> {
            TemperatureConverter.kelvinToCelsius(temperature)
        }
    }

    val fahrenheit: BigDecimal = when (unit) {
        CELSIUS -> {
            TemperatureConverter.celsiusToFahrenheit(temperature)
        }
        FAHRENHEIT -> {
            temperature
        }
        KELVIN -> {
            TemperatureConverter.kelvinToFahrenheit(temperature)
        }
    }

    val kelvin: BigDecimal = when (unit) {
        CELSIUS -> {
            TemperatureConverter.celsiusToKelvin(temperature)
        }
        FAHRENHEIT -> {
            TemperatureConverter.fahrenheitToKelvin(temperature)
        }
        KELVIN -> {
            temperature
        }
    }

    companion object {
        fun ofKelvin(temperature: BigDecimal): Temperature? =
            withinRange(Temperature(temperature, KELVIN))

        fun ofCelsius(temperature: BigDecimal): Temperature? =
            withinRange(Temperature(temperature, CELSIUS))

        fun ofFahrenheit(temperature: BigDecimal): Temperature? =
            withinRange(Temperature(temperature, FAHRENHEIT))

        fun of(temperature: BigDecimal, unit: TemperatureUnit): Temperature? =
            withinRange(Temperature(temperature, unit))

        private fun withinRange(temperature: Temperature): Temperature? {
            val absoluteTemperature = temperature.kelvin;
            return if (absoluteTemperature >= BigDecimal.ZERO) {
                temperature
            } else {
                null
            }
        }
    }
}
