package com.example.calculatorapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorapp.calculator_algorithm.MathCalculator

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val calculator: MathCalculator) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return MainViewModel(calculator) as T
    }
}