package com.kindev.simplecalc2

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorMemory(
    memory: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "M: $memory",
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .alpha(0.5f),  // Set alpha value for transparency
        style = androidx.compose.ui.text.TextStyle(
            fontSize = 18.sp,  // Increased font size for better visibility
            color = Color.Green
        )
    )
}
