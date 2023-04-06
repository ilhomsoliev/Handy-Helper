package com.ikcollab.budget.budget.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ikcollab.budget.components.AddIcon
import com.ikcollab.core.showLog
import com.ikcollab.domain.usecase.budget.story.GetStorySumByCategoryId
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CategoryAddItem(
    text: String,
    categoryId: Int,
    onAddClick: () -> Unit,
    onClick: () -> Unit,
    getStorySumByType: GetStorySumByCategoryId
) {
    var sum by remember { mutableStateOf(0.0) }
    val coroutine = rememberCoroutineScope()
    LaunchedEffect(key1 = false, block = {
        getStorySumByType(categoryId).onEach {
            sum = it ?: 0.0
        }.launchIn(coroutine)
    })
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
        .clickable {
            onClick()
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = sum.toString())
                Spacer(modifier = Modifier.width(6.dp))
                AddIcon {
                    onAddClick()
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Divider(thickness = 2.dp)
    }
}