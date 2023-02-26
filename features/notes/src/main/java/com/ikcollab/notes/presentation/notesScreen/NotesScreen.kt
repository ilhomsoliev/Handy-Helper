@file:OptIn(ExperimentalMaterialApi::class)

package com.ikcollab.notes.presentation.notesScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.components.DraggableCard.*
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeDoneTrash
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.core.Constants
import com.ikcollab.notes.presentation.components.CustomNotesCategory
import com.ikcollab.notes.presentation.foldersNotesScreen.FoldersNoteScreenViewModel
import com.ikcollab.notes.presentation.theme.WhiteRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
   openFolderDetails:(Int)->Unit,
   viewModel: NotesScreenViewModel = hiltViewModel()
) {
    val foldersNoteScreenViewModel: FoldersNoteScreenViewModel = hiltViewModel()

    val coroutineScope = rememberCoroutineScope()

    val stateFolder = remember { viewModel.stateFolder }

    LaunchedEffect(key1 = true){
        stateFolder.value.folders.forEach { folder ->
            folder.id?.let {
                foldersNoteScreenViewModel.getNotesByFolderId(folderId = it)
            }
        }
        delay(100)
        viewModel.countNotesOfFolder()
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
        }
    }
}