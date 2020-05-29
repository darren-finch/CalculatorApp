package com.example.calculatorapp

import android.os.Bundle
import android.text.InputType.TYPE_NULL
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorapp.utils.InjectionUtils
import com.example.calculatorapp.viewmodel.MainViewModel
import com.example.calculatorapp.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity()
{
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(InjectionUtils.provideCalculator())).get(MainViewModel::class.java)
    }
    //Don't access before onViewCreated.
    private val currentEquation: String
        get()
        {
            return if(equationTextInputEditText != null)
                equationTextInputEditText.text.toString()
            else
                ""
        }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getError().observe(this, Observer { errorMsg ->
            showToast(errorMsg)
        })
    }
    override fun onStart()
    {
        super.onStart()
        disableTextInputs()
        plusButton.setOnClickListener{
            appendOperationToEquation("+")
        }
        subtractButton.setOnClickListener{
            appendOperationToEquation("-")
        }
        multiplyButton.setOnClickListener{
            appendOperationToEquation("*")
        }
        divideButton.setOnClickListener{
            appendOperationToEquation("/")
        }
        decimalButton.setOnClickListener{
            appendDecimalToEquation()
        }
        equalsButton.setOnClickListener{
            displayEquationResult()
        }
    }
    private fun disableTextInputs()
    {
        equationTextInputEditText.isFocusable = true
        equationTextInputEditText.isFocusableInTouchMode = true
        equationTextInputEditText.inputType = TYPE_NULL
    }
    fun inputNumber(view: View)
    {
        val number = view.tag.toString()
        appendNumberToEquation(number)
    }
    private fun appendNumberToEquation(number: String)
    {
        var potentialWhitespace = ""
        if(currentEquation.isNotEmpty() && lastCharInEquationIsAnOperation())
        {
            potentialWhitespace = " "
        }
        equationTextInputEditText.append("$potentialWhitespace$number")
    }
    private fun appendOperationToEquation(operation: String)
    {
        if(currentEquation.isNotEmpty() && isOperation(operation) && !lastCharInEquationIsAnOperation())
            equationTextInputEditText.append(" $operation")
    }
    private fun appendDecimalToEquation()
    {
        if(currentEquation.isNotEmpty() && !lastCharInEquationIsAnOperation())
            equationTextInputEditText.append(".")
    }
    private fun lastCharInEquationIsAnOperation() : Boolean
    {
        return if(currentEquation.isNotEmpty())
            isOperation(currentEquation.last().toString())
        else
            false
    }
    private fun isOperation(potentialOperation: String) : Boolean
    {
        return when(potentialOperation)
        {
            "+" -> true
            "-" -> true
            "*" -> true
            "/" -> true
            else -> false
        }
    }
    private fun displayEquationResult()
    {
        if(currentEquation.isNotEmpty() && !lastCharInEquationIsAnOperation())
            showToast("$currentEquation = ${calculateEquationResult()}", false)
        else
            showToast("Finish the unclosed operation in your equation.")
    }
    private fun calculateEquationResult() : Double
    {
        viewModel.setEquation(currentEquation)
        return viewModel.calculateResultOfEquation()
    }
    private fun showToast(msg: String, shortDisplayLength: Boolean = true)
    {
        Toast.makeText(this, msg, if(shortDisplayLength) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
    }
}

