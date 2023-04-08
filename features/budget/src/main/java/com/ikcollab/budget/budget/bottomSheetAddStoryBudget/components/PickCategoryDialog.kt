package com.ikcollab.budget.budget.bottomSheetAddStoryBudget.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ikcollab.model.dto.budget.BudgetCategoryDto

@Composable
fun PickCategoryDialog(
    categories: List<BudgetCategoryDto>,
    onCategoryPicked: (BudgetCategoryDto) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Choose a category",
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 22.sp,
                )
                categories.forEach {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onCategoryPicked(it)
                        }) {
                        Text(
                            text = it.name,
                            fontSize = 18.sp,
                            color = MaterialTheme.colors.onPrimary,
                        )
                        Divider()
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

}