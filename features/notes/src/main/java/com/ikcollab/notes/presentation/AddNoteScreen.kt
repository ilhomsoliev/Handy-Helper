package com.ikcollab.notes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.notes.presentation.components.CustomInsertFolderTextField

@Composable
fun AddNoteScreen(
    onGoBack:()->Unit,
) {
    val viewModel: NotesScreenViewModel = hiltViewModel()
    val stateNoteTitle by viewModel.stateNoteTitle
    val stateNoteDescription by viewModel.stateNoteDescription
    val stateFolderId=viewModel.stateNotesFolderId
    Column() {
        CustomInsertFolderTextField(
            value = stateNoteTitle,
            onValueChange = { viewModel.updateNoteTitle(it) },
            placeholder = "Title"
        )
        CustomInsertFolderTextField(
            value = stateNoteDescription,
            onValueChange = { viewModel.updateNoteDescription(it) },
            placeholder = "Description.."
        )
        FloatingActionButton(
            backgroundColor = Color.Red,
            onClick = {
                viewModel.insertNoteToDatabase(stateFolderId.value)
                onGoBack()
            },
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = null)
        }
    }
}