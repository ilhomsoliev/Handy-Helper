package com.ikcollab.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DatePickerLabel(
    date: String,
    onClick: () -> Unit,
) {
    Box(modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(Color.White)
        .clickable { onClick() }) {
        Row(modifier = Modifier.padding(6.dp)) {
            Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null)
            Text(modifier = Modifier.padding(start = 6.dp),text = date)
        }
    }
}