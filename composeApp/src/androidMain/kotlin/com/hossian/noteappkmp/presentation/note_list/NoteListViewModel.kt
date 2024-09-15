package com.hossian.noteappkmp.presentation.note_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hossian.noteappkmp.domain.model.Note
import com.hossian.noteappkmp.domain.usecases.NoteUseCases
import com.hossian.noteappkmp.utils.NoteOrder
import com.hossian.noteappkmp.utils.OrderType
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val noteUseCases: NoteUseCases
) : ViewModel() {

    private val _state = mutableStateOf<NoteListState>(NoteListState())
    val state: State<NoteListState> = _state

    private var recentlyDeletedNote: Note? = null
    private var fetchRunning = false

    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteUseCases.deleteNote.execute(note)
            recentlyDeletedNote = note
        }
        getAllNotes()
    }

    fun toggleOrderSection(){
        _state.value = state.value.copy(
            isOrderSectionVisible = !state.value.isOrderSectionVisible
        )
        getAllNotes()
    }

    fun restoreNote(){
        viewModelScope.launch {
            noteUseCases.addNote.execute(recentlyDeletedNote ?: return@launch)
            recentlyDeletedNote = null
        }
        getAllNotes()
    }
    fun orderNoteList(noteOrder: NoteOrder){
        if (state.value.noteOrder::class == noteOrder::class &&
            state.value.noteOrder.orderType == noteOrder.orderType
        ) {
            return
        }
        getAllNotes(noteOrder)
    }

    fun getAllNotes(noteOrder: NoteOrder = _state.value.noteOrder) {
        if (fetchRunning) {
            return
        }
        viewModelScope.launch {
            fetchRunning = true
            _state.value = state.value.copy(
                notes = noteUseCases.getAllNotes.execute(noteOrder),
                noteOrder = noteOrder
            )
            fetchRunning = false
        }
    }
}

class NoteListViewModelFactory(
    private val noteUseCases: NoteUseCases
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
            return NoteListViewModel(noteUseCases = noteUseCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}