package com.hossian.noteappkmp.data.repository

import com.hossian.noteappkmp.data.local.NoteDao
import com.hossian.noteappkmp.data.local.NoteDaoImpl
import com.hossian.noteappkmp.domain.model.Note
import com.hossian.noteappkmp.domain.repository.NoteRepository

class NoteRepositoryImpl(
    private val noteDao: NoteDao
): NoteRepository {
    override suspend fun getAllNotes(): List<Note> = noteDao.getAllNotes()

    override suspend fun getNoteById(id: Int): Note? = noteDao.getNoteById(id.toLong())

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

}