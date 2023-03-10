package com.ikcollab.model.dto.budget

data class BudgetStoryDto(
    val id: Int? = null,
    val comment: String,
    val value: String,
    val type: String,
    val dateCreated: Long,
    val categoryId: Int,
    val categoryName:String,
)