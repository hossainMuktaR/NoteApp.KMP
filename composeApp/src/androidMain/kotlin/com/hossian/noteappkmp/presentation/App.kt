package com.hossian.noteappkmp.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hossian.noteappkmp.data.local.DatabaseDriverFactory
import com.hossian.noteappkmp.data.local.NoteDaoImpl
import com.hossian.noteappkmp.data.local.NoteDatabaseFactory
import com.hossian.noteappkmp.data.repository.NoteRepositoryImpl
import com.hossian.noteappkmp.domain.usecases.AddNote
import com.hossian.noteappkmp.domain.usecases.DeleteNote
import com.hossian.noteappkmp.domain.usecases.GetAllNotes
import com.hossian.noteappkmp.domain.usecases.GetNoteById
import com.hossian.noteappkmp.domain.usecases.NoteUseCases
import com.hossian.noteappkmp.presentation.add_edit_note.AddEditNoteScreen
import com.hossian.noteappkmp.presentation.add_edit_note.AddEditNoteViewModel
import com.hossian.noteappkmp.presentation.add_edit_note.AddEditViewModelFactory
import com.hossian.noteappkmp.presentation.note_list.NoteListScreen
import com.hossian.noteappkmp.presentation.note_list.NoteListViewModel
import com.hossian.noteappkmp.presentation.note_list.NoteListViewModelFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val context = LocalContext.current
        val noteDao = remember { NoteDaoImpl(NoteDatabaseFactory(DatabaseDriverFactory(context)).createDatabase()) }
        val repository = remember { NoteRepositoryImpl(noteDao) }
        val noteUseCases = remember {
            NoteUseCases(
                getNoteById = GetNoteById(repository),
                getAllNotes = GetAllNotes(repository),
                addNote = AddNote(repository),
                deleteNote = DeleteNote(repository)
            )
        }

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.NoteListScreen.route )
        {
            composable(
                route = Screen.NoteListScreen.route
            ){
                val vm: NoteListViewModel = viewModel(
                    factory = NoteListViewModelFactory(
                        noteUseCases = noteUseCases
                    )
                )
                NoteListScreen(navController = navController, viewModel = vm)
            }
            composable(
                route = Screen.AddEditNoteScreen.route +
                        "?noteId={noteId}&noteColor={noteColor}",
                arguments = listOf(
                    navArgument(
                        name = "noteId"
                    ){
                        type = NavType.IntType
                        defaultValue = -1
                    },
                    navArgument(
                        name = "noteColor"
                    ){
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ){
                val noteColor = it.arguments?.getInt("noteColor") ?: -1
                val noteId = it.arguments?.getInt("noteId") ?: -1
                val vm: AddEditNoteViewModel = viewModel(
                    factory = AddEditViewModelFactory(
                        noteUseCases = noteUseCases,
                        noteId = noteId,
                    )
                )
                AddEditNoteScreen(navController = navController, noteColor = noteColor, viewModel = vm)
            }
        }
    }
}