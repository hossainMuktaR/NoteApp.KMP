package com.hossian.noteappkmp.domain.usecases

import com.hossian.noteappkmp.domain.model.Note
import com.hossian.noteappkmp.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend fun execute(note: Note){
        repository.deleteNote(note)
    }
}