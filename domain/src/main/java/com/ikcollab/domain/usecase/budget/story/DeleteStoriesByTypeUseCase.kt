package com.ikcollab.domain.usecase.budget.story
import com.ikcollab.repository.budget.BudgetRepository
import javax.inject.Inject

class DeleteStoriesByTypeUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(type: String) {
        repository.deleteStoriesByType(type)
    }
}