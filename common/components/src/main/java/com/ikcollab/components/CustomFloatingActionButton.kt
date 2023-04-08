package com.ikcollab.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
@Composable
fun CustomFloatingActionButton(
    isMultiple: Boolean = false,
    onInsert: () -> Unit,
    onEdit: () -> Unit = {}
) {
    var scope = rememberCoroutineScope()
    var isSortVisible by remember {
        mutableStateOf(false)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedVisibility(
            visible = isSortVisible,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
            ),
            exit = slideOutVertically(
                targetOffsetY = { 250 },
                animationSpec = tween(durationMillis = 200, easing = FastOutLinearInEasing)
            )
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                FloatingActionButton(
                    backgroundColor = Color.White,
                    contentColor = Color(0xFF44DDBF),
                    modifier = Modifier.size(50.dp),
                    onClick = onInsert
                ) {
                    Icon(imageVector = Icons.Default.Folder, contentDescription = null)
                }

                Spacer(modifier = Modifier.height(8.dp))
                FloatingActionButton(
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.secondary,
                    modifier = Modifier.size(45.dp),
                    onClick = onEdit
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        FloatingActionButton(
            contentColor = Color.White,
            backgroundColor = MaterialTheme.colors.secondary,
            modifier = Modifier
                .size(60.dp),
            onClick = {
                if (isMultiple) {
                    isSortVisible = !isSortVisible
                } else {
                    onInsert()
                }
            },
            shape = CircleShape
        ) {
            Icon(
                imageVector = if (!isSortVisible) Icons.Default.Add else Icons.Default.Remove,
                contentDescription = null
            )
        }
    }
}