package com.ikcollab.notes.presentation.bottomSheetInsertFolder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ikcollab.components.ModalSheetDefaultStick
import com.ikcollab.core.Constants
import com.ikcollab.notes.presentation.components.CustomInsertFolderItem

@Composable
fun BottomSheetInsertFolder(
    state:BottomSheetInsertFolderState,
    onEvent:(BottomSheetInsertFolderEvent)->Unit
) {
    val stateOfKeyboard = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true, block = {
        stateOfKeyboard.value = true
    })
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomInsertFolderItem(
            value = state.folderName,
            onValueChange = {
                onEvent(BottomSheetInsertFolderEvent.OnFolderNameChange(it))
            },
            onClick = {
                onEvent(BottomSheetInsertFolderEvent.InsertFolder)
                stateOfKeyboard.value = false
            },
            placeholder = "Name of Folder...",
            modifier = Modifier,
                stateOfKeyboard = stateOfKeyboard
        )
    }
}