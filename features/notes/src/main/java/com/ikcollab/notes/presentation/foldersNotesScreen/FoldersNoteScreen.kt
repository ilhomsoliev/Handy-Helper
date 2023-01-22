package com.ikcollab.notes.presentation.foldersNotesScreen

import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.components.DraggableCard.ActionsRow
import com.ikcollab.components.DraggableCard.CardsScreenViewModel
import com.ikcollab.components.DraggableCard.DraggableCard
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.core.Constants
import com.ikcollab.notes.presentation.components.CustomNotesCategory
import com.ikcollab.notes.presentation.components.CustomNotesItem
import com.ikcollab.notes.presentation.notesScreen.NotesScreenViewModel
import com.ikcollab.notes.presentation.theme.WhiteRed
import kotlinx.coroutines.launch
import java.sql.Date

@Composable
fun FoldersNoteScreen(
    folderId:Int,
    openAddNoteScreen:(Int) ->Unit,
    viewModel: FoldersNoteScreenViewModel = hiltViewModel(),
    showDetailsOnClick:(Int,Int)->Unit
) {

    val notes by viewModel.stateNotesByFolderId

    LaunchedEffect(key1 = false, block = {
        viewModel.getNotesByFolderId(folderId = folderId)
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(WhiteRed)
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            items(notes.notes) { note ->
                DraggableScaffold(
                    contentUnderRight = {
                        SwipeTrash (onTrashClick = {
                            note.id?.let {
                                viewModel.deleteNoteById(
                                    it,
                                    note.title,
                                    dateCreated = note.dateCreated,
                                    description = note.description,
                                    folderId = folderId
                                )
                                Log.e("Delete", "Success")
                            }
                        })
                    },
                    contentUnderLeft = {
                        SwipeEdit(onClick = {
                            // TODO
                        })
                    },
                    contentOnTop = {
                        CustomNotesItem(
                            title = note.title,
                            description = note.description,
                            dateTime = Date(note.dateCreated).toString(),
                            showDetailsOnClick = {
                                note.id?.let {
                                    showDetailsOnClick(folderId, it)
                                    Constants.NOTE_TITLE = note.title
                                    Constants.NOTE_DESCRIPTION = note.description
                                    Constants.NOTE_DATE_TIME = note.dateCreated
                                }
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 25.dp, bottom = 25.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(backgroundColor = Color.Red, onClick = {
            openAddNoteScreen(folderId)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}