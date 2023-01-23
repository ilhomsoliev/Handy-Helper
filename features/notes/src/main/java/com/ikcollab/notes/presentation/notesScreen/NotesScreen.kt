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
import com.ikcollab.notes.presentation.theme.WhiteRed
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
   openFolderDetails:(Int)->Unit,
   viewModel: NotesScreenViewModel = hiltViewModel()
) {
    val stateNumberCategoriesNote by remember {
        viewModel.stateNumberCategoriesNote
    }
    val coroutineScope = rememberCoroutineScope()

    val stateFolder = viewModel.stateFolder.value.folders
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(WhiteRed)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {
            items(stateFolder) { folder ->
                DraggableScaffold(
                    contentUnderRight = {
                        SwipeTrash (onTrashClick = {
                            coroutineScope.launch {
                                folder.id?.let {
                                    viewModel.deleteFolder(
                                        it,
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
                            number = stateNumberCategoriesNote
                        )
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}