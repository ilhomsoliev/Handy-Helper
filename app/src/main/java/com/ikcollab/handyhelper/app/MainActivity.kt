package com.ikcollab.handyhelper.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.handyhelper.app.navigation.Navigation
import com.ikcollab.handyhelper.app.navigation.NavigationViewModel
import com.ikcollab.handyhelper.app.theme.HandyHelperTheme
import com.ikcollab.todolist.todoCategoryScreen.TodoCategoryScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HandyHelperTheme {
                val viewModel = hiltViewModel<NavigationViewModel>()

                Navigation(state = viewModel.state.collectAsState().value, onEvent = { event ->
                    viewModel.onEvent(event)
                })
            }
        }
    }
}
