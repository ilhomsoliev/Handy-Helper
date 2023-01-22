package com.ikcollab.goals.goalStepsScreen

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.components.DraggableCard.ActionsRow
import com.ikcollab.components.DraggableCard.CardsScreenViewModel
import com.ikcollab.components.DraggableCard.DraggableCard
import com.ikcollab.components.draggableScaffold.DraggableScaffold
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
            Column() {
                Text(
                    text = "Break your goal down into steps",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Text(text = "The steps of the goal are the transition from one phase to another. When the goal is too far way, steps act as signposts that allow you to track your progress and make sure you are on the right track.")
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
                    Text(text = goal.name, fontWeight = FontWeight.Bold)
                }
                item {
                    // Diagram
                    GoalDiagram(
                        modifier = Modifier,
                        pending = goal.stepsCount - goal.completedStepsCount,
                        completed = goal.completedStepsCount,
                        totalSteps = goal.stepsCount
                    )
                }
                item {
                    Text(text = "Pending")
                }
                items(stepsGoal.filter { !it.isCompleted }) {
                    DraggableScaffold(
                        contentUnderRight = {
                            Box() {
                                Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                    IconButton(onClick = {
                                        // onEdit
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.DoneAll,
                                            tint = Color.Green,
                                            contentDescription = null
                                        )
                                    }
                                    IconButton(onClick = {
                                        // onDelete
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            tint = Color.Red,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        },
                        contentUnderLeft = {
                            Box() {
                                IconButton(
                                    modifier = Modifier.padding(horizontal = 12.dp),
                                    onClick = {
                                        // onEdit
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null
                                    )
                                }
                            }
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
                    Text(text = "Completed")
                }
                items(stepsGoal.filter { it.isCompleted }) {
                    StepGoalItem(
                        isCompleted = it.isCompleted,
                        stepGaolContent = it.name,
                        deadline = it.dateCreated
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }
    }
}