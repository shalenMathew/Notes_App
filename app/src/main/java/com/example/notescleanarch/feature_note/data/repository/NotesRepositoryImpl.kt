package com.example.notescleanarch.feature_note.data.repository

import com.example.notescleanarch.feature_note.data.data_source.NotesDao
import com.example.notescleanarch.feature_note.domain.model.Note
import com.example.notescleanarch.feature_note.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(val notesDao: NotesDao): NotesRepository {


    override fun getNotes(): Flow<List<Note>> {
        return notesDao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
       return notesDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
      return notesDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
      return notesDao.deleteNote(note)
    }
}