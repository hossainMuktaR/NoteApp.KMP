package com.hossian.noteappkmp.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hossian.noteappkmp.presentation.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel
) {
    val noteTitle = viewModel.noteTitle.value
    val noteContent = viewModel.noteContent.value


    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val noteBackgroundAnimColor = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
//                        try {
                            viewModel.saveNote()
                            navController.navigateUp()
//                        } catch (e: InvalidNoteException) {
//                            scope.launch {
//                                snackbarHostState.showSnackbar(e.message ?: "Couldn't save note")
//                            }
//                        }
                    }
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "Save Note")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimColor.value)
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                viewModel.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (colorInt == viewModel.noteColor.value) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimColor.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.changeColor(colorInt)
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = noteTitle.text,
                hint = noteTitle.hint,
                onValueChange = { value ->
                    viewModel.enterTitle(value)
                },
                onFocusChange = { focusState ->
                    viewModel.changeTitleFocus(focusState)
                },
                isHintVisible = noteTitle.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h6.copy(
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = noteContent.text,
                hint = noteContent.hint,
                onValueChange = { value ->
                    viewModel.enterContent(value)
                },
                onFocusChange = { foucusState ->
                    viewModel.changeContentFocus(foucusState)
                },
                isHintVisible = noteContent.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.body1.copy(
                    color = Color.White
                ),
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}