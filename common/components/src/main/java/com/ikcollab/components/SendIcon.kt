package com.ikcollab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun SendIcon(
    onClick: () -> Unit,
) {
    IconButton(onClick = {
        onClick()
    }, Modifier.clip(CircleShape).background(Color.Blue)) {
        Icon(imageVector = Icons.Default.Send, contentDescription = null)
    }

}