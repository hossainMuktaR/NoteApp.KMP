package com.hossian.noteappkmp.presentation

sealed class Screen(val route: String){
    data object NoteListScreen: Screen("note_list_Screen")
    data object AddEditNoteScreen: Screen("add_edit_note_Screen")
}