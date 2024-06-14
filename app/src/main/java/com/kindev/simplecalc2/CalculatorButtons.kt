package com.kindev.simplecalc2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButtonsGrid(viewModel: CalculatorViewModel) {
    val buttonRows = listOf(
        listOf("(", ")", "MC", "MR", "MS"),
        listOf("7", "8", "9", "/", "√"),
        listOf("4", "5", "6", "*", "^"),
        listOf("1", "2", "3", "-", "+"),
        listOf("0", ".", "←", "=")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        buttonRows.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(4.dp)
            ) {
                row.forEach { label ->
                    CalculatorButton(
                        text = label,
                        onClick = { viewModel.onButtonClicked(label) },
                        color = if (label.any { it.isDigit() }) Color(0xFF66BB6A) else Color(0xFF2E7D32),
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
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
    val isNumber = text.all { it.isDigit() }
    val isSpecialFunction = text in listOf( "MC", "MR", "MS", "←")

    val fontSize = when (text) {
        "MC", "MR", "MS" -> 20.sp
        else -> if (isNumber) 30.sp else 36.sp
    }
    val textColor = if (isSpecialFunction) Color.White else if (isNumber) Color.Black else Color.White

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxHeight()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                fontSize = fontSize,
                color = textColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
