package com.example.notescleanarch.feature_note.presentation.add_edit_notes

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notescleanarch.feature_note.domain.model.Note
import com.example.notescleanarch.feature_note.presentation.add_edit_notes.components.TransparentHintTextField
import com.example.notescleanarch.feature_note.presentation.notes.NoteEvent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


//designing the screen for update section

@Composable
fun AddEditNoteScreen(
navController: NavController,// will use this to go back to mainActivity once the save btn is pressed
noteColor:Int,// here we passed the noteColor coz when we click at the note we need the background color of the screen to be same as the note we clicked
// so we pass the noteColor as parameter
viewModel: AddEditNoteViewModel= hiltViewModel()
){

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
            // -1 will occur when we click the add note btn if add note btn is clicked then the logic suggest the screen to take some random color,
            // the if  its not -1 it mean its some no which indicates its a noteId ...then take the noteColor of the note as the screen color instead of any
        // random color
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true ){

        viewModel.eventFlow.collectLatest {event->

            when(event){

                is AddEditNoteViewModel.UiEvent.ShowSnackbar->{
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }
                }

                is AddEditNoteViewModel.UiEvent.SaveNote->{
                    navController.navigateUp() // navigate works like stack when u use navigateUp it goes back to previous screen
                }

            }

        }

    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {viewModel.onEvent(AddEditNoteEvent.SaveNote)},
                containerColor = MaterialTheme.colorScheme.onPrimary)
            {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
        }
        },
        snackbarHost ={SnackbarHost(snackbarHostState)}
    ) {it->

        Column(modifier= Modifier
            .fillMaxSize()
            .background(noteBackgroundAnimatable.value)
            .padding(it),
            ) {

            Row (modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
                ){
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor.value == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                //    viewModel.noteColor.value=colorInt // we cant do this we cant directly change the color thats not a good practice
//                            we will instead use Onevent
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = { enteredTitle ->
                    // remember can't directly change the textView state
                    viewModel.onEvent(AddEditNoteEvent.EnteredTitle(enteredTitle))
                } ,
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible , // we r taking care of the logic of isHintVisible in ChangeTitleFocus(it), we have to just fetch the isHintVisible
                textStyle = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(start = 12.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {enteredContent->
                    // remember can't directly change the textView state
                    viewModel.onEvent(AddEditNoteEvent.EnteredContent(enteredContent))
                } ,
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible , // we r taking care of the logic of isHintVisible in ChangeTitleFocus(it), we have to just fetch the isHintVisible
                textStyle = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(15.dp)
            )

        }


    }

}