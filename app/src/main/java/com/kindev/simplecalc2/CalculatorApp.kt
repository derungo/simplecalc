package com.kindev.simplecalc2

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun CalculatorAppContent(viewModel: CalculatorViewModel) {
    var showHistory by remember { mutableStateOf(false) }

    Surface(color = MaterialTheme.colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 32.dp)
            ) {
                // Display the result and memory above the buttons
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.align(Alignment.CenterStart)) {
                        CalculatorDisplay(result = viewModel.result.collectAsState().value)
                        CalculatorMemory(memory = viewModel.memory.collectAsState().value)
                    }

                    // History button
                    Image(
                        painter = painterResource(id = R.drawable.historyicon),
                        contentDescription = "History",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .size(24.dp)
                            .clickable { showHistory = !showHistory }
                            .alpha(0.5f) // Semi-transparent
                    )
                }

                // Button grid below the display
                CalculatorButtonsGrid(viewModel)
            }

            if (showHistory) {
                Surface(
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // Adjust height as needed
                        .align(Alignment.BottomCenter)
                ) {
                    CalculatorHistory(
                        history = viewModel.history.collectAsState().value,
                        onSelect = { entry ->
                            viewModel.loadFromHistory(entry)
                            showHistory = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorHistory(history: List<String>, onSelect: (String) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        items(history) { entry ->
            Text(
                text = entry,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(entry) }
                    .padding(8.dp)
            )
        }
    }
}