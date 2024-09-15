package com.hossian.noteappkmp.domain.usecases

import com.hossian.noteappkmp.data.repository.NoteRepositoryImpl
import com.hossian.noteappkmp.domain.model.Note
import com.hossian.noteappkmp.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    suspend fun execute(note: Note) {
        if (note.title.isBlank()) return
        if (note.content.isBlank()) {
            return
        }
        repository.insertNote(note)
    }
}