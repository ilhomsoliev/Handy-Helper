package com.ikcollab.budget.category.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ikcollab.budget.category.components.BudgetCategoryItem
import com.ikcollab.model.dto.budget.BudgetCategoryDto

@Composable
fun BudgetCategoriesList(
    categories: List<BudgetCategoryDto>,
    onAddClick: () -> Unit
) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            onAddClick()
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item{ 
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(categories) {
                BudgetCategoryItem(text = it.name)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}