package com.ikcollab.budget.budget.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ikcollab.model.dto.budget.BudgetCategoryDto

@Composable
fun IncomeScreen(
    categories: List<BudgetCategoryDto>,
    total: Int,
    balance: Int,
) {
    Scaffold(modifier = Modifier.background(Color.Transparent), floatingActionButton = {
        FloatingActionButton(onClick = {

        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) {
        LazyColumn(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(it)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    categories.forEach { category ->

                       /* CategoryAddItem(
                            text = category.name,
                            onAddClick = { *//*TODO*//* },
                            onClick = {

                            }, getStorySumByType = get)*/
                    }
                    Column {
                        Row() {
                            Text(text = "Total")
                            Text(text = total.toString())
                        }
                        Row() {
                            Text(text = "Balance")
                            Text(text = balance.toString())
                        }
                    }
                }
            }
            item {
                Text(text = "Story")
            }
        }
    }
}