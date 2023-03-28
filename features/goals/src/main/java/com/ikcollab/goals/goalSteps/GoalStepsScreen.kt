package com.ikcollab.goals.goalSteps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikcollab.components.CustomFloatingActionButton
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeDoneTrash
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeUndoneTrash
import com.ikcollab.core.toMMMDD
import com.ikcollab.goals.components.GoalDiagram
import com.ikcollab.goals.components.StepGoalItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalStepsScreen(
    state: GoalStepsState,
    onEvent: (GoalStepsEvent) -> Unit,
) {
    val draggableState = rememberDismissState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        CustomFloatingActionButton(onInsert = {
            onEvent(GoalStepsEvent.OpenBottomSheet)
        })
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (state.steps.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Break your goal down into steps",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = "The steps of the goal are the transition from one phase to another. When the goal is too far way, steps act as signposts that allow you to track your progress and make sure you are on the right track."
                        )
                    }
                }
            }
            state.goal?.let { goal ->
                if (state.steps.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                    ) {
                        item {
                            Text(text = goal.name, fontWeight = FontWeight.Bold, fontSize = 28.sp)
                        }
                        item {
                            GoalDiagram(
                                modifier = Modifier,
                                pending = goal.stepsCount - goal.completedStepsCount,
                                completed = goal.completedStepsCount,
                                totalSteps = goal.stepsCount
                            )
                        }
                        item {
                            Text(
                                modifier = Modifier.padding(vertical = 6.dp),
                                text = "Pending:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp
                            )
                        }
                        items(state.steps.filter { !it.isCompleted }) { step ->
                            DraggableScaffold(
                                contentUnderRight = {
                                    SwipeDoneTrash(onDoneClick = {
                                        onEvent(GoalStepsEvent.OnMarkAsCompletedClick(step.id!!))
                                        coroutineScope.launch {
                                            draggableState.reset()
                                        }
                                    }, onTrashClick = {
                                        onEvent(GoalStepsEvent.OnDeleteStepGoalClick(step.id!!))
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
                                    StepGoalItem(
                                        isCompleted = step.isCompleted,
                                        stepGaolContent = step.name,
                                        deadline = if(step.dateCreated == 0L) "" else step.dateCreated.toMMMDD()
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        item {
                            Text(
                                modifier = Modifier.padding(vertical = 6.dp),
                                text = "Completed:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp
                            )
                        }
                        items(state.steps.filter { it.isCompleted }) { step ->
                            DraggableScaffold(
                                contentUnderRight = {
                                    SwipeUndoneTrash(onUndoneClick = {
                                        onEvent(GoalStepsEvent.OnMarkNotAsCompletedClick(step.id!!))
                                        coroutineScope.launch {
                                            draggableState.reset()
                                        }
                                    }, onTrashClick = {
                                        onEvent(GoalStepsEvent.OnDeleteStepGoalClick(step.id!!))
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
                                    StepGoalItem(
                                        isCompleted = step.isCompleted,
                                        stepGaolContent = step.name,
                                        deadline = if(step.dateCreated == 0L) "" else step.dateCreated.toMMMDD()
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                        }
                    }
                }
            }
        }
    }
}