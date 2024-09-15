package com.hossian.noteappkmp.domain.repository

import com.hossian.noteappkmp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun getAllNotes(): List<Note>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}