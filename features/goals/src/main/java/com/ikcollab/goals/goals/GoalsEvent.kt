package com.ikcollab.goals.goals

sealed class GoalsEvent {
    data class OpenGoalStepsScreen(val id:Int):GoalsEvent()
    data class OnDeleteStepGoalClick(val id: Int) : GoalsEvent()
    object OpenBottomSheet : GoalsEvent()

}