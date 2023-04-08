package com.ikcollab.domain.usecase.budget.category

import com.ikcollab.repository.budget.BudgetRepository

import javax.inject.Inject

class DeleteBudgetCategoryByIdUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(id:Int) {
        repository.deleteCategoryById(id)
        repository.deleteAllBudgetByCategoryId(id)
    }
}