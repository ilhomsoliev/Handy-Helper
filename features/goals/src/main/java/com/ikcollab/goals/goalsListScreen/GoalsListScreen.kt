package com.ikcollab.goals.goalsListScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.goals.components.GoalWithStepsItem

@Composable
fun GoalsListScreen(viewModel: GoalsListScreenViewModel = hiltViewModel()) {

    val goalWithSteps = viewModel.goalsWithSteps

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 8.dp), content = {
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        items(goalWithSteps) {
            GoalWithStepsItem(goalName = it.name, it.steps)
            Spacer(modifier = Modifier.height(12.dp))
        }
    })
}