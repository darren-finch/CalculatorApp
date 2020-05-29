package com.example.calculatorapp

import com.example.calculatorapp.calculator_algorithm.MathCalculator
import com.example.calculatorapp.viewmodel.MainViewModel
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

class MainViewModelTest
{
//    @Rule
//    @JvmField
//    val instantExecutorRule = InstantTaskExecutorRule()

    //region Constants -----------------------------------------------------------------------------

    private val RETURN_VALUE = 5.0

    //endregion Constants --------------------------------------------------------------------------

    //region Helper Fields -------------------------------------------------------------------------
    private val calculator = mockk<MathCalculator>()

    //endregion Helper Fields ----------------------------------------------------------------------

    private lateinit var SUT: MainViewModel

    //region Set up / Tear down
    @Before
    fun setup()
    {
        SUT = MainViewModel(calculator)
    }
    //endregion Set up / Tear down

    //region Tests ---------------------------------------------------------------------------------

    private val TEST_EXPRESSION_RESULT = 5.0

    //calculateExpression - returns result of math expression
    @Test
    fun calculateResultOfExpression_returnsResultOfMathExpression()
    {
        //Arrange
        success()
        //Act
        val result = SUT.calculateResultOfEquation()
        //Assert
        assertThat(result, `is`(TEST_EXPRESSION_RESULT))
    }

    //calculateResultOfExpression - sets alert for toast message to appear if an error occurs when parsing the equation
//    @Test
//    fun calculateResultOfExpression_divideByZeroError_displayToastIsTrue()
//    {
//        //Arrange
//        divideByZeroError()
//        //Act
//        SUT.calculateResultOfExpression()
//        SUT.getError().observeForever {  }
//        //Assert
//        assertThat(SUT.getError().value, `is`("Cannot divide by zero!"))
//    }
//
    private fun success()
    {
        every { calculator.calculateResult("") }.returns(TEST_EXPRESSION_RESULT)
    }
//
//    private fun divideByZeroError()
//    {
//        every { expression.calculateResult("") }.throws(ArithmeticException("Cannot divide by zero!"))
//    }

    //endregion Tests ------------------------------------------------------------------------------

    //region Helper Classes ------------------------------------------------------------------------
//    private class MathExpressionHelper(private val returnValue: Double) : MathExpression
//    {
//        var timesInvoked = 0
//        override fun calculateResult(expression: String): Double
//        {
//            timesInvoked++
//            return returnValue
//        }
//    }
    //endregion Helper Classes ---------------------------------------------------------------------

    //region Helper Methods ------------------------------------------------------------------------

    //endregion Helper Methods ---------------------------------------------------------------------
}