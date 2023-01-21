package com.ikcollab.notes.presentation.addNoteScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.notes.presentation.components.CustomInsertFolderTextField
import com.ikcollab.notes.presentation.notesScreen.NotesScreenViewModel

@Composable
fun AddNoteScreen(
    folderId:Int,
    onGoBack:()->Unit,
    viewModel: AddNoteScreenViewModel = hiltViewModel()
) {
    val stateNoteTitle by viewModel.stateNoteTitle
    val stateNoteDescription by viewModel.stateNoteDescription
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
                viewModel.insertNoteToDatabase(folderId = folderId, onDone = {

                })

            },
            shape = CircleShape
        ) {
            Icon(imageVector = Icons.Default.Done, contentDescription = null)
        }
    }
}