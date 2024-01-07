package com.example.notescleanarch.feature_note.domain.use_cases

import com.example.notescleanarch.feature_note.domain.model.InvalidNoteException
import com.example.notescleanarch.feature_note.domain.model.Note
import com.example.notescleanarch.feature_note.domain.repository.NotesRepository
import kotlin.jvm.Throws

class AddNote(val notesRepository: NotesRepository) {


@Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){

        if(note.title.isBlank()){
            throw InvalidNoteException("The title is empty")
        }

    if (note.content.isBlank()){
        throw InvalidNoteException("The content is empty")
    }

    notesRepository.insertNote(note)

    }

}