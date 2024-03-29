package com.example.notescleanarch.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// designing radio btn
@Composable
fun DefaultRadioButton(
    text:String,
    selected:Boolean,
    onSelect:()->Unit,
    modifier: Modifier=Modifier
    ){

    Row(modifier=modifier,
        verticalAlignment = Alignment.CenterVertically) {

        RadioButton(selected = selected,
            onClick = onSelect, // on Click means when someone clicks the button
            // the logic of onSelect is defined in OrderSection
            colors = RadioButtonDefaults.colors(
                selectedColor = Color.Black,
                unselectedColor = Color.Gray
            )
        )

        Text(text = text, style = MaterialTheme.typography.labelSmall)
        
    }

 


}