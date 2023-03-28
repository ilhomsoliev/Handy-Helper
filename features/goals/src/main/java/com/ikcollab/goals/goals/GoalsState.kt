package com.ikcollab.goals.goals

import com.ikcollab.model.dto.goals.GoalDto

data class GoalsState(
    val isLoading:Boolean = false,
    val goals:List<GoalDto> = emptyList()
)