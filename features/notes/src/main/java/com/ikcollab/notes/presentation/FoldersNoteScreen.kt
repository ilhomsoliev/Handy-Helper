package com.ikcollab.notes.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FoldersNoteScreen(
) {
    val viewModel: NotesScreenViewModel = hiltViewModel()
    val notes by viewModel.stateNotesByFolderId
    LazyColumn() {
        items(notes.notes){
            Column() {
                Text(it.title)
                Text(it.description)
            }
        }
    }
}