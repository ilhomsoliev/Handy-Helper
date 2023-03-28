package com.ikcollab.handyhelper.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.ikcollab.core.DataStoreManager
import com.ikcollab.handyhelper.app.navigation.Navigation
import com.ikcollab.handyhelper.app.navigation.NavigationViewModel
import com.ikcollab.handyhelper.app.theme.HandyHelperTheme
import com.ikcollab.todolist.todoCategoryScreen.TodoCategoryScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStoreManager: DataStoreManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager.getLanguageId.onEach {
            val locale = Locale(it)
            Locale.setDefault(locale)
            val resources = this.resources
            val configuration = resources.configuration
            configuration.locale = locale
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }.launchIn(GlobalScope)

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
