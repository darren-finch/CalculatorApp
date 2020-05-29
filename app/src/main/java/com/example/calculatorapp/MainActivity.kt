package com.example.calculatorapp

import android.os.Bundle
import android.text.Editable
import android.text.InputType.TYPE_NULL
import android.text.TextWatcher
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
            return viewModel.equation
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
        setupTextInputs()
        setupButtons()
    }
    private fun setupTextInputs()
    {
        equationTextInputEditText.isFocusable = true
        equationTextInputEditText.isFocusableInTouchMode = true
        equationTextInputEditText.inputType = TYPE_NULL
        equationTextInputEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                if(!viewModel.isShowingEquationResult)
                    viewModel.equation = s.toString()
            }
        })
    }
    private fun setupButtons()
    {
        clearButton.setOnClickListener{
            clearEquation()
        }
        backspaceButton.setOnClickListener{
            backspace()
        }
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
    fun inputNumber(view: View)
    {
        val number = view.tag.toString()
        appendNumberToEquation(number)
    }
    private fun clearEquation()
    {
        setEquationText("")
        viewModel.isShowingEquationResult = false
    }
    private fun backspace()
    {
        if(currentEquation.isNotEmpty())
        {
            if(viewModel.isShowingEquationResult)
                viewModel.isShowingEquationResult = false

            val trimmedText = currentEquation.trim()
            val backSpacedText = trimmedText.removeRange(trimmedText.lastIndex, trimmedText.length)
            equationTextInputEditText.setText(backSpacedText)
        }
    }
    //region Append Functions
    private fun appendNumberToEquation(number: String)
    {
        if(!viewModel.isShowingEquationResult)
        {
            var potentialWhitespace = ""
            if (currentEquation.isNotEmpty() && lastCharInEquationIsAnOperation())
            {
                potentialWhitespace = " "
            }
            appendTextToEquation("$potentialWhitespace$number")
        }
    }
    private fun appendOperationToEquation(operation: String)
    {
        if(!viewModel.isShowingEquationResult)
        {
            if(currentEquation.isNotEmpty() && isOperation(operation) && !lastCharInEquationIsAnOperation())
                appendTextToEquation(" $operation")
        }
    }
    private fun appendDecimalToEquation()
    {
        if(currentEquation.isNotEmpty() && !lastCharInEquationIsAnOperation())
            appendTextToEquation(".")
    }
    private fun appendTextToEquation(text: String)
    {
        if(!viewModel.isShowingEquationResult)
            equationTextInputEditText.append(text)
    }
    //endregion
    //region Helpers
    private fun lastCharInEquationIsAnOperation() : Boolean
    {
        return if(currentEquation.isNotEmpty())
            isOperation(currentEquation.trim().last().toString())
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
    private fun setEquationText(text: String)
    {
        equationTextInputEditText.setText(text)
    }
    private fun showToast(msg: String, shortDisplayLength: Boolean = true)
    {
        Toast.makeText(this, msg, if(shortDisplayLength) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
    }
    //endregion
    private fun displayEquationResult()
    {
        if(!viewModel.isShowingEquationResult)
        {
            viewModel.isShowingEquationResult = true
            if(currentEquation.isNotEmpty() && !lastCharInEquationIsAnOperation())
                setEquationText("$currentEquation = ${calculateEquationResult()}")
            else
                showToast("Finish the unclosed operation in your equation.")
        }
        else
            showToast("You've already calculated this equation.")
    }
    private fun calculateEquationResult() : Double
    {
        return viewModel.calculateResultOfEquation()
    }
}

