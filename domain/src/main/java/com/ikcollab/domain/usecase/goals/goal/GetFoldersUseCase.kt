package com.ikcollab.domain.usecase.goals.goal

import com.ikcollab.local.dao.goals.GoalDao
import com.ikcollab.model.dao.goals.GoalDto
import com.ikcollab.model.dao.note.FolderDto
import com.ikcollab.model.dao.toFolderDto
import com.ikcollab.model.dao.toGoalDto
import com.ikcollab.repository.goals.GoalsRepository
import com.ikcollab.repository.notes.NotesRepository
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