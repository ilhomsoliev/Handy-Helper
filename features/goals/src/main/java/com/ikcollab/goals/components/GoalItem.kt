package com.ikcollab.goals.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikcollab.components.ProgressIndicator
import com.ikcollab.core.toMMDDYYYY

@Composable
fun GoalItem(
    modifier: Modifier = Modifier,
    title: String,
    start: Long,
    end: Long,
    stepsCount: Int,
    stepsCompletedCount: Int,
    daysLeft: Long,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(MaterialTheme.colors.primary)
        .clickable {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Start:")
                Text(text = end.toMMDDYYYY())
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Deadline:")
                Text(text = end.toMMDDYYYY())
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Steps:")
                Text(text = "$stepsCompletedCount/$stepsCount")
            }
            ProgressIndicator(
                progress = (stepsCompletedCount.toFloat() / if (stepsCount.toFloat() == 0f) 1f else stepsCount.toFloat()).toFloat()
            )
            // Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Days left:")
                Text(text = "0/0")
            }
            ProgressIndicator(
                progress = (stepsCompletedCount / if (stepsCount == 0) 1 else stepsCount).toFloat()
            )
        }
    }
}