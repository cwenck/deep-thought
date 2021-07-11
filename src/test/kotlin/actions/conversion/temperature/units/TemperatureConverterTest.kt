package actions.conversion.temperature.units

import org.testng.annotations.DataProvider
import org.testng.annotations.Test
import java.math.BigDecimal
import kotlin.test.assertTrue

class TemperatureConverterTest {

    @DataProvider(parallel = true)
    fun celsiusToKelvinData(): Iterator<Array<Any>> {
        return listOf<Array<Any>>(
            arrayOf(BigDecimal.ZERO, BigDecimal("273.15")),
            arrayOf(BigDecimal("-273.15"), BigDecimal.ZERO),
            arrayOf(BigDecimal("100"), BigDecimal("373.15"))
        ).iterator()
    }

    @Test(groups = ["unit"], dataProvider = "celsiusToKelvinData")
    fun testCelsiusToKelvin(input: BigDecimal, expectedOutput: BigDecimal) {
        val actualOutput = TemperatureConverter.celsiusToKelvin(input)
        assertBigDecimalEquals(expectedOutput, actualOutput)
    }

    @DataProvider(parallel = true)
    fun kelvinToCelsiusData(): Iterator<Array<Any>> {
        return listOf<Array<Any>>(
            arrayOf(BigDecimal.ZERO, BigDecimal("-273.15")),
            arrayOf(BigDecimal("273.15"), BigDecimal.ZERO),
            arrayOf(BigDecimal("373.15"), BigDecimal("100"))
        ).iterator()
    }

    @Test(groups = ["unit"], dataProvider = "kelvinToCelsiusData")
    fun testKelvinToCelsius(input: BigDecimal, expectedOutput: BigDecimal) {
        val actualOutput = TemperatureConverter.kelvinToCelsius(input)
        assertBigDecimalEquals(expectedOutput, actualOutput)
    }

    @DataProvider(parallel = true)
    fun celsiusToFahrenheitData(): Iterator<Array<Any>> {
        return listOf<Array<Any>>(
            arrayOf(BigDecimal.ZERO, BigDecimal("32")),
            arrayOf(BigDecimal("100"), BigDecimal("212")),
        ).iterator()
    }

    @Test(groups = ["unit"], dataProvider = "celsiusToFahrenheitData")
    fun testCelsiusToFahrenheit(input: BigDecimal, expectedOutput: BigDecimal) {
        val actualOutput = TemperatureConverter.celsiusToFahrenheit(input)
        assertBigDecimalEquals(expectedOutput, actualOutput)
    }

    @DataProvider(parallel = true)
    fun fahrenheitToCelsiusData(): Iterator<Array<Any>> {
        return listOf<Array<Any>>(
            arrayOf(BigDecimal("32"), BigDecimal.ZERO),
            arrayOf(BigDecimal("212"), BigDecimal("100")),
        ).iterator()
    }

    @Test(groups = ["unit"], dataProvider = "fahrenheitToCelsiusData")
    fun testFahrenheitToCelsius(input: BigDecimal, expectedOutput: BigDecimal) {
        val actualOutput = TemperatureConverter.fahrenheitToCelsius(input)
        assertBigDecimalEquals(expectedOutput, actualOutput)
    }

    private fun assertBigDecimalEquals(expected: BigDecimal, actual: BigDecimal) {
        assertTrue(expected.compareTo(actual) == 0, "expected [$expected] but found [$actual]")
    }
}
