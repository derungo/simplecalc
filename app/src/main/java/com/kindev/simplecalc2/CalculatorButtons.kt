package com.kindev.simplecalc2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CalculatorButtonsGrid(viewModel: CalculatorViewModel) {
    val buttons = listOf(
        listOf("(", ")", "MC", "MR", "MS", "C"),
        listOf("7", "8", "9", "/", "√"),
        listOf("4", "5", "6", "*", "^"),
        listOf("1", "2", "3", "-", "+"),
        listOf("0", ".", "←", "=")
    )
    // Using a Column to layout rows of buttons, filling available height
    Column(modifier = Modifier.fillMaxSize()) {  // Now fills the maximum size available
        buttons.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,  // Adjusted for no padding between buttons
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                row.forEach { label ->
                    CalculatorButton(
                        text = label,
                        onClick = { viewModel.onButtonClicked(label) },
                        color = if (label.any { it.isDigit() }) Color.LightGray else Color.DarkGray,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    color: Color,
    modifier: Modifier = Modifier
) {
    // Define categories for button types
    val isNumber = text.all { it.isDigit() }
    val isSpecialFunction = text in listOf("C", "MC", "MR", "MS", "←")  // Identify special function keys

    // Determine font size based on the category
    val fontSize = if (isNumber || isSpecialFunction) 18.sp else 22.sp

    // Define text color based on the category
    val textColor = if (isSpecialFunction) Color.White  // Keep special functions white
    else if (isNumber) Color.Black  // Numbers are black
    else Color.White  // Default color for operators and other functions



    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(0.dp),  // Maintaining square corners
        modifier = modifier
            .fillMaxHeight()  // Ensuring buttons fill the available vertical space within their row
            .padding(0.dp)  // Removing padding around the buttons
    ) {
        Text(
            text = text,
            fontSize = fontSize,  // Applying conditional font size
            color = textColor  // Applying conditional text color
        )
    }
}