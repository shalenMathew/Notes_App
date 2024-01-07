package com.example.notescleanarch.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notescleanarch.feature_note.domain.util.NoteOrder
import com.example.notescleanarch.feature_note.domain.util.OrderType

// making order layout

@Composable
fun OrderSection(
    modifier: Modifier=Modifier,
    noteOrder:NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange:(NoteOrder)-> Unit // takes a NoteOrder as parameter
){
Column(
    modifier=modifier
) {

    Row (modifier=modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically)
    {
        DefaultRadioButton(text = "Title",
            selected = noteOrder is NoteOrder.Title, // if Title button is selected
            onSelect ={ onOrderChange(NoteOrder.Title(noteOrder.orderType)) }) // on click this logic will be executed
        Spacer(modifier = Modifier.width(8.dp))

        DefaultRadioButton(text = "Date",
            selected = noteOrder is NoteOrder.Date,
            onSelect ={ onOrderChange(NoteOrder.Date(noteOrder.orderType)) })

        Spacer(modifier = Modifier.width(8.dp))

        DefaultRadioButton(text = "Color",
            selected = noteOrder is NoteOrder.Color,
            onSelect ={ onOrderChange(NoteOrder.Color(noteOrder.orderType)) })


    }

    Spacer(modifier = Modifier.height(5.dp))


    Row(modifier=modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        DefaultRadioButton( text = "Ascending",
            selected = noteOrder.orderType is OrderType.Ascending ,
            onSelect = {
                onOrderChange(noteOrder.copy(OrderType.Ascending))
            })

        Spacer(modifier = Modifier.width(8.dp))

        DefaultRadioButton(text = "Descending",
            selected = noteOrder.orderType is OrderType.Descending,
            onSelect = {
                onOrderChange(noteOrder.copy(OrderType.Descending))
            })
    }
}
}