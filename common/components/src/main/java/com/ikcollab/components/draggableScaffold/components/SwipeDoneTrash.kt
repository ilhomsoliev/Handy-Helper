package com.ikcollab.components.draggableScaffold.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SwipeDoneTrash(
    onDoneClick:()->Unit,
    onTrashClick:()->Unit,
) {
    Box() {
        Row(modifier = Modifier.padding(horizontal = 12.dp)) {
            IconButton(onClick = onDoneClick) {
                Icon(
                    imageVector = Icons.Default.DoneAll,
                    tint = Color.Green,
                    contentDescription = null
                )
            }
            IconButton(onClick = onTrashClick) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    tint = Color.Red,
                    contentDescription = null
                )
            }
        }
    }
}
