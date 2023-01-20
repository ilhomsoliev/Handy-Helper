package com.ikcollab.notes.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ikcollab.components.ModalSheetDefaultStick

@Composable
fun CustomInsertFolderItem(
    value:String,
    onValueChange:(String)->Unit,
    onClick:()->Unit,
    placeholder: String,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        ModalSheetDefaultStick()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomInsertFolderTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = placeholder
            )
            FloatingActionButton(
                backgroundColor= Color.Red,
                onClick = onClick,
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
            }
        }
    }
}