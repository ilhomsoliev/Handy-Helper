package com.ikcollab.notes.presentation.foldersNotesScreen

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.notes.presentation.components.CustomNotesItem
import com.ikcollab.notes.presentation.notesScreen.NotesScreenViewModel
import com.ikcollab.notes.presentation.theme.WhiteRed
import java.sql.Date

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
    Column(modifier= Modifier
        .fillMaxSize()
        .background(WhiteRed)) {
        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            items(notes.notes) {note->
                CustomNotesItem(
                    title = note.title,
                    description = note.description,
                    dateTime = Date(note.dateCreated).toString()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Box(modifier = Modifier.fillMaxSize().padding(end = 25.dp, bottom = 25.dp), contentAlignment = Alignment.BottomEnd) {
            FloatingActionButton(backgroundColor = Color.Red,onClick = {
                openAddNoteScreen(folderId)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
}