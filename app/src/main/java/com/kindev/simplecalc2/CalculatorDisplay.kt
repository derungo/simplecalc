package com.kindev.simplecalc2

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorDisplay(
    result: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.
        fillMaxWidth()
        .border(2.dp, Color.Green.copy(alpha = 0.5f))
        .padding(16.dp),
        contentAlignment = Alignment.CenterEnd  // Aligns text to the right
    ) {
        Text(
            text = result,
            style = TextStyle(
                fontSize = 36.sp,  // Increased font size for better visibility
                color = Color.Green,  // Set color within TextStyle
                fontWeight = FontWeight.Bold  // Example to set fontWeight
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
