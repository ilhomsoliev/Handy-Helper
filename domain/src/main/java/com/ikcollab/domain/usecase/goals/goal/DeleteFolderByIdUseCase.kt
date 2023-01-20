package com.ikcollab.domain.usecase.goals.goal

import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.toGoalEntity
import com.ikcollab.repository.goals.GoalsRepository
import javax.inject.Inject

class DeleteGoalByIdUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend operator fun invoke(goalDto: GoalDto) {
        repository.deleteGoal(goalDto.toGoalEntity())
    }
}