package com.example.notescleanarch.feature_note.domain.use_cases

import com.example.notescleanarch.feature_note.domain.model.Note
import com.example.notescleanarch.feature_note.domain.repository.NotesRepository

class GetNote(val repository: NotesRepository) {

    suspend operator fun invoke(id:Int): Note?{
    return repository.getNoteById(id)
}
}