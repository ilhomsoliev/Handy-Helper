package com.ikcollab.domain.usecase.budget.category

import com.ikcollab.model.dto.budget.BudgetCategoryDto
import com.ikcollab.model.dto.toBudgetCategoryEntity
import com.ikcollab.repository.budget.BudgetRepository
import javax.inject.Inject

class InsertBudgetCategoryUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budgetCategoryDto: BudgetCategoryDto) {
        repository.insertBudgetCategory(budgetCategoryDto.toBudgetCategoryEntity())
    }
}