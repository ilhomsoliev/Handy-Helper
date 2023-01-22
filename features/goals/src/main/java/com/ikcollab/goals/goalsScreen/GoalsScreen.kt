package com.ikcollab.goals.goalsScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.goals.components.GoalItem
import com.ikcollab.model.dto.goals.GoalDto

@Composable
fun GoalsScreen(
    openGoalStepsScreen:(Int)->Unit,
    viewModel: GoalsScreenViewModel = hiltViewModel()
) {
    val goals = viewModel.goals

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(goals) { goal: GoalDto ->
            GoalItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                title = goal.name,
                start = goal.dateStart,
                end = goal.dateEnd,
                stepsCount = goal.stepsCount,
                stepsCompletedCount = goal.completedStepsCount,
                daysLeft = goal.dateEnd - goal.dateStart,
                onClick = {
                    openGoalStepsScreen(goal.id!!)
                },
            )
        }
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Swipe left to delete Goal, right to edit"
            )
        }
    }

}