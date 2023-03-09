package com.ikcollab.notes.presentation.searchNotesScreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.core.Constants
import com.ikcollab.notes.presentation.components.CustomNotesItem
import com.ikcollab.notes.presentation.foldersNotesScreen.FoldersNoteScreenViewModel
import com.ikcollab.notes.presentation.notesScreen.NotesScreenViewModel
import kotlinx.coroutines.launch
import java.sql.Date
import java.util.*

@Composable
fun SearchNotesScreen(
    viewModel: SearchNotesScreenViewModel = hiltViewModel(),
    showDetailsOnClick:(Int,Int)->Unit,
    stateSearch:String,
    editNote:(Int,Int)->Unit
) {
    val foldersNoteScreenViewModel:FoldersNoteScreenViewModel = hiltViewModel()

    val notesScreenViewModel:NotesScreenViewModel = hiltViewModel()

    val coroutineScope = rememberCoroutineScope()

    val stateNotes = viewModel.stateNotes.value
    Column(modifier=Modifier.fillMaxSize()) {
        LazyColumn(modifier=Modifier.padding(top = 12.dp)){
            items(stateNotes.notes){ note->
                if(note.title.toUpperCase(Locale.ROOT)
                        .contains(stateSearch.trim().toUpperCase(Locale.ROOT))
                ){
                    DraggableScaffold(
                        contentUnderRight = {
                            SwipeTrash (onTrashClick = {
                                coroutineScope.launch {
                                    note.id?.let {
                                        foldersNoteScreenViewModel.deleteNoteById(
                                            it,
                                            note.title,
                                            dateCreated = note.dateCreated,
                                            description = note.description,
                                            folderId = note.folderId
                                        )
                                        Log.e("Delete", "Success")
                                    }
                                }
                            })
                        },
                        contentUnderLeft = {
                            SwipeEdit(onClick = {
                                Constants.WHICH_NOTE.value=Constants.EDIT_NOTE
                                Constants.FOLDER_ID_ARG_IS_LESS_OF_NULL.value = true
                                coroutineScope.launch {
                                    notesScreenViewModel.stateFolder.value.folders.forEach {
                                        if(it.id == note.folderId || note.folderId==-1){
                                            note.id?.let {id->
                                                editNote(note.folderId, id)
                                                Constants.NOTE_TITLE = note.title
                                                Constants.NOTE_DESCRIPTION = note.description
                                                Constants.NOTE_DATE_TIME = note.dateCreated
                                                Constants.FOLDER_ID = note.folderId
                                                Constants.NOTE_ID = id
                                            }
                                            Constants.FOLDER_NAME.value = if(note.folderId==-1) "" else it.name
                                        }
                                    }
                                }
                            })
                        },
                        contentOnTop = {
                            CustomNotesItem(
                                title = note.title,
                                description = note.description,
                                dateTime = Date(note.dateCreated).toString(),
                                showDetailsOnClick = {
                                    coroutineScope.launch {
                                        notesScreenViewModel.stateFolder.value.folders.forEach {
                                            if(it.id == note.folderId || note.folderId==-1){
                                                note.id?.let { id->
                                                    showDetailsOnClick(note.folderId,id)
                                                    Constants.NOTE_TITLE = note.title
                                                    Constants.NOTE_DESCRIPTION = note.description
                                                    Constants.NOTE_DATE_TIME = note.dateCreated
                                                }
                                                Constants.FOLDER_NAME.value = if(note.folderId==-1) "" else it.name
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}