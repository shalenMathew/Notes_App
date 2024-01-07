package com.example.notescleanarch.feature_note.domain.util

sealed class NoteOrder(val orderType: OrderType) {

    // 3 diff ways to order a note
    class Title(orderType: OrderType):NoteOrder(orderType)
    class Date(orderType: OrderType):NoteOrder(orderType)
    class Color(orderType: OrderType):NoteOrder(orderType)

    fun copy(orderType: OrderType):NoteOrder{
       return when(this){

          is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }

}