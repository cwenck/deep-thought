package actions.conversion.temperature.units

import java.math.BigDecimal

object TemperatureConverter {
    private val CELSIUS_FAHRENHEIT_MULTIPLIER = BigDecimal("1.8")
    private val CELSIUS_FAHRENHEIT_OFFSET = BigDecimal("32.0")
    private val CELSIUS_KELVIN_OFFSET = BigDecimal("273.15")

    fun celsiusToFahrenheit(celsius: BigDecimal): BigDecimal = (celsius * CELSIUS_FAHRENHEIT_MULTIPLIER) + CELSIUS_FAHRENHEIT_OFFSET
    fun fahrenheitToCelsius(fahrenheit: BigDecimal): BigDecimal = (fahrenheit - CELSIUS_FAHRENHEIT_OFFSET) / CELSIUS_FAHRENHEIT_MULTIPLIER
    fun celsiusToKelvin(celsius: BigDecimal): BigDecimal = celsius + CELSIUS_KELVIN_OFFSET
    fun kelvinToCelsius(kelvin: BigDecimal): BigDecimal = kelvin - CELSIUS_KELVIN_OFFSET
    fun fahrenheitToKelvin(fahrenheit: BigDecimal): BigDecimal = celsiusToKelvin(fahrenheitToCelsius(fahrenheit))
    fun kelvinToFahrenheit(kelvin: BigDecimal): BigDecimal = celsiusToFahrenheit(kelvinToCelsius(kelvin))
}
