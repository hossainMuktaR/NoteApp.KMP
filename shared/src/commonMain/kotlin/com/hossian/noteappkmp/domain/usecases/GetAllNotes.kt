package com.hossian.noteappkmp.domain.usecases

import com.hossian.noteappkmp.domain.model.Note
import com.hossian.noteappkmp.domain.repository.NoteRepository
import com.hossian.noteappkmp.utils.NoteOrder
import com.hossian.noteappkmp.utils.OrderType

class GetAllNotes(
    private val repository: NoteRepository
) {
    suspend fun execute(
        noteOrder: NoteOrder
    ): List<Note> {
        val notes = repository.getAllNotes()
        return when (noteOrder.orderType){
                is OrderType.Ascending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> { notes.sortedBy { it.title }}
                        is NoteOrder.Date -> { notes.sortedBy { it.timeStamp }}
                        is NoteOrder.Color -> { notes.sortedBy { it.color }}
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder){
                        is NoteOrder.Title -> { notes.sortedByDescending { it.title }}
                        is NoteOrder.Date -> { notes.sortedByDescending { it.timeStamp }}
                        is NoteOrder.Color -> { notes.sortedByDescending { it.color }}
                    }
                }
            }

    }
}