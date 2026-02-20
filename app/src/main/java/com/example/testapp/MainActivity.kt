package com.example.testapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteApp()
        }
    }
}

@Composable
fun NoteApp() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var editingNote by remember { mutableStateOf<Pair<String, String>?>(null) }
    val notes = remember { mutableStateListOf<Pair<String, String>>() }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

        ){
            Text("Notlarım",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Başlık") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Notunuzu buraya yazınız") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if(editingNote == null ){
                        notes.add(Pair(title,description))
                    }else {
                        val index = notes.indexOf(editingNote)
                        if(index != -1){
                            notes[index] = Pair(title,description)
                        }
                        editingNote = null
                    }
                    title = ""
                    description = ""
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                )
            ) {
                Text(if (editingNote == null) "Notlara ekle" else "Güncelle")
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(notes) { note ->
                    var expanded by remember  { mutableStateOf(false) }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        onClick = {
                            expanded =!expanded
                        }
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(note.first,
                                style = MaterialTheme.typography.titleMedium)
                            Row (
                                modifier= Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End)
                            {

                                Button(
                                    onClick = {
                                        title= note.first
                                        description = note.second
                                        editingNote = note
                                    }
                                ) {
                                    Text("düzenle")
                                }
                                Button(
                                    onClick = {
                                        notes.remove(note) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.error
                                    )
                                ) { Text("Sil") }
                            }
                            if(expanded){
                                Spacer(modifier =Modifier.height(8.dp))
                                Text(note.second)

                            }

                        }
                    }
                }
            }
        }

}
@Preview(showBackground = true)
@Composable
fun PreviewNoteApp() {
    NoteApp()
}
