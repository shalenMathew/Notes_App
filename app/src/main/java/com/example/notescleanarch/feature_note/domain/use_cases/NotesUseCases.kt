package com.example.notescleanarch.feature_note.domain.use_cases

data class NotesUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNote: GetNote

)

    // ok now this an interesting class, we know that use cases consists the features of the app like fetching data, add ,delete etc.. and later in stage
// in app there will app where we will add new features and will make new uses cases, as we know that this use cases we should pass in viewmodel so
// that we can pass it to ui, we cant pass all the use_ cases in the viewmodel constructor , becoz as the no of usecases  will increase will also increase
// the parameter of the viewmodel , by using the data class we can avoid passing the all usecases in viewmodel instead we can only pass this data class
