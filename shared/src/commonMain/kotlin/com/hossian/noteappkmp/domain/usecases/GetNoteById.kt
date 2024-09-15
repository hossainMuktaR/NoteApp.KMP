package com.hossian.noteappkmp.domain.usecases

import com.hossian.noteappkmp.domain.model.Note
import com.hossian.noteappkmp.domain.repository.NoteRepository

class GetNoteById(
    private val repository: NoteRepository
) {
    suspend fun execute(id: Int): Note? {
        return repository.getNoteById(id)
    }
}