package com.ikcollab.domain.usecase.goals.goal

import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.toGoalEntity
import com.ikcollab.repository.goals.GoalsRepository
import javax.inject.Inject

class InsertGoalUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend operator fun invoke(goalDto: GoalDto) {
        repository.insertGoal(goalDto.toGoalEntity())
    }
}