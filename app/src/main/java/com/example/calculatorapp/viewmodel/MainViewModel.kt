package com.example.calculatorapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculatorapp.calculator_algorithm.MathCalculator

class MainViewModel(private val calculator: MathCalculator) : ViewModel()
{
    private val error = MutableLiveData<String>()
    private var equation = ""
    fun calculateResultOfEquation() : Double
    {
        return try
        {
            calculator.calculateResult(equation)
        }
        catch (e: Exception)
        {
            error.value = e.message
            0.0
        }
    }
    fun getError() = error as LiveData<String>
    fun setEquation(equation: String)
    {
        this.equation = equation
    }
}