package com.example.notescleanarch.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notescleanarch.ui.theme.BabyBlue
import com.example.notescleanarch.ui.theme.LightGreen
import com.example.notescleanarch.ui.theme.RedOrange
import com.example.notescleanarch.ui.theme.RedPink
import com.example.notescleanarch.ui.theme.Violet

// declaring model in domain layer as different business will have different models which means model class comes under business logic which is usually
// declared in domain class

@Entity
data class Note(
    val title:String,
    val content:String,
    val timeStamp:Long,
    val color:Int,
    @PrimaryKey
    val id:Int? = null
){
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message:String):Exception(message)