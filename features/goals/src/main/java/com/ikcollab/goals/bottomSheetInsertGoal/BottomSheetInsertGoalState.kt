package com.ikcollab.goals.bottomSheetInsertGoal

data class BottomSheetInsertGoalState(
    val goalName: String = "",
    val goalStartDate: Long = System.currentTimeMillis(),
    val goalEndDate: Long = System.currentTimeMillis(),

    )