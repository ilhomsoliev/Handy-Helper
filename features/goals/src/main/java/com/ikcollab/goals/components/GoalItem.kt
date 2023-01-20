package com.ikcollab.goals.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GoalItem(
    modifier:Modifier = Modifier,
    title:String,
    start:Long,
    end:Long,
    stepsCount:Int,
    stepsCompletedCount:Int,
    daysLeft:Int,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(Color.White)
    ){
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Start:")
                Text(text = start.toString())
            }
            Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Deadline:")
                Text(text = end.toString())
            }
            Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Steps:")
                Text(text = "$stepsCompletedCount/$stepsCount")
            }
            // Bar
            Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                Text(text = "Days left:")
                Text(text = "0/0")
            }
        }
    }

}