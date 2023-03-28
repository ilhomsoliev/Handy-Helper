package com.ikcollab.todolist.todoCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikcollab.domain.usecase.todo_list.todoCategory.GetTodoCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoCategoryScreenViewModel @Inject constructor(
    private val getTodoCategoriesUseCase: GetTodoCategoriesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(TodoCategoryState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TodoCategoryState()
    )

    init {
        getTodoListCategories()
    }

    fun onEvent(event: TodoCategoryEvent) {
        when (event) {
            is TodoCategoryEvent.InsertCategory -> {

            }
            else -> Unit
        }
    }

    private fun getTodoListCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            getTodoCategoriesUseCase().onEach { list ->
                _state.update {
                    it.copy(
                        categories = list
                    )
                }
            }.launchIn(viewModelScope)
        }

    }

}