package com.ikcollab.notes.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikcollab.notes.presentation.theme.Gray
import com.ikcollab.notes.presentation.theme.Orange
import com.ikcollab.notes.presentation.theme.WhiteNotesCategory

@Composable
fun CustomNotesCategory(
    onClick:()->Unit,
    icon:ImageVector,
    title:String,
    number:Int
) {
    Box(modifier =  Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(WhiteNotesCategory)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(tint = Orange,modifier = Modifier.size(30.dp), imageVector = icon, contentDescription = null)
                Text(modifier=Modifier.padding(start = 8.dp),text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Text("$number", color = Gray, fontSize = 20.sp)
        }
    }
}