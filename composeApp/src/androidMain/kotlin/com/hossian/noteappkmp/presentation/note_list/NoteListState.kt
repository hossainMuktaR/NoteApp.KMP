package com.hossian.noteappkmp.presentation.note_list

import com.hossian.noteappkmp.domain.model.Note
import com.hossian.noteappkmp.utils.NoteOrder
import com.hossian.noteappkmp.utils.OrderType

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible : Boolean = false
)