package com.ikcollab.model.dto.goals

data class StepGoalDto(
    val id: Int? = null,
    val name: String,
    val isCompleted: Boolean,
    val dateCreated: Long,
    val deadline: Long,
    val goalId:Int
)
