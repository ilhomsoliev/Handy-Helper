package com.ikcollab.notes.presentation.foldersNotesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.notes.presentation.notesScreen.NotesScreenViewModel

@Composable
fun FoldersNoteScreen(
    folderId:Int,
    openAddNoteScreen:(Int) ->Unit,
    viewModel: FoldersNoteScreenViewModel = hiltViewModel()
) {
    val notes by viewModel.stateNotesByFolderId

    LaunchedEffect(key1 = false, block = {
        viewModel.getNotesByFolderId(folderId = folderId)
    })

    LazyColumn() {
        items(notes.notes){
            Column() {
                Text(it.title)
                Text(it.description)
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd){
        FloatingActionButton(onClick = {
            openAddNoteScreen(folderId)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}