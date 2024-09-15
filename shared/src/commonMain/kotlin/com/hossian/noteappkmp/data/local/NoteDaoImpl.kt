package com.hossian.noteappkmp.data.local

import com.hossain.noteappkmp.NoteDatabase
import com.hossian.noteappkmp.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class NoteDaoImpl(
    db: NoteDatabase
): NoteDao {

    private val queries = db.noteDatabaseQueries
    override suspend fun getAllNotes(): List<Note> {
        return withContext(Dispatchers.IO) {
            queries.getAllNotes().executeAsList().map { it.toNote() }
        }
    }

    override suspend fun getNoteById(id: Long): Note? {
        return withContext(Dispatchers.IO) {
            queries.getNoteById(id).executeAsOneOrNull()!!.toNote()
        }
    }

    override suspend fun insertNote(note: Note) {
        withContext(Dispatchers.IO) {
            queries.insertNote(
                note.id?.toLong(),
                note.title,
                note.content,
                note.timeStamp,
                note.color.toLong()
            )
        }
    }

    override suspend fun deleteNote(note: Note) {
        return withContext(Dispatchers.IO) {
            queries.deleteNote(note.id!!.toLong())
        }
    }

}