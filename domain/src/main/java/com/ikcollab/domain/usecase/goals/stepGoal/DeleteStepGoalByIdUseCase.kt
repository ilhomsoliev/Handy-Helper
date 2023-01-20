package com.ikcollab.domain.usecase.goals.stepGoal

import com.ikcollab.model.dto.goals.StepGoalDto
import com.ikcollab.model.dto.toStepGoalEntity
import com.ikcollab.repository.goals.GoalsRepository
import javax.inject.Inject

class DeleteStepGoalByIdUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend operator fun invoke(stepGoalDto: StepGoalDto) {
        repository.deleteStepGoal(stepGoalDto.toStepGoalEntity())
    }
}