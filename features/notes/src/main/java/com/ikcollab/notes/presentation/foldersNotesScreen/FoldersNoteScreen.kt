package com.ikcollab.notes.presentation.foldersNotesScreen

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Folder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.components.CustomDialog
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.sql.Date

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FoldersNoteScreen(
    state: State<FoldersNotesScreenState>,
    onEvent: (FoldersNotesScreenEvent)->Unit
) {
    val isDialogState = remember{
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .background(WhiteRed)
    ) {
        LazyColumn(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            item() {
                state.value.note?.let {
                    it.notes?.let { notes ->
                        notes.forEach { note ->
                            DraggableScaffold(
                                contentUnderRight = {
                                    SwipeTrash(onTrashClick = {
                                        isDialogState.value = true
                                    })
                                },
                                contentUnderLeft = {
                                    SwipeEdit(onClick = {
                                        Constants.WHICH_NOTE.value = Constants.EDIT_NOTE
                                        coroutineScope.launch {
                                            if (state.value.folderId == note.folderId || note.folderId == -1) {
                                                note.id?.let { id ->
                                                    onEvent(
                                                        FoldersNotesScreenEvent.OnNoteIdChange(
                                                            id
                                                        )
                                                    )
                                                }
                                                onEvent(FoldersNotesScreenEvent.NavigateToEditNote)
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
                                                note.id?.let { id ->
                                                    onEvent(
                                                        FoldersNotesScreenEvent.OnNoteIdChange(
                                                            id
                                                        )
                                                    )
                                                    Constants.NOTE_TITLE = note.title
                                                    Constants.NOTE_DESCRIPTION = note.description
                                                    Constants.NOTE_DATE_TIME = note.dateCreated
                                                    onEvent(FoldersNotesScreenEvent.NavigateToShowDetails)
                                                }
                                            }
                                        }
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            CustomDialog(
                                text = "Attention",
                                description = "The entry will be permanently deleted",
                                okBtnClick =
                                {
                                    note.id?.let { id ->
                                        onEvent(FoldersNotesScreenEvent.OnNoteIdChange(id))
                                        onEvent(FoldersNotesScreenEvent.DeleteNoteById)
                                        Log.e("Delete", "Success")
                                        isDialogState.value = false
                                    }
                                },
                                cancelBtnClick = { isDialogState.value = false },
                                isDialogOpen = isDialogState,
                                okBtnText = "Delete",
                                cancelBtnText = "Cancel"
                            ) {
                                isDialogState.value = false
                            }
                        }
                    }
                }
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
            onEvent(FoldersNotesScreenEvent.NavigateToAddNote)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}