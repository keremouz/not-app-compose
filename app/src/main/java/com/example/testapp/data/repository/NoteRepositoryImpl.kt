package com.example.testapp.data.repository

import com.example.testapp.data.local.NoteDao
import com.example.testapp.data.local.toDomain
import com.example.testapp.data.local.toEntity
import com.example.testapp.domain.model.Note
import com.example.testapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getAllNotes().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insert(note: Note) {
        dao.insert(note.toEntity())
    }

    override suspend fun update(note: Note) {
        dao.update(note.toEntity())
    }

    override suspend fun delete(note: Note) {
        dao.delete(note.toEntity())
    }
}