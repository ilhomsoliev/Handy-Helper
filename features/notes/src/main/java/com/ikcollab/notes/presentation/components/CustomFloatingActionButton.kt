package com.ikcollab.notes.presentation.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CustomFloatingActionButton() {
    var scope = rememberCoroutineScope()
    var shapeState by remember { mutableStateOf(RoundedCornerShape(18.dp)) }
    var isSortVisible by remember {
        mutableStateOf(false)
    }
    Column() {
        AnimatedVisibility(visible = isSortVisible) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                FloatingActionButton(
                    backgroundColor = Color.White,
                    contentColor = Color.Yellow,
                    modifier = Modifier.size(50.dp),
                    onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Folder, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(8.dp))
                FloatingActionButton(
                    backgroundColor = Color.White,
                    contentColor = Color.Red,
                    modifier = Modifier.size(45.dp),
                    onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                }
                Spacer(modifier = Modifier.height(8.dp))
                scope.launch {
                    shapeState = CircleShape
                }
            }
        }
        scope.launch {
            if (!isSortVisible) {
                shapeState = RoundedCornerShape(18.dp)
            }
        }
        FloatingActionButton(
            contentColor = Color.White,
            backgroundColor = Color.Red,
            modifier = Modifier
                .size(60.dp),
            onClick = { isSortVisible = !isSortVisible },
            shape = shapeState
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }
}