@file:OptIn(ExperimentalMaterialApi::class)

package com.ikcollab.notes.presentation.notesScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.components.DraggableCard.*
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.core.Constants
import com.ikcollab.notes.presentation.components.CustomNotesCategory
import com.ikcollab.notes.presentation.components.CustomNotesItem
import com.ikcollab.notes.presentation.foldersNotesScreen.FoldersNoteScreenViewModel
import com.ikcollab.notes.presentation.searchNotesScreen.SearchNotesScreenViewModel
import kotlinx.coroutines.launch
import java.sql.Date

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NotesScreen(
    folderId:Int,
    openFolderDetails:(Int)->Unit,
   viewModel: NotesScreenViewModel = hiltViewModel(),
   showDetailsOnClick:(Int,Int)->Unit,
    editNote:(Int,Int)->Unit
) {
    val foldersNoteScreenViewModel: FoldersNoteScreenViewModel = hiltViewModel()

    val coroutineScope = rememberCoroutineScope()

    val stateNotesByFolderId = remember{ foldersNoteScreenViewModel.stateNotesByFolderId }
    val stateFolder = remember { viewModel.stateFolder }

    LaunchedEffect(key1 = true){
        viewModel.getFolders()
        foldersNoteScreenViewModel.getNotesByFolderId(-1){
            Constants.FOLDER_NAME.value = ""
            Constants.FOLDER_ID_IS_NULL.value = false
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {
            items(stateFolder.value.folders) { folder ->
                DraggableScaffold(
                    contentUnderRight = {
                        SwipeTrash(onTrashClick = {
                            coroutineScope.launch {
                                folder.id?.let { id ->
                                    viewModel.deleteFolder(
                                        id,
                                        folder.name,
                                        dateCreated = folder.dateCreated
                                    )
                                    viewModel.deleteAllNotesByFolderId(id)
                                    Log.e("Delete", "Success")
                                }
                            }
                        })
                    },
                    contentUnderLeft = {
                        SwipeEdit(onClick = {
                            // TODO
                        })
                    },
                    contentOnTop = {
                        CustomNotesCategory(
                            onClick = {
                                coroutineScope.launch {
                                        openFolderDetails(folder.id!!)
                                    Constants.FOLDER_NAME.value = folder.name
                                }
                            },
                            icon = Icons.Default.Folder,
                            title = folder.name,
                            number = folder.countOfNotes
                        )
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
            item {
                Spacer(modifier = Modifier.height(25.dp))
            }
            items(stateNotesByFolderId.value.notes){ note ->
                if(note.folderId<=0) {
                    DraggableScaffold(
                        contentUnderRight = {
                            SwipeTrash(onTrashClick = {
                                note.id?.let {
                                    foldersNoteScreenViewModel.deleteNoteById(
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
                                Constants.FOLDER_ID_ARG_IS_LESS_OF_NULL.value = true
                                Constants.WHICH_NOTE.value=Constants.EDIT_NOTE
                                coroutineScope.launch {
                                    stateFolder.value.folders.forEach { folder->
                                        if(folder.id == note.folderId || note.folderId==-1){
                                            note.id?.let {id->
                                                editNote(note.folderId, id)
                                                Constants.NOTE_TITLE = note.title
                                                Constants.NOTE_DESCRIPTION = note.description
                                                Constants.NOTE_DATE_TIME = note.dateCreated
                                                Constants.FOLDER_ID = note.folderId
                                                Constants.NOTE_ID = id
                                            }
                                            Constants.FOLDER_NAME.value = if(note.folderId==-1) "" else folder.name
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
                                        note.id?.let {
                                            showDetailsOnClick(folderId, it)
                                            Constants.NOTE_TITLE = note.title
                                            Constants.NOTE_DESCRIPTION = note.description
                                            Constants.NOTE_DATE_TIME = note.dateCreated
                                        }
                                    }
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}