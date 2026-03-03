package com.example.testapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailScreen(
    viewModel: NoteViewModel,
    noteId: Int,
    onBack: () -> Unit
) {
    val notes = viewModel.allNotes.collectAsStateWithLifecycle(initialValue = emptyList()).value
    val note = notes.firstOrNull { it.id == noteId }

    var isEditing by remember(noteId) { mutableStateOf(false) }
    var title by remember(noteId) { mutableStateOf(note?.title.orEmpty()) }

    var description by remember(noteId) { mutableStateOf(note?.description.orEmpty()) }

    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(note?.id, note?.title, note?.description) {
        if (!isEditing && note != null) {
            title = note.title
            description = note.description
        }
    }

    if (showDeleteDialog && note != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Not silinsin mi?") },
            text = { Text("Bu işlem geri alınamaz.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.delete(note)
                        showDeleteDialog = false
                        onBack()
                    }
                ) { Text("Sil") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Vazgeç") }
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (isEditing) "Notu Düzenle" else "Not Detayı") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Geri") }
                },
                actions = {
                    if (note != null) {

                        TextButton(onClick = { showDeleteDialog = true }) {
                            Text("Sil")
                        }
                    }

                    if (isEditing) {
                        TextButton(
                            onClick = {
                                isEditing = false
                                if (note != null) {
                                    title = note.title
                                    description = note.description
                                }
                            }
                        ) {
                            Text("İptal")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (note == null) return@FloatingActionButton

                    if (!isEditing) {

                        isEditing = true
                    } else {

                        viewModel.update(
                            note.copy(
                                title = title,
                                description = description
                            )
                        )
                        isEditing = false
                    }
                }
            ) {
                Text(if (isEditing) "💾" else "✎")
            }
        }
    ) { padding ->
        if (note == null) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Not bulunamadı.")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                if (!isEditing) {
                    Text(note.title, style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(12.dp))
                    Text(note.description, style = MaterialTheme.typography.bodyLarge)
                } else {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Başlık") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Açıklama") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 160.dp),
                        minLines = 6,
                        maxLines = 12
                    )
                }
            }
        }
    }
}