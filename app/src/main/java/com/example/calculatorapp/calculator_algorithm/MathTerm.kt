package com.example.calculatorapp.calculator_algorithm

import java.lang.ArithmeticException
import java.lang.Double.parseDouble
import java.lang.Exception
import java.lang.NumberFormatException

class MathTerm(private val term: String)
{
    private val termNumbersAndOperations: List<String> by lazy {
        splitTermIntoNumbersAndOperations()
    }

    private fun splitTermIntoNumbersAndOperations() : List<String>
    {
        if(term.isEmpty()) return emptyList()
        val termString = removePlusSignFromTerm()
        return termString.split(Regex("(?<=[*/])|(?=[*/])"))
    }

    private fun removePlusSignFromTerm() : String
    {
        return term.replaceFirst("+", "")
    }

    fun calculateResult() : Double
    {
        if(termNumbersAndOperations.isEmpty()) return 0.0
        var currentResult = 1.0
        for (i in termNumbersAndOperations.indices)
        {
            var currentNumber: Double

            try
            {
                currentNumber = parseDouble(termNumbersAndOperations[i])
                val currentOperation = if(i - 1 >= 0) termNumbersAndOperations[i - 1] else ""
                currentResult = doOperation(currentOperation, currentResult, currentNumber)
            }
            //There are two mainly possible exceptions. NumberFormatException and DivideByZeroException.
            catch (e: ArithmeticException)
            {
                throw e
            }
            //A NumberFormatException probably means we hit an operational sign (* or /)
            catch (e: NumberFormatException)
            {
                continue
            }
            //If we couldn't figure out the exception was, that's not good. Just return 0.
            catch (e: Exception)
            {
                return 0.0
            }
        }
        return currentResult
    }
    private fun doOperation(operation: String, currentResult: Double, currentNumber: Double) : Double
    {
        return when (operation)
        {
            "*" -> currentResult * currentNumber
            "/" -> currentResult / if(currentNumber != 0.0) currentNumber else throw ArithmeticException("Cannot divide by zero!")
            else -> currentNumber
        }
    }
}