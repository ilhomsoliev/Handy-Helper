package com.ikcollab.model.dto.budget

data class BudgetStoryDto(
    val id: Int? = null,
    val comment: String = "",
    val value: Int = 0,
    val type: String = "",
    val dateCreated: Long = 0,
    val categoryId: Int = -1,
    val categoryName:String = "",
)