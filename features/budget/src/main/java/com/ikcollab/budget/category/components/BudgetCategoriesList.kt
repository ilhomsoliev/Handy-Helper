package com.ikcollab.budget.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ikcollab.budget.category.components.BudgetCategoryItem
import com.ikcollab.components.draggableScaffold.DraggableScaffold
import com.ikcollab.components.draggableScaffold.components.SwipeEdit
import com.ikcollab.components.draggableScaffold.components.SwipeTrash
import com.ikcollab.model.dto.budget.BudgetCategoryDto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BudgetCategoriesList(
    categories: List<BudgetCategoryDto>,
    onDeleteClick: (Int,String) -> Unit,
    onEditClick: (Int) -> Unit,
    onAddClick: () -> Unit
) {
    val draggableState = rememberDismissState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            onAddClick()
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(categories) {
                DraggableScaffold(
                    contentUnderRight = {
                        SwipeTrash {
                            onDeleteClick(it.id!!,it.name)
                            coroutineScope.launch {
                                draggableState.reset()
                            }
                        }
                    },
                    contentUnderLeft = {
                        SwipeEdit(onClick = {
                            it.id?.let { it1 -> onEditClick(it1) }
                            coroutineScope.launch {
                                draggableState.reset()
                            }
                        })
                    },
                    contentOnTop = {
                        BudgetCategoryItem(text = it.name)
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))
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