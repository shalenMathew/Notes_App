package com.example.notescleanarch.feature_note.domain.repository

import com.example.notescleanarch.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(): Flow<List<Note>>

    suspend  fun getNoteById(id:Int):Note?

    suspend fun insertNote(note:Note)

    suspend fun deleteNote(note:Note)

}