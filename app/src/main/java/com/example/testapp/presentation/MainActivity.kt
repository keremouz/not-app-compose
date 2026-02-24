package com.example.testapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testapp.domain.model.Note
import com.example.testapp.ui.theme.TestAppTheme
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {

    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestAppTheme {
                NoteScreen(viewModel)
            }
        }
    }
}

@Composable
fun NoteScreen(viewModel: NoteViewModel) {

    val notes by viewModel.allNotes.collectAsStateWithLifecycle(initialValue = emptyList())

    NoteContent(
        notes = notes,
        onAdd = { viewModel.insert(it) },
        onUpdate = { viewModel.update(it) },
        onDelete = { viewModel.delete(it) }
    )
}

@Composable
fun NoteContent(
    notes: List<Note>,
    onAdd: (Note) -> Unit,
    onUpdate: (Note) -> Unit,
    onDelete: (Note) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var editingNote by remember { mutableStateOf<Note?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Notlarım",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Başlık") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Açıklama") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (title.isNotBlank()) {

                    if (editingNote == null) {
                        onAdd(
                            Note(
                                id = 0,
                                title = title,
                                description = description
                            )
                        )
                    } else {
                        editingNote?.let {
                            onUpdate(
                                it.copy(
                                    title = title,
                                    description = description
                                )
                            )
                        }
                        editingNote = null
                    }

                    title = ""
                    description = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.Yellow
            )
        ) {
            Text(if (editingNote == null) "Ekle" else "Güncelle")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = notes,
                key = { it.id }
            ) { note ->

                var expanded by remember(note.id) { mutableStateOf(false) }

                Card(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black,
                        contentColor = Color.Yellow
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        if (expanded) {

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(text = note.description)

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(onClick = {
                                    editingNote = note
                                    title = note.title
                                    description = note.description
                                }) {
                                    Text("Düzenle")
                                }

                                TextButton(onClick = {
                                    onDelete(note)
                                }) {
                                    Text("Sil")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoteContent() {
    TestAppTheme {
        NoteContent(
            notes = emptyList(),
            onAdd = {},
            onUpdate = {},
            onDelete = {}
        )
    }
}