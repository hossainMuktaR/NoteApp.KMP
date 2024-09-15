package com.hossian.noteappkmp.data.local

import com.hossain.noteappkmp.NoteEntity
import com.hossian.noteappkmp.domain.model.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id.toInt(),
        title = title,
        timeStamp = timeStamp,
        content = content,
        color = color.toInt(),
    )
}