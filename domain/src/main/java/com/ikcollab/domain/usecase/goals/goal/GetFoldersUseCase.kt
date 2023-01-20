package com.ikcollab.domain.usecase.goals.goal

import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.toGoalDto
import com.ikcollab.repository.goals.GoalsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFoldersUseCase @Inject constructor(
    private val repository: GoalsRepository
) {
    operator fun invoke(): Flow<List<GoalDto>> {
        return repository.getGoals().map { goals ->
            goals.sortedBy { it.dateCreated }.map {
                it.toGoalDto(
                    stepsCount = repository.getGoalStepsCount(it.id),
                    completedStepsCount = repository.getGoalCompletedStepsCount(it.id)
                )
            }
        }
    }
}