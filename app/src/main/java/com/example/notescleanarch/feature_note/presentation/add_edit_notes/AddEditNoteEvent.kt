package com.example.notescleanarch.feature_note.presentation.add_edit_notes

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    // event simply means every single action an user can make

    data class EnteredTitle(val title:String):AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnteredContent(val content:String):AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditNoteEvent()
    data class ChangeColor(val color:Int):AddEditNoteEvent()
    object SaveNote:AddEditNoteEvent()


}


