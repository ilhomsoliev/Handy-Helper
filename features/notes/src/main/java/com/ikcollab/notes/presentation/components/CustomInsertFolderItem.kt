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

@Composable
fun CustomInsertFolderItem(
    value:String,
    onValueChange:(String)->Unit,
    onClick:()->Unit,
    placeholder: String,
    focus:MutableState<Boolean>,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomInsertFolderTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = placeholder,
                focus = focus
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