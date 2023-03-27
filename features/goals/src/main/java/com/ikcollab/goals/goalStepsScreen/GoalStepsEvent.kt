package com.ikcollab.goals.goalStepsScreen

sealed class GoalStepsEvent {
    data class OnInit(val goalId: Int) : GoalStepsEvent()
    data class OnMarkAsCompletedClick(val id: Int) : GoalStepsEvent()
    data class OnMarkNotAsCompletedClick(val id: Int) : GoalStepsEvent()
    data class OnDeleteStepGoalClick(val id: Int) : GoalStepsEvent()
    object OpenBottomSheet: GoalStepsEvent()
}