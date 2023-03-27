package com.ikcollab.goals.goalsScreen

import android.media.MediaDrm.OnEventListener
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.components.CustomFloatingActionButton
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.components.draggableScaffold.components.SwipeUndoneTrash
import com.ikcollab.goals.components.GoalItem
import com.ikcollab.goals.goalStepsScreen.GoalStepsEvent
import com.ikcollab.model.dto.goals.GoalDto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalsScreen(
    state: GoalsState,
    onEvent: (GoalsEvent) -> Unit,
) {
    val draggableState = rememberDismissState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(floatingActionButton = {
        CustomFloatingActionButton(
            onInsert = {
                onEvent(GoalsEvent.OpenBottomSheet)
            },
        )
    }) {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .padding(it)) {
            items(state.goals) { goal: GoalDto ->
                DraggableScaffold(
                    contentUnderRight = {
                        SwipeTrash(onTrashClick = {
                            onEvent(GoalsEvent.OnDeleteStepGoalClick(goal.id!!))
                            coroutineScope.launch {
                                draggableState.reset()
                            }
                        })
                    },
                    contentUnderLeft = {
                        SwipeEdit(onClick = {
                            // TODO
                        })
                    },
                    contentOnTop = {
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
                                onEvent(GoalsEvent.OpenGoalStepsScreen(goal.id!!))
                            },
                        )
                    }
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
}