package com.example.testapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddClick: () -> Unit,
    onGoNotes: () -> Unit,
    titleColor: Color,
    goNotesTextColor: Color
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Not Uygulaması",
                        color = titleColor
                    )
                }
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {

                FloatingActionButton(onClick = onAddClick) {
                    Text("+")
                }

                Spacer(Modifier.height(8.dp))

                // ✅ FAB'ın ALTINDA
                WeatherInfoCard()
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            TextButton(onClick = onGoNotes) {
                Text(
                    text = "Notlara Git",
                    color = goNotesTextColor,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}