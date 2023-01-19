package com.ikcollab.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ikcollab.local.dao.goals.GoalDao
import com.ikcollab.local.dao.goals.StepGoalDao
import com.ikcollab.local.dao.notes.FolderDao
import com.ikcollab.local.dao.notes.NoteDao
import com.ikcollab.model.local.goals.GoalEntity
import com.ikcollab.model.local.goals.StepGoalEntity
import com.ikcollab.model.local.note.FolderEntity
import com.ikcollab.model.local.note.NoteEntity

@Database(
    entities = [NoteEntity::class,FolderEntity::class, StepGoalEntity::class, GoalEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HandyHelperDatabase:RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao
    abstract fun goalDao(): GoalDao
    abstract fun stepGoalDao(): StepGoalDao
}