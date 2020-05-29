package com.example.calculatorapp.calculator_algorithm

import java.lang.Exception

/*
* This class stores and calculates a simple math expression/equation (no exponents or parentheses).
* All characters in the expression must be valid, or the behavior of this class cannot be guaranteed.
* */
class MathCalculator
{
    //Test equation: -5 + 6 + 5 × 9 ÷ 8
    //Remove whitespace: -5+6+5×9÷8
    //Separate terms by (+) or (-): ['-5', '+6', '+5×9÷8']
    //Remove addition signs: ['-5', '6', '5x9÷8']
    //Get result of each term: ['-5', '6', '5.625']
    //Sum up the results of each term: 6.625

    private fun getTermsFromEquation(equation: String) : List<MathTerm>
    {
        val equationWithNoWhitespace = removeWhitespaceFromEquation(equation)

        val mathTerms = mutableListOf<MathTerm>()
        val stringTerms = equationWithNoWhitespace.split(Regex("(?=[+-])"))
        for(stringTerm in stringTerms)
            mathTerms.add(MathTerm(stringTerm))

        println("Term array = $stringTerms")
        println("Term array results = ${mathTerms.map { it.calculateResult() }}")

        return mathTerms
    }

    private fun removeWhitespaceFromEquation(equation: String) : String
    {
        println("Removing whitespace from $equation = ${equation.replace(" ", "")}")
        return equation.replace(" ", "")
    }

    fun calculateResult(equation: String) : Double
    {
        if(equation.isEmpty()) return 0.0

        var sum = 0.0
        val terms = getTermsFromEquation(equation)
        try
        {
            for(term in terms)
                sum += term.calculateResult()
        }
        catch (e: Exception)
        {
            throw e
        }

        return sum
    }
}