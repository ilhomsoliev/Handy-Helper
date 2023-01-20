package com.ikcollab.local.dao.goals

import androidx.room.Dao
import androidx.room.Query
import com.ikcollab.local.dao.ext.BaseDao
import com.ikcollab.model.local.goals.GoalEntity
import com.ikcollab.model.local.goals.StepGoalEntity
import com.ikcollab.model.local.note.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StepGoalDao : BaseDao<StepGoalEntity> {
    @Query("SELECT * FROM ${StepGoalEntity.TABLE_NAME}")
    fun getStepsGoal(): Flow<List<StepGoalEntity>>

    @Query("SELECT * FROM ${StepGoalEntity.TABLE_NAME} WHERE id = :stepGoalId")
    suspend fun getStepGoalById(stepGoalId: Int): StepGoalEntity?

    @Query("DELETE FROM ${StepGoalEntity.TABLE_NAME}")
    suspend fun deleteAllStepsGoal()

    @Query("DELETE FROM ${StepGoalEntity.TABLE_NAME} WHERE id = :stepGoalId")
    suspend fun deleteStepGoalById(stepGoalId: Int)

    @Query("SELECT * FROM ${StepGoalEntity.TABLE_NAME} WHERE goal_id = :goalId")
    fun getStepsGoalByGoalId(goalId: Int): Flow<List<StepGoalEntity>>

    @Query("SELECT COUNT(*) FROM ${StepGoalEntity.TABLE_NAME} WHERE goal_id = :goalId")
    suspend fun getGoalsStepsCount(goalId: Int): Int

    @Query("SELECT COUNT(*) FROM ${StepGoalEntity.TABLE_NAME} WHERE goal_id = :goalId AND is_completed = 1")
    suspend fun getGoalsCompletedStepsCount(goalId: Int): Int
}
