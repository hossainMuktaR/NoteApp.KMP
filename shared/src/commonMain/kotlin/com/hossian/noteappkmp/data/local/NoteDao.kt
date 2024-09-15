package com.hossian.noteappkmp.data.local

import com.hossian.noteappkmp.domain.model.Note

interface NoteDao {

    suspend fun getAllNotes(): List<Note>

    suspend fun getNoteById(id: Long): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

}