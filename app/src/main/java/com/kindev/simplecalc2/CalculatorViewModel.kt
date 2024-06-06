package com.kindev.simplecalc2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mariuszgromada.math.mxparser.Expression


class CalculatorViewModel : ViewModel() {
    private val _result = MutableStateFlow("0")
    val result: StateFlow<String> = _result

    private val _memory = MutableStateFlow("0")
    val memory: StateFlow<String> = _memory

    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history

    private var lastInputWasResult = false


    fun updateDisplayValue(value: Double) {
        viewModelScope.launch {
            _result.value = if (value % 1.0 == 0.0) {
                value.toInt().toString()  // No decimal part, convert to Integer
            } else {
                value.toString()  // Keep the decimal part
            }
            lastInputWasResult = true
        }
    }
    fun onNumberClicked(number: Int) {
        viewModelScope.launch {
            if (lastInputWasResult) {
                _result.value = number.toString()
                lastInputWasResult = false
            } else {
                val currentResult = _result.value.trim()
                if (currentResult == "0") {
                    _result.value = number.toString()
                } else {
                    _result.value += number.toString()
                }
            }
        }
    }

    fun onOperatorClicked(operator: String) {
        viewModelScope.launch {
            try {
                println("Operator clicked: $operator")
                when (operator) {
                    "√" -> {
                        if (_result.value == "0" || lastInputWasResult) {
                            _result.value = "√("
                        } else {
                            val lastChar = _result.value.last()
                            if (lastChar.isDigit() || lastChar == ')') {
                                _result.value += "*√("
                            } else {
                                _result.value += "√("
                            }
                        }
                        lastInputWasResult = false
                    }
                    "+", "-", "*", "/", "^" -> {
                        if (lastInputWasResult) {
                            lastInputWasResult = false
                        }
                        _result.value += operator
                        println("Updated result: ${_result.value}")
                    }
                    "=" -> {
                        var expression = _result.value
                        // Append closing parenthesis if there's an unmatched opening parenthesis
                        val openParentheses = expression.count { it == '(' }
                        val closeParentheses = expression.count { it == ')' }
                        if (openParentheses > closeParentheses) {
                            expression += ")".repeat(openParentheses - closeParentheses)
                        }
                        // Replace display operator with internal operator for evaluation
                        expression = expression.replace("√", "sqrt")
                        val exp = Expression(expression)
                        val finalResult = exp.calculate()
                        if (finalResult.isNaN()) {
                            _result.value = "Error"
                        } else {
                            println("Expression evaluated: $expression = $finalResult")
                            addToHistory(_result.value, finalResult)
                            updateDisplayValue(finalResult)
                        }
                    }
                }
            } catch (e: Exception) {
                _result.value = "Error"
                println("Error in operator clicked: $e")
            }
        }
    }
    private fun evaluateExpression() {
        viewModelScope.launch {
            try {
                val expression = Expression(_result.value)
                val finalResult = expression.calculate()
                if (finalResult.isNaN()) {
                    _result.value = "Error"
                } else {
                    println("Expression evaluated: ${_result.value} = $finalResult")
                    addToHistory(_result.value, finalResult)
                    updateDisplayValue(finalResult)
                }
            } catch (e: Exception) {
                _result.value = "Error"
                println("Error in evaluateExpression: $e")
            }
        }
    }

    private fun addToHistory(expression: String, result: Double) {
        viewModelScope.launch {
            val newEntry = "$expression = $result"
            println("Adding to history: $newEntry") // Log the new entry
            _history.value += newEntry
            println("Current history: ${_history.value}")
        }
    }

    fun loadFromHistory(entry: String) {
        viewModelScope.launch {
            val parts = entry.split(" = ")
            if (parts.size == 2) {
                _result.value = parts[0]
                lastInputWasResult = true
            }
        }
    }
    fun onDecimalClicked() {
        viewModelScope.launch {
            val currentResult = _result.value.trim()
            // Replace "0" with "0." to start decimal inputs correctly
            if (currentResult == "0") {
                _result.value = "0."
            } else if (!currentResult.contains(".")) {
                _result.value += "."
            }
        }
    }
    fun onClearClicked() {
        viewModelScope.launch {
            _result.value = "0"
            lastInputWasResult = false
        }
    }

    fun onBackspaceClicked() {
        viewModelScope.launch {
            val currentResult = _result.value
            if (currentResult.length > 1) {
                _result.value = currentResult.substring(0, currentResult.length - 1)
            } else {
                _result.value = "0"
            }
        }
    }
    fun onParenthesisClicked(parenthesis: String) {
        viewModelScope.launch {
            val currentResult = _result.value.trim()
            // Specifically handle the scenario for starting with "("
            if (currentResult == "0" && parenthesis == "(") {
                _result.value = parenthesis
            } else {
                _result.value += parenthesis
            }
        }
    }

    fun onMemoryRecall() {
        viewModelScope.launch {
            _result.value = _memory.value
            lastInputWasResult = true
        }
    }

    fun onMemorySave() {
        viewModelScope.launch {
            _memory.value = _result.value
        }
    }

    fun onMemoryClear() {
        viewModelScope.launch {
            _memory.value = "0"
        }
    }

    fun onButtonClicked(button: String) {
        viewModelScope.launch {
            when (button) {
                "C" -> onClearClicked()
                "←" -> onBackspaceClicked()
                "MC" -> onMemoryClear()
                "MR" -> onMemoryRecall()
                "MS" -> onMemorySave()
                "+", "-", "*", "/", "^", "√", "=" -> onOperatorClicked(button)
                "(", ")" -> onParenthesisClicked(button)  // Handle parentheses

                "." -> onDecimalClicked()
                else -> {
                    if (button.all { it.isDigit() }) { // Check if all characters in the button are digits
                        onNumberClicked(button.toInt())
                    }
                }
            }
        }
    }
}
