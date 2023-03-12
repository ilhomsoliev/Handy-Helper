package com.ikcollab.notes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomNotesItem(
    title:String,
    description:String,
    dateTime:String,
    onItemClick:()->Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(MaterialTheme.colors.primary)
        .clickable {
            onItemClick()
        }
    ){
        Column(modifier=Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp)) {
            Row(modifier = Modifier
                .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Start)
                Text(dateTime, fontSize = 12.sp, textAlign = TextAlign.End)
            }
            Text(text = description)
        }
    }
}