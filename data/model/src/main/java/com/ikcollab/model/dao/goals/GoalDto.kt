package com.ikcollab.model.dao.goals

data class GoalDto(
    val id: Int,
    val name: String,
    val stepsCount:Int,
    val completedStepsCount:Int,
    val dateCreated: Long,
    val dateStart: Long,
    val dateEnd: Long,
)