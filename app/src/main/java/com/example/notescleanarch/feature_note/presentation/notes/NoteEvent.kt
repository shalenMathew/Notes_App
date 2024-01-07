package com.example.notescleanarch.feature_note.presentation.notes

import com.example.notescleanarch.feature_note.domain.model.Note
import com.example.notescleanarch.feature_note.domain.util.NoteOrder

sealed class NoteEvent {

    // event simply means every single action an user can make in  an ui this below are the changes an user can make and thats what we declared it here
    data class Order(val noteOrder: NoteOrder) : NoteEvent()
    data class DeleteNote(val note: Note) : NoteEvent()
    data object RestoreNote:NoteEvent() // to restore the recently deleted note
    data object ToggleOrderSection:NoteEvent() // to toggle the the sort button
}