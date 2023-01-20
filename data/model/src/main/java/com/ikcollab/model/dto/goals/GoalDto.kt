package com.ikcollab.model.dto.goals

data class GoalDto(
    val id: Int? = null,
    val name: String,
    val stepsCount:Int,
    val completedStepsCount:Int,
    val dateCreated: Long,
    val dateStart: Long,
    val dateEnd: Long,
)