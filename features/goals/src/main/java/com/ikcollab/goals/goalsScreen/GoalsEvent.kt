package com.ikcollab.goals.goalsScreen

import com.ikcollab.goals.goalStepsScreen.GoalStepsEvent

sealed class GoalsEvent {
    data class OpenGoalStepsScreen(val id:Int):GoalsEvent()
    data class OnDeleteStepGoalClick(val id: Int) : GoalsEvent()
    object OpenBottomSheet : GoalsEvent()

}