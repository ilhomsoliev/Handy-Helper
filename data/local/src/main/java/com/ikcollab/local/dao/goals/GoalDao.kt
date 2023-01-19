package com.ikcollab.local.dao.goals

import androidx.room.Query
import com.ikcollab.local.dao.ext.BaseDao
import com.ikcollab.model.local.goals.GoalEntity
import com.ikcollab.model.local.goals.StepGoalEntity
import com.ikcollab.model.local.note.NoteEntity
import kotlinx.coroutines.flow.Flow

interface GoalDao : BaseDao<GoalEntity> {
    @Query("SELECT * FROM ${GoalEntity.TABLE_NAME}")
    fun getGoals(): Flow<List<GoalEntity>>

    @Query("SELECT * FROM ${GoalEntity.TABLE_NAME} WHERE id = :goalId")
    suspend fun getGoalById(goalId: Int): GoalEntity?

    @Query("DELETE FROM ${GoalEntity.TABLE_NAME}")
    suspend fun deleteAllGoals()

    @Query("DELETE FROM ${GoalEntity.TABLE_NAME} WHERE id = :goalId")
    suspend fun deleteGoalById(goalId: Int)

}