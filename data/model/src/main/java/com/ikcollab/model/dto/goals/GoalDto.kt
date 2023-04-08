package com.ikcollab.model.dto.goals

data class GoalDto(
    val id: Int? = null,
    val name: String = "",
    val stepsCount:Int = 0,
    val completedStepsCount:Int = 0,
    val dateCreated: Long = System.currentTimeMillis(),
    val dateStart: Long = System.currentTimeMillis(),
    val dateEnd: Long = System.currentTimeMillis(),
)