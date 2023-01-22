package com.ikcollab.handyhelper.app.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.goals.goal.InsertGoalUseCase
import com.ikcollab.domain.usecase.goals.stepGoal.InsertStepGoalUseCase
import com.ikcollab.domain.usecase.notes.folder.InsertFolderUseCase
import com.ikcollab.model.dto.goals.GoalDto
import com.ikcollab.model.dto.goals.StepGoalDto
import com.ikcollab.model.dto.note.FolderDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val insertGoalUseCase: InsertGoalUseCase,
    private val insertFolderUseCase: InsertFolderUseCase,
    private val insertStepGoalUseCase: InsertStepGoalUseCase,
) : ViewModel() {
    private val _stateFolderName = mutableStateOf("")
    val stateFolderName = _stateFolderName

    // ------------------------------------------
    private val _newGoalName = mutableStateOf("")
    val newGoalName: State<String> = _newGoalName

    private val _newGoalStartDate = mutableStateOf(System.currentTimeMillis())
    val newGoalStartDate: State<Long> = _newGoalStartDate

    private val _newGoalEndDate = mutableStateOf(System.currentTimeMillis())
    val newGoalEndDate: State<Long> = _newGoalEndDate

    // ------------------------------------------

    private val _newStepGoalId = mutableStateOf(-1)
    val newStepGoalId: State<Int> = _newStepGoalId

    private val _newStepGoalName = mutableStateOf("")
    val newStepGoalName: State<String> = _newStepGoalName

    @RequiresApi(Build.VERSION_CODES.O)
    private val _newStepGoalDeadline = mutableStateOf(LocalDate.ofEpochDay(LocalDate.now().toEpochDay()).toEpochDay())
    @RequiresApi(Build.VERSION_CODES.O)
    val newStepGoalDeadline: State<Long> = _newStepGoalDeadline

    fun setNewGoalName(value: String) {
        _newGoalName.value = value
    }

    fun setNewStepGoalName(value: String) {
        _newStepGoalName.value = value
    }

    fun setNewStepGoalId(value: Int) {
        _newStepGoalId.value = value
    }

    fun setFolderName(folder: String) {
        _stateFolderName.value = folder
    }

    fun setNewGoalStartDate(value: Long) {
        _newGoalStartDate.value = value
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setNewStepGoalDeadline(value: Long) {
        _newStepGoalDeadline.value = value
    }

    fun setNewGoalEndDate(value: Long) {
        _newGoalEndDate.value = value
    }

    fun insertFolder(
        name: String,
        dateCreated: Long = System.currentTimeMillis()
    ) {
        viewModelScope.launch {
            insertFolderUseCase(FolderDto(name = name, dateCreated = dateCreated, id = null))
        }
        _stateFolderName.value = ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertStepGoalToDatabase(onDone: () -> Unit) {
        if (_newStepGoalName.value.isEmpty() && _newStepGoalId.value != -1) return

        viewModelScope.launch {
            insertStepGoalUseCase(
                StepGoalDto(
                    id = null,
                    name = _newStepGoalName.value,
                    isCompleted = false,
                    dateCreated = System.currentTimeMillis(),
                    deadline = _newStepGoalDeadline.value,
                    goalId = _newStepGoalId.value,
                )
            )
            _newStepGoalDeadline.value = System.currentTimeMillis()
            _newStepGoalName.value = ""
            onDone()
        }
    }

    fun insertGoalToDatabase(onDone: () -> Unit) {
        if (_newGoalName.value.isEmpty()) return

        viewModelScope.launch {
            insertGoalUseCase(
                GoalDto(
                    id = null,
                    name = _newGoalName.value,
                    stepsCount = 0,
                    completedStepsCount = 0,
                    dateCreated = System.currentTimeMillis(),
                    dateStart = _newGoalStartDate.value,
                    dateEnd = _newGoalEndDate.value,
                )
            )
            _newGoalEndDate.value = System.currentTimeMillis()
            _newGoalStartDate.value = System.currentTimeMillis()
            _newGoalName.value = ""
            onDone()
        }
    }

}