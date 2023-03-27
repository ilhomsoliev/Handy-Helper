package com.ikcollab.goals.bottomSheetInsertGoal

sealed class BottomSheetInsertGoalEvent {
    object InsertGoalToDatabase : BottomSheetInsertGoalEvent()
    data class OnNewGoalNameChange(val value: String) : BottomSheetInsertGoalEvent()
}

sealed class BottomSheetInsertGoalOneTimeEvent {
    object CloseBottomSheet : BottomSheetInsertGoalOneTimeEvent()
}