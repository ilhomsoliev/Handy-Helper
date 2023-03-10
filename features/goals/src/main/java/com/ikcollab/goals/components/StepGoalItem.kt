package com.ikcollab.goals.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.ikcollab.core.toMMDDYYYY
import com.ikcollab.core.toMMMDD

@Composable
fun StepGoalItem(
    isCompleted: Boolean,
    stepGaolContent: String,
    deadline: Long,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colors.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stepGaolContent,
                fontFamily = FontFamily.SansSerif,
                color = if (isCompleted) Color.Gray else Color.Unspecified,
                style = if (isCompleted) TextStyle(textDecoration = TextDecoration.LineThrough) else LocalTextStyle.current
            )

            Text(text = deadline.toMMMDD().toString())
        }
    }
}