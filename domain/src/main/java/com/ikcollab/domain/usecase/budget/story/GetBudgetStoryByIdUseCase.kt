package com.ikcollab.domain.usecase.budget.story

import com.ikcollab.model.dto.budget.BudgetStoryDto
import com.ikcollab.model.dto.toBudgetStoryDto
import com.ikcollab.repository.budget.BudgetRepository
import javax.inject.Inject

class GetBudgetStoryByIdUseCase @Inject constructor(
    private val repository: BudgetRepository
) {
    suspend operator fun invoke(storyId: Int): BudgetStoryDto {
        val story = repository.getStoryById(storyId)
        return story!!.toBudgetStoryDto(repository.getCategoryById(story.categoryId)!!.name)
    }

}