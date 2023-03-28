package com.ikcollab.goals.bottomSheetInsertStepGoal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.unit.dp
import com.ikcollab.components.DatePickerLabel
import com.ikcollab.components.ModalSheetDefaultStick
import com.ikcollab.components.ModalSheetTextField
import com.ikcollab.components.SendIcon
import com.ikcollab.components.datePicker.DatePicker
import com.ikcollab.core.toCurrentInMillis
import com.ikcollab.core.toMMDDYYYY
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomSheetInsertStepGoal(
    state: BottomSheetInsertStepGoalState,
    onEvent: (BottomSheetInsertStepGoalEvent) -> Unit,
) {
    val calendarState = rememberSheetState()

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
                .padding(12.dp),
            value = state.stepGoalValue,
            hint = "Add a new step",
            onValueChange = {
                onEvent(BottomSheetInsertStepGoalEvent.OnStepGoalValueChange(it))
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DatePickerLabel(
                date = if (state.deadline == 0L) "Deadline" else state.deadline.toMMDDYYYY(),
                onClick = {
                    calendarState.show()
                })
            SendIcon(
                modifier = Modifier
                    .focusTarget(),
                onClick = {
                    onEvent(BottomSheetInsertStepGoalEvent.OnAddClick)
                }
            )
        }
    }
    DatePicker(calendarState) {
        onEvent(BottomSheetInsertStepGoalEvent.OnDeadlineChange(it.toCurrentInMillis()))
    }
}