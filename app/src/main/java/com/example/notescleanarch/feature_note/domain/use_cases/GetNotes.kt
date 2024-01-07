package com.example.notescleanarch.feature_note.domain.use_cases

import com.example.notescleanarch.feature_note.domain.model.Note
import com.example.notescleanarch.feature_note.domain.repository.NotesRepository
import com.example.notescleanarch.feature_note.domain.util.NoteOrder
import com.example.notescleanarch.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(private val notesRepository: NotesRepository) {

    // use cases represents core business logic
    // and mostly perform one single function

    // so here we are getting notes

    operator fun invoke(
        // distinguishing between order type
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending) // passing the order in which we want to get our note as parameter(this is the
    // default parameter which we have already initialized
    ): Flow<List<Note>> {

        // invoke is a simple function that makes objects act like functions

        return  notesRepository.getNotes().map { notes->

            // so here in sort we are transforming the unsorted list into sorted one

           when(noteOrder.orderType) {
               is OrderType.Ascending ->{
                   when(noteOrder){
                       is NoteOrder.Date-> {notes.sortedBy { it.timeStamp }}
                       is NoteOrder.Color-> {notes.sortedBy { it.color }}
                       is NoteOrder.Title->{notes.sortedBy {  it.title.lowercase()}}
                   }
               }

               is OrderType.Descending ->{
                   when(noteOrder){
                       is NoteOrder.Date->{ notes.sortedByDescending { it.timeStamp }}
                       is NoteOrder.Title->{notes.sortedByDescending { it.title.lowercase() }}
                       is NoteOrder.Color->{notes.sortedByDescending { it.color }}
                   }
               }
           }
        }

    }


}