package com.ikcollab.budget.budget

import com.ikcollab.model.dto.budget.BudgetCategoryDto

data class BudgetState(
    val isLoading:Boolean = false,
    val expensesCategories: List<BudgetCategoryDto> = emptyList(),
    val incomeCategories: List<BudgetCategoryDto> = emptyList(),
)