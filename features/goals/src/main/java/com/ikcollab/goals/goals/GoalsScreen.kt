package com.ikcollab.goals.goals

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.ikcollab.components.draggableScaffold.ExpandState
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.components.draggableScaffold.rememberDraggableScaffoldState
import com.ikcollab.goals.components.GoalItem
import com.ikcollab.model.dto.goals.GoalDto
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GoalsScreen(
    state: GoalsState,
    onEvent: (GoalsEvent) -> Unit,
) {
    //val draggableState = rememberDraggableScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                onEvent(GoalsEvent.OpenBottomSheet)
            }
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            if (state.goals.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(it)
                ) {
                    items(state.goals) { goal: GoalDto ->
                        val draggableDismissState = rememberDismissState()

                        DraggableScaffold(
                            contentUnderRight = {
                                SwipeTrash(onTrashClick = {
                                    onEvent(GoalsEvent.OnDeleteStepGoalClick(goal.id!!))
                                    coroutineScope.launch {
                                        draggableDismissState.reset()
                                    }
                                })
                            },
                            contentUnderLeft = {
                                SwipeEdit(onClick = {
                                    coroutineScope.launch {
                                        draggableDismissState.reset()
                                    }
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
                                    days = ChronoUnit.DAYS.between(
                                        LocalDateTime.ofInstant(
                                            Instant.ofEpochMilli(goal.dateStart),
                                            ZoneId.systemDefault()
                                        ), LocalDateTime.ofInstant(
                                            Instant.ofEpochMilli(goal.dateEnd),
                                            ZoneId.systemDefault()
                                        )
                                    ),
                                    daysLeft = ChronoUnit.DAYS.between(
                                        LocalDateTime.ofInstant(
                                            Instant.ofEpochMilli(System.currentTimeMillis()),
                                            ZoneId.systemDefault()
                                        ), LocalDateTime.ofInstant(
                                            Instant.ofEpochMilli(goal.dateEnd),
                                            ZoneId.systemDefault()
                                        )
                                    ),
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
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "You don't have any goals yet.",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Goals have to be clear, simple, and in writing. If they aren't in writing and reviwed daily, they aren't really goals. They're wishes."
                        )
                    }
                }
            }
        }
    }
}