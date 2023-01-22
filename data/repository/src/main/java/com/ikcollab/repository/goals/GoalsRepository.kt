package com.ikcollab.repository.goals

import com.ikcollab.local.dao.goals.GoalDao
import com.ikcollab.local.dao.goals.StepGoalDao
import com.ikcollab.model.local.goals.GoalEntity
import com.ikcollab.model.local.goals.StepGoalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GoalsRepository @Inject constructor(
    //@get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val goalDao: GoalDao,
    //@get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val stepGoalDao: StepGoalDao,
) {

    suspend fun insertGoal(goalEntity: GoalEntity) = goalDao.insert(goalEntity)
    suspend fun insertStepGoal(stepGoalEntity: StepGoalEntity) = stepGoalDao.insert(stepGoalEntity)
    suspend fun deleteGoal(goalEntity: GoalEntity) = goalDao.delete(goalEntity)
    suspend fun deleteStepGoal(stepGoalEntity: StepGoalEntity) = stepGoalDao.delete(stepGoalEntity)
    suspend fun deleteGoalById(goalId: Int) = goalDao.deleteGoalById(goalId)
    suspend fun deleteStepGoalById(stepGoalId: Int) = stepGoalDao.deleteStepGoalById(stepGoalId)
    suspend fun getGoalById(goalId: Int): GoalEntity? = goalDao.getGoalById(goalId)
    suspend fun getStepGoalById(stepGoalId: Int): StepGoalEntity? = stepGoalDao.getStepGoalById(stepGoalId)
    fun getStepsGoalSByGoalId(goalId: Int): Flow<List<StepGoalEntity>> = stepGoalDao.getStepsGoalByGoalId(goalId)
    fun getGoals(): Flow<List<GoalEntity>> = goalDao.getGoals()
    suspend fun getGoalStepsCount(goalId: Int): Int = stepGoalDao.getGoalsStepsCount(goalId)
    suspend fun getGoalCompletedStepsCount(goalId: Int): Int = stepGoalDao.getGoalsCompletedStepsCount(goalId)

}