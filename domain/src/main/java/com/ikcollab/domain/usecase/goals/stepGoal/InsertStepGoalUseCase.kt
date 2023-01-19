package com.ikcollab.domain.usecase.goals.stepGoal

import com.ikcollab.model.dao.goals.StepGoalDto
import com.ikcollab.model.dao.toStepGoalEntity
import com.ikcollab.repository.goals.GoalsRepository
import javax.inject.Inject

class InsertStepGoalUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend operator fun invoke(stepGoalDto: StepGoalDto) {
        repository.insertStepGoal(stepGoalDto.toStepGoalEntity())
    }
}