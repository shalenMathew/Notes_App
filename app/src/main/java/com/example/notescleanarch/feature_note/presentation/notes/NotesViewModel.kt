package com.example.notescleanarch.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notescleanarch.feature_note.domain.model.Note
import com.example.notescleanarch.feature_note.domain.use_cases.NotesUseCases
import com.example.notescleanarch.feature_note.domain.util.NoteOrder
import com.example.notescleanarch.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NotesViewModel @Inject constructor(val notesUseCases: NotesUseCases):ViewModel(){ // hilt initializes notesUseCases

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob:Job? = null

init {
    getNotes(NoteOrder.Date(OrderType.Descending))
}


fun onEvent(noteEvent:NoteEvent){

    when(noteEvent){

        is NoteEvent.Order->{

            if(state.value.noteOrder::class==noteEvent.noteOrder::class &&
                state.value.noteOrder.orderType==noteEvent.noteOrder.orderType){
                return
            }
            getNotes(noteEvent.noteOrder)
        }

        is NoteEvent.DeleteNote->{
           viewModelScope.launch {
               notesUseCases.deleteNote.deleteNote(noteEvent.note) // when we not using invoke we have to call the function
               recentlyDeletedNote=noteEvent.note
           }
        }

       is NoteEvent.RestoreNote-> {
           viewModelScope.launch {
               notesUseCases.addNote(recentlyDeletedNote ?: return@launch) // here we are using invoke that why we can call pass the parameters
               // directly to the objects instead of calling tye function to pass the object
               recentlyDeletedNote=null
           }
       }

        is NoteEvent.ToggleOrderSection-> {
            _state.value = state.value.copy(
                isOrderSectionVisible = !state.value.isOrderSectionVisible // toggling logic
            )
        }
    }

}

    private fun getNotes(noteOrder:NoteOrder) {
        // everytime we call getNotes we might create new scope everytime so to cancel previous scope and to launch new scope we will use job
        getNotesJob?.cancel()// if there exist a scope then cancel it

        getNotesJob=notesUseCases.getNotes(noteOrder).onEach {notesList ->

            _state.value =state.value.copy(
                notes = notesList,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)

    }

}