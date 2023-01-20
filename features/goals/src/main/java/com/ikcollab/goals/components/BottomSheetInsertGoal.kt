package com.ikcollab.goals.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
        ModalSheetDefaultStick(
            modifier = Modifier.padding(top = 16.dp)
        )
        ModalSheetTextField(value = goalValue, hint = "Enter a goal", onValueChange = onGoalValueChange)
        Button(onClick = {
            onAddClick()
        }) {
            Text(text = "Add")
        }
    }
}