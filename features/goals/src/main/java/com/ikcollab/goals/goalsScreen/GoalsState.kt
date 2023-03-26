package com.ikcollab.goals.goalsScreen

import com.ikcollab.model.dto.goals.GoalDto

data class GoalsState(
    val isLoading:Boolean = false,
    val goals:List<GoalDto> = emptyList()
)