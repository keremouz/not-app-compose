package com.example.testapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.testapp.domain.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    viewModel: NoteViewModel,
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Not Ekle") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Geri") } }
            )
        },
        floatingActionButton = {
            Column(horizontalAlignment = Alignment.End) {

                FloatingActionButton(
                    onClick = {
                        if (title.isNotBlank()) {
                            viewModel.insert(
                                Note(id = 0, title = title.trim(), description = description.trim())
                            )
                            onSaved()
                        }
                    }
                ) { Text("✓") }

                Spacer(Modifier.height(8.dp))


                WeatherInfoCard()
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Başlık") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(14.dp))


            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Açıklama") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 400.dp, max = 1000.dp),
                maxLines = 8
            )


            Spacer(Modifier.height(8.dp))
        }
    }
}