package com.ikcollab.goals.bottomSheetInsertGoal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ikcollab.components.ModalSheetDatePicker
import com.ikcollab.components.ModalSheetDefaultStick
import com.ikcollab.components.ModalSheetTextField
import com.ikcollab.components.datePicker.DatePicker
import com.ikcollab.core.toMMDDYYYY
import com.ikcollab.goals.bottomSheetInsertStepGoal.BottomSheetInsertStepGoalEvent
import com.maxkeppeker.sheets.core.models.base.rememberSheetState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomSheetInsertGoal(
    state: BottomSheetInsertGoalState,
    onEvent: (BottomSheetInsertGoalEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ModalSheetDefaultStick(
            modifier = Modifier.padding(top = 16.dp)
        )
        ModalSheetTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            value = state.goalName,
            hint = "Enter a goal",
            onValueChange = {
                onEvent(BottomSheetInsertGoalEvent.OnNewGoalNameChange(it))
            }
        )

        ModalSheetDatePicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            title = "Start",
            value = state.goalStartDate.toMMDDYYYY(),
            onValueChange = {
                onEvent(BottomSheetInsertGoalEvent.OnStartTimeChange(it))
            })

        ModalSheetDatePicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),

            title = "Deadline",
            value = state.goalEndDate.toMMDDYYYY(),
            onValueChange = {
                onEvent(BottomSheetInsertGoalEvent.OnEndTimeChange(it))

            })

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 12.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                onEvent(BottomSheetInsertGoalEvent.InsertGoalToDatabase)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(modifier = Modifier.padding(4.dp), fontSize = 18.sp, text = "Add")
        }
    }
}