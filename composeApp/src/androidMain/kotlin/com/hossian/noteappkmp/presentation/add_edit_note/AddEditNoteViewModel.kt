package com.hossian.noteappkmp.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossian.noteappkmp.domain.model.Note
import com.hossian.noteappkmp.domain.usecases.NoteUseCases
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModelProvider
import com.hossian.noteappkmp.presentation.note_list.NoteListViewModel

class AddEditNoteViewModel(
    private val noteUseCases: NoteUseCases,
    private val noteId: Int
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title here..."
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter Note Content Here....."
        )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    val noteColors = listOf(Color.Red, Color.Blue, Color.Magenta, Color.Green, Color.Yellow )


    private val _noteColor = mutableIntStateOf(noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private var currentNoteId: Int? = null

    init {
        noteId.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteById.execute(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false,
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false,
                        )
                        _noteColor.value = note.color
                    }
                }
            }

        }
    }

    fun enterTitle(value: String) {
        _noteTitle.value = noteTitle.value.copy(
            text = value
        )
    }

    fun changeTitleFocus(focusState: FocusState) {
        _noteTitle.value = noteTitle.value.copy(
            isHintVisible = !focusState.isFocused &&
                    noteTitle.value.text.isBlank()
        )
    }

    fun enterContent(value: String) {
        _noteContent.value = noteContent.value.copy(
            text = value
        )
    }

    fun changeContentFocus(focusState: FocusState) {
        _noteContent.value = noteContent.value.copy(
            isHintVisible = !focusState.isFocused &&
                    noteContent.value.text.isBlank()
        )
    }

    fun changeColor(color: Int) {
        _noteColor.value = color
    }

    suspend fun saveNote() {
        noteUseCases.addNote.execute(
            Note(
                title = noteTitle.value.text,
                content = noteContent.value.text,
                color = noteColor.value,
                timeStamp = System.currentTimeMillis(),
                id = currentNoteId
            )
        )
    }
}

class AddEditViewModelFactory(
    private val noteUseCases: NoteUseCases,
    private val noteId: Int
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditNoteViewModel::class.java)) {
            return AddEditNoteViewModel(noteUseCases, noteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}