package com.ikcollab.goals.goalStepsScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeDoneTrash
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeUndoneTrash
import com.ikcollab.goals.components.GoalDiagram
import com.ikcollab.goals.components.StepGoalItem

@Composable
fun GoalStepsScreen(
    goalId: Int,
    viewModel: GoalStepsScreenViewModel = hiltViewModel()
) {
    val stepsGoal = viewModel.stepsGoal
    val goal by viewModel.goal

    LaunchedEffect(key1 = false, block = {
        viewModel.getStepsGoal(goalId)
        viewModel.getGoalById(goalId)
    })

    if (stepsGoal.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Break your goal down into steps",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Text(textAlign = TextAlign.Center,text = "The steps of the goal are the transition from one phase to another. When the goal is too far way, steps act as signposts that allow you to track your progress and make sure you are on the right track.")
            }
        }
    } else {
        goal?.let { goal ->
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
                    Text(modifier = Modifier.padding(vertical = 6.dp),text = "Pending:", fontWeight = FontWeight.Bold, fontSize = 21.sp)
                }
                items(stepsGoal.filter { !it.isCompleted }) {
                    DraggableScaffold(
                        contentUnderRight = {
                            SwipeDoneTrash(onDoneClick = {
                                viewModel.markAsCompleteStepGoal(it, onDone = {
                                    viewModel.getGoalById(goalId)
                                })
                            }, onTrashClick = {
                                viewModel.deleteStepGoal(it, onDone = {
                                    viewModel.getGoalById(goalId)
                                })
                            })
                        },
                        contentUnderLeft = {
                            SwipeEdit(onClick = {
                                // TODO
                            })
                        },
                        contentOnTop = {
                            StepGoalItem(
                                isCompleted = it.isCompleted,
                                stepGaolContent = it.name,
                                deadline = it.dateCreated
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Text(modifier = Modifier.padding(vertical = 6.dp),text = "Completed:", fontWeight = FontWeight.Bold, fontSize = 21.sp)
                }
                items(stepsGoal.filter { it.isCompleted }) {
                    DraggableScaffold(
                        contentUnderRight = {
                            SwipeUndoneTrash(onUndoneClick = {
                                viewModel.markAsNotCompleteStepGoal(it, onDone = {
                                    viewModel.getGoalById(goalId)
                                })
                            }, onTrashClick = {
                                viewModel.deleteStepGoal(it, onDone = {
                                    viewModel.getGoalById(goalId)
                                })
                            })
                        },
                        contentUnderLeft = {
                            SwipeEdit(onClick = {
                                // TODO
                            })
                        },
                        contentOnTop = {
                            StepGoalItem(
                                isCompleted = it.isCompleted,
                                stepGaolContent = it.name,
                                deadline = it.dateCreated
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }
    }
}