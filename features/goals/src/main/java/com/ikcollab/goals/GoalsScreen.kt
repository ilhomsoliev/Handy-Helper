package com.ikcollab.goals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.goals.components.GoalItem

@Composable
fun GoalsScreen(
    viewModel: GoalsScreenViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        repeat(5) {
            GoalItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                title = "Title",
                start = 123123,
                end = 1234123,
                stepsCount = 12,
                stepsCompletedCount = 6,
                daysLeft = 123,
            )
        }

    }
}