package com.ikcollab.budget.budget

import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.dto.budget.BudgetStoryDto

data class BudgetState(
    val isLoading: Boolean = false,
    val expensesCategories: List<BudgetCategoryDto> = emptyList(),
    val expensesStories: List<BudgetStoryDto> = emptyList(),
    val incomeCategories: List<BudgetCategoryDto> = emptyList(),
    val incomeStories: List<BudgetStoryDto> = emptyList(),
)