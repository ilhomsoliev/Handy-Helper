package com.ikcollab.domain.usecase.budget.story

import com.ikcollab.model.dto.budget.BudgetStoryDto
import com.ikcollab.model.dto.toBudgetStoryEntity
import com.ikcollab.repository.budget.BudgetRepository
import javax.inject.Inject

class DeleteBudgetStoryByIdUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(budgetStoryDto: BudgetStoryDto) {
        repository.deleteBudgetStory(budgetStoryDto.toBudgetStoryEntity())
    }
}