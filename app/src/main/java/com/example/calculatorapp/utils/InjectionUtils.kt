package com.example.calculatorapp.utils

import com.example.calculatorapp.calculator_algorithm.MathCalculator

//Doing this because I'm too lazy to get Dagger 2
object InjectionUtils
{
    private val calculator = MathCalculator()

    fun provideCalculator() = calculator
}