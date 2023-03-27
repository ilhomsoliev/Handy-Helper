package com.ikcollab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color

@Composable
fun SendIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {

    IconButton(modifier = modifier.clip(CircleShape).background(MaterialTheme.colors.primary), onClick = {
        onClick()
    }) {
        Icon(imageVector = Icons.Default.Send, contentDescription = null)
    }

}