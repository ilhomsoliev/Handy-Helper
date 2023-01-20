package com.ikcollab.goals.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ikcollab.components.ModalSheetDefaultStick
import com.ikcollab.components.ModalSheetTextField

@Composable
fun BottomSheetInsertGoal(
    goalValue:String,
    onGoalValueChange:(String)->Unit,
    start:Long,
    deadline:Long,
    onAddClick:()->Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ModalSheetDefaultStick()
        ModalSheetTextField(value = goalValue, hint = "Enter a goal", onValueChange = onGoalValueChange)
    }
}