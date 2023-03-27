package com.ikcollab.goals.bottomSheetInsertGoal

sealed class BottomSheetInsertGoalEvent {
    object InsertGoalToDatabase : BottomSheetInsertGoalEvent()
    data class OnNewGoalNameChange(val value: String) : BottomSheetInsertGoalEvent()
    data class OnStartTimeChange(val value: Long) : BottomSheetInsertGoalEvent()
    data class OnEndTimeChange(val value: Long) : BottomSheetInsertGoalEvent()
}

sealed class BottomSheetInsertGoalOneTimeEvent {
    object CloseBottomSheet : BottomSheetInsertGoalOneTimeEvent()
}