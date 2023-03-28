package com.ikcollab.budget.budget.bottomSheetAddStoryBudget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ikcollab.components.ModalSheetDefaultStick

@Composable
fun BottomSheetAddStoryBudget(
    state: BottomSheetAddStoryBudgetState,
    onEvent: (BottomSheetAddStoryBudgetEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ModalSheetDefaultStick(
            modifier = Modifier.padding(top = 16.dp)
        )

    }

}