package com.hossian.noteappkmp.domain.usecases

data class NoteUseCases(
    val getAllNotes: GetAllNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote,
    val getNoteById: GetNoteById,
)