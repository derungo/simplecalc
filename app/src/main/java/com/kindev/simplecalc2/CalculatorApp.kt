package com.kindev.simplecalc2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorAppContent(viewModel: CalculatorViewModel) {
    var showHistory by remember { mutableStateOf(false) }

    Surface(color = Color.Black) {
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

                    // History and Clear buttons
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 16.dp, bottom = 4.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.historyicon),
                            contentDescription = "History",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { showHistory = !showHistory }
                                .alpha(0.5f) // Semi-transparent
                                .padding(end = 10.dp), // Padding between buttons

                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Green)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_clear),
                            contentDescription = "Clear",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { viewModel.onClearClicked() }
                                .alpha(0.5f), // Semi-transparent
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Green)
                        )
                    }
                }

                // Button grid below the display
                CalculatorButtonsGrid(viewModel)
            }

            if (showHistory) {
                Surface(
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // Adjust height as needed
                        .align(Alignment.BottomCenter)
                        .shadow(4.dp, RectangleShape)
                        .clip(RectangleShape)
                        .background(Color.Black, RectangleShape)
                        .border(2.dp, Color.Green, RectangleShape)
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
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(history) { entry ->
            Text(
                text = entry,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Green), // Change text color to white
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(entry) }
                    .padding(8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
