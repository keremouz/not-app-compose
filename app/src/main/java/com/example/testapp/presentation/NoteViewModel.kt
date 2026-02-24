package com.example.testapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.data.local.NoteDatabase
import com.example.testapp.data.repository.NoteRepositoryImpl
import com.example.testapp.domain.model.Note
import kotlinx.coroutines.launch
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = NoteDatabase.getDatabase(application).noteDao()
    private val repository = NoteRepositoryImpl(dao)

    val allNotes = repository.getNotes()

    fun insert(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repository.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repository.delete(note)
    }
}