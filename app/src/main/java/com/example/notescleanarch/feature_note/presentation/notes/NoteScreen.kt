package com.example.notescleanarch.feature_note.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.notescleanarch.feature_note.presentation.notes.components.NoteItem
import com.example.notescleanarch.feature_note.presentation.notes.components.OrderSection
import com.example.notescleanarch.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun NoteScreen(
    navController: NavController,
    viewModel:NotesViewModel= hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = remember { SnackbarHostState()}
    val scope = rememberCoroutineScope()

// Jetpack Compose Scaffold is a pre-designed layout component in the Jetpack Compose UI toolkit.

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.AddEditNoteScreen.route) }, containerColor = MaterialTheme.colorScheme.onPrimary )
            {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        snackbarHost = { SnackbarHost(scaffoldState) }
    ) {it->

        Column(modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()){

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,) {

                Text(text = "Notes",modifier=Modifier,color = Color.Black, style = MaterialTheme.typography.headlineLarge, fontSize =20.sp)

               IconButton(onClick = {
                  viewModel.onEvent(NoteEvent.ToggleOrderSection)// making the boolean true or false as we click the icon
               }) {
                   Icon(imageVector = Icons.Default.Sort, contentDescription ="Sort" )
               }
            }
            AnimatedVisibility(visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {

                OrderSection(modifier = Modifier
                    .fillMaxWidth(),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onEvent(NoteEvent.Order(it))
                    })
            }
            
            Spacer(modifier = Modifier.height(10.dp))

            // notes list starts from here
            
            LazyColumn(modifier= Modifier.fillMaxSize()){
                items(state.notes){note->
                    
                    NoteItem(note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {    navController.navigate(
                                Screen.AddEditNoteScreen.route +
                                        "?noteId=${note.id}&noteColor=${note.color}"
                            )},
                        onDeleteClick = {
                            viewModel.onEvent(NoteEvent.DeleteNote(note))

                            // we should launch a snack-bar in coroutine
                            scope.launch {
                                val result = scaffoldState.showSnackbar(
                                    message ="Note deleted",
                                    actionLabel = "Undo"
                                )

                                if (result==SnackbarResult.ActionPerformed){
                                    viewModel.onEvent(NoteEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                
            }
            
        }



    }

}