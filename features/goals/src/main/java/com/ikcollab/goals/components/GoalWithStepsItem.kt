package com.ikcollab.goals.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeUndoneTrash
import com.ikcollab.model.dto.goals.StepGoalDto

@Composable
fun GoalWithStepsItem(
    goalName: String,
    steps: List<StepGoalDto>,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Text(text = goalName, fontWeight = FontWeight.Bold, fontSize = 21.sp)
            steps.filter { !it.isCompleted }.forEach {
                DraggableScaffold(
                    contentUnderRight = {
                        SwipeUndoneTrash(onUndoneClick = {
                            /*viewModel.markAsNotCompleteStepGoal(it, onDone = {
                                viewModel.getGoalById(goalId)
                            })*/
                        }, onTrashClick = {
                            /*viewModel.deleteStepGoal(it, onDone = {
                                viewModel.getGoalById(goalId)
                            })*/
                        })
                    },
                    contentUnderLeft = {
                        SwipeEdit(onClick = {
                            // TODO
                        })
                    },
                    contentOnTop = {
                        GoalSubStepItem(content = it.name, isCompleted = it.isCompleted)
                    }
                )
            }
            steps.filter { it.isCompleted }.forEach {
                DraggableScaffold(
                    contentUnderRight = {
                        SwipeUndoneTrash(onUndoneClick = {
                            /*viewModel.markAsNotCompleteStepGoal(it, onDone = {
                                viewModel.getGoalById(goalId)
                            })*/
                        }, onTrashClick = {
                            /*viewModel.deleteStepGoal(it, onDone = {
                                viewModel.getGoalById(goalId)
                            })*/
                        })
                    },
                    contentUnderLeft = {
                        SwipeEdit(onClick = {
                            // TODO
                        })
                    },
                    contentOnTop = {
                        GoalSubStepItem(content = it.name, isCompleted = it.isCompleted)
                    }
                )
            }
        }
    }
}

@Composable
fun GoalSubStepItem(
    content: String,
    isCompleted: Boolean,
) {
    Row(modifier = Modifier.fillMaxWidth().background(Color.White), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(Color.Black)
                //.padding(end = 16.dp)
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = content,
            color = if (isCompleted) Color.Gray else Color.Unspecified,
            style = if (isCompleted) TextStyle(textDecoration = TextDecoration.LineThrough) else LocalTextStyle.current
        )
    }
}