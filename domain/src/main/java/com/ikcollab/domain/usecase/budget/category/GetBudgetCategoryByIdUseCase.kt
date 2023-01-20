package com.ikcollab.domain.usecase.budget.category

import com.ikcollab.repository.budget.BudgetRepository
import com.ikcollab.repository.todo_list.TodoRepository
import javax.inject.Inject

class GetBudgetCategoryByIdUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budgetCategoryId: Int) = repository.getCategoryById(budgetCategoryId)

}