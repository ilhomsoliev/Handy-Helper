package com.ikcollab.model.dto.budget

data class BudgetCategoryDto(
    val id: Int,
    val name: String,
    val dateCreated: Long,
    val type: String,
)