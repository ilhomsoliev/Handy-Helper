package com.ikcollab.todolist.components.bottomSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ikcollab.components.ModalSheetDatePicker
import com.ikcollab.components.ModalSheetDefaultStick
import com.ikcollab.components.ModalSheetTextField

@Composable
fun BottomSheetInsertTodoTask(
    taskValue: String,
    onTaskValueChange: (String) -> Unit,
    deadline: Long,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ModalSheetDefaultStick(
            modifier = Modifier.padding(top = 16.dp)
        )
        ModalSheetTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            value = taskValue,
            hint = "Enter a goal",
            onValueChange = onTaskValueChange
        )
        ModalSheetDatePicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            title = "Start",
            value = "11/12/12",
            onValueChange = {

            })
        ModalSheetDatePicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            title = "Deadline",
            value = "11/12/12",
            onValueChange = {

            })

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp), onClick = {
            onAddClick()
        }) {
            Text(text = "Add")
        }
    }
}