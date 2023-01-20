package com.ikcollab.model.dto.budget

data class BudgetCategoryDto(
    val id: Int? = null,
    val name: String,
    val dateCreated: Long,
    val type: String,
)