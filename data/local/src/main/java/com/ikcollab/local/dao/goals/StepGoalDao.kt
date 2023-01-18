package com.ikcollab.local.dao.goals

import androidx.room.Query
import com.ikcollab.local.dao.ext.BaseDao
import com.ikcollab.model.local.goals.GoalEntity
import com.ikcollab.model.local.goals.StepGoalEntity
import com.ikcollab.model.local.note.NoteEntity
import kotlinx.coroutines.flow.Flow

interface StepGoalDao:BaseDao<StepGoalEntity> {
    @Query("SELECT * FROM ${StepGoalEntity.TABLE_NAME}")
    suspend fun getStepsGoal(): Flow<List<StepGoalEntity>>

    @Query("SELECT * FROM ${StepGoalEntity.TABLE_NAME} WHERE id = :stepGoalId")
    suspend fun getGoalById(stepGoalId: Int): StepGoalEntity?

    @Query("DELETE FROM ${StepGoalEntity.TABLE_NAME}")
    suspend fun deleteAllStepsGoal()

    @Query("DELETE FROM ${StepGoalEntity.TABLE_NAME} WHERE id = :stepGoalId")
    suspend fun deleteStepGoalById(stepGoalId: Int)

}