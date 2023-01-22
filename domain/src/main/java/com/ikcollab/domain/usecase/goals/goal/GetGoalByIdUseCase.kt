package com.ikcollab.domain.usecase.goals.goal

import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.toGoalDto
import com.ikcollab.repository.goals.GoalsRepository
import javax.inject.Inject

class GetGoalByIdUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    suspend operator fun invoke(goalId: Int): GoalDto {

        return repository.getGoalById(goalId)
            ?.toGoalDto(
                stepsCount = repository.getGoalStepsCount(goalId),
                completedStepsCount = repository.getGoalCompletedStepsCount(goalId)
            )!!
    }


}