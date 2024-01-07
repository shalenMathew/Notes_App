package com.example.notescleanarch.feature_note.domain.use_cases

import com.example.notescleanarch.feature_note.domain.model.Note
import com.example.notescleanarch.feature_note.domain.repository.NotesRepository

class DeleteNote(val notesRepository: NotesRepository) {

    suspend fun deleteNote(note: Note){
        notesRepository.deleteNote(note)
    }
}