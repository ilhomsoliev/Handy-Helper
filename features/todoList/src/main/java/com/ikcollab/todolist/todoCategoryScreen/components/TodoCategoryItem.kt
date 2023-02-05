package com.ikcollab.todolist.todoCategoryScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun TodoCategoryItem(
    modifier: Modifier = Modifier,
    title: String
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(width = 12.dp, color = Color.Blue, shape = CircleShape)
                    .background(Color.Blue)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title)
        }
    }
}