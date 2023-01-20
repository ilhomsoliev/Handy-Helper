@file:OptIn(ExperimentalMaterialApi::class)

package com.ikcollab.notes.presentation

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
import com.ikcollab.notes.presentation.components.CustomNotesCategory
import com.ikcollab.notes.presentation.theme.WhiteRed
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
   openFolderDetails:()->Unit,
   viewModel: NotesScreenViewModel = hiltViewModel()
) {
    val cardsScreenViewModel: CardsScreenViewModel = hiltViewModel()
    val stateNumberCategoriesNote by remember {
        viewModel.stateNumberCategoriesNote
    }
    val coroutineScope = rememberCoroutineScope()

    val stateFolder = viewModel.stateFolder.value.folders
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteRed)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {
            items(stateFolder) { folder ->
                Box(Modifier.fillMaxWidth()) {
                    ActionsRow(
                        actionIconSize = 80.dp,
                        onDelete = {
                            folder.id?.let {
                                viewModel.deleteFolder(
                                    it,
                                    folder.name,
                                    dateCreated = folder.dateCreated
                                )
                                Log.e("Delete", "Success")
                            }
                        },
                        onEdit = {}
                    )
                    //for advanced cases use DraggableCardComplex
                    DraggableCard(
                        isRevealed = cardsScreenViewModel.revealedCardIdsList.value.contains(
                            folder.id
                        ),
                        cardHeight = 56.dp,
                        cardOffset = 168f,
                        onExpand = { folder.id?.let { cardsScreenViewModel.onItemExpanded(it) } },
                        onCollapse = { folder.id?.let { cardsScreenViewModel.onItemCollapsed(it) } },
                        content = {
                            CustomNotesCategory(
                                onClick = {
                                    coroutineScope.launch {
                                            folder.id?.let { viewModel.updateNotesFolderIdAndName(it,folder.name) }
                                        openFolderDetails()
                                    }
                                },
                                icon = Icons.Default.Folder,
                                title = folder.name,
                                number = stateNumberCategoriesNote
                            )
                        },
                       backgroundColor = Color.Transparent
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}