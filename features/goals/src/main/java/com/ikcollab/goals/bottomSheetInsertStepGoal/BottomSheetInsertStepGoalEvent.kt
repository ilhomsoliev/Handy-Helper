package com.ikcollab.goals.bottomSheetInsertStepGoal

import com.ikcollab.goals.goalStepsScreen.GoalStepsEvent

sealed class BottomSheetInsertStepGoalEvent {
    data class OnStepGoalValueChange(val value: String) : BottomSheetInsertStepGoalEvent()
    object OnAddClick : BottomSheetInsertStepGoalEvent()
    data class OnDeadlineChange(val value: Long) : BottomSheetInsertStepGoalEvent()
    data class OnInit(val goalId: Int) : BottomSheetInsertStepGoalEvent()

}

sealed class BottomSheetInsertStepGoalOneTimeEvent {
    object CloseBottomSheet : BottomSheetInsertStepGoalOneTimeEvent()
}