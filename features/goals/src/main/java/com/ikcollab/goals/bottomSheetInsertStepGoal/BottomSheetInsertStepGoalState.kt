package com.ikcollab.goals.bottomSheetInsertStepGoal

data class BottomSheetInsertStepGoalState(
    val goalId:Int = 0,
    val stepGoalValue:String = "",
    val deadline:Long = System.currentTimeMillis(),

)