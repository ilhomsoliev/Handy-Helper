package com.ikcollab.model.dto.goals

class GoalWithStepsDto(
    val id: Int? = null,
    val name: String,
    val dateCreated: Long,
    val steps:List<StepGoalDto>
)