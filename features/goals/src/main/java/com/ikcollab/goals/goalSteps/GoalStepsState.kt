package com.ikcollab.goals.goalSteps

import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.goals.StepGoalDto

data class GoalStepsState(
    val isLoading: Boolean = false,
    val steps: List<StepGoalDto> = emptyList(),
    val goal: GoalDto? = null,
)