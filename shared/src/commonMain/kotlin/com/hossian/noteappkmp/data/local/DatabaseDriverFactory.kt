package com.hossian.noteappkmp.data.local

import app.cash.sqldelight.db.SqlDriver
import com.hossain.noteappkmp.NoteDatabase

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

class NoteDatabaseFactory(private val driverFactory: DatabaseDriverFactory) {
    fun createDatabase(): NoteDatabase {
        val driver = driverFactory.createDriver()
        return NoteDatabase(driver)
    }
}