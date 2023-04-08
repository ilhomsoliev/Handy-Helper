package com.ikcollab.budget.budget.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.core.toMMDDYYYY
import com.ikcollab.domain.usecase.budget.story.GetStorySumByCategoryId
import com.ikcollab.domain.usecase.budget.story.GetStorySumByType
import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.dto.budget.BudgetCategoryWithSumDto
import com.ikcollab.model.dto.budget.BudgetStoryDto
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpensesScreen(
    categories: List<BudgetCategoryDto>,
    stories: List<BudgetStoryDto>,
    total: String,
    balance: String,
    onAddClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    getStorySumByType: GetStorySumByCategoryId

) {
    val draggableState = rememberDismissState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            onAddClick(-1)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        categories.forEach { category ->
                            CategoryAddItem(
                                text = category.name,

                                onAddClick = {
                                    onAddClick(category.id!!)
                                },
                                onClick = {

                                }, categoryId = category.id!!, getStorySumByType = getStorySumByType
                            )
                        }
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Total:",
                                    fontSize = 22.sp,
                                    color = MaterialTheme.colors.onBackground,
                                    fontWeight = FontWeight.Bold,
                                )
                                Text(
                                    text = total,
                                    color = MaterialTheme.colors.secondary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                            }
                            if (balance.isNotEmpty()) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Balance:",
                                        fontSize = 22.sp,
                                        color = MaterialTheme.colors.onBackground,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Text(
                                        text = balance,
                                        color = MaterialTheme.colors.secondary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Story",
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(stories) { story ->
                DraggableScaffold(
                    contentUnderRight = {
                        SwipeTrash {
                            story.id?.let { it1 -> onDeleteClick(it1) }
                            coroutineScope.launch {
                                draggableState.reset()
                            }
                        }
                    },
                    contentUnderLeft = {
                        SwipeEdit(onClick = {
                            story.id?.let { it1 -> onEditClick(it1) }
                            coroutineScope.launch {
                                draggableState.reset()
                            }
                        })
                    },
                    contentOnTop = {
                        BudgetStoryItem(
                            category = story.categoryName,
                            comment = story.comment,
                            amount = "$${story.value}",
                            date = story.dateCreated.toMMDDYYYY()
                        )
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
            item{
                Spacer(modifier = Modifier.height(62.dp))
            }
        }
    }
}