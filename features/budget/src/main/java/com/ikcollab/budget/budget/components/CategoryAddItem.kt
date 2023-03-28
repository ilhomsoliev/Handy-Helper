package com.ikcollab.budget.budget.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ikcollab.budget.components.AddIcon

@Composable
fun CategoryAddItem(
    text: String,
    balance: String,
    onAddClick: () -> Unit,
    onClick: () -> Unit,
) {
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
                Text(text = balance)
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