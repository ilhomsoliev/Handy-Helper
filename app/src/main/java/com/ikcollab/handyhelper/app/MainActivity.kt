package com.ikcollab.handyhelper.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.ads.MobileAds
import com.ikcollab.core.DataStoreManager
import com.ikcollab.handyhelper.app.navigation.Navigation
import com.ikcollab.handyhelper.app.navigation.NavigationViewModel
import com.ikcollab.components.theme.HandyHelperTheme
import com.ikcollab.handyhelper.app.ads.loadInterstitial
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : ComponentActivity(), CoroutineScope  {
    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + CoroutineName("Activity Scope") + CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception $throwable in context:$coroutineContext")
        }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        dataStoreManager.getLanguageId.onEach {
            val locale = Locale(it)
            Locale.setDefault(locale)
            val resources = this.resources
            val configuration = resources.configuration
            configuration.locale = locale
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }.launchIn(this)

        MobileAds.initialize(this@MainActivity)
        loadInterstitial(this)

        setContent {
            HandyHelperTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(MaterialTheme.colors.background)
                val viewModel = hiltViewModel<NavigationViewModel>()
                Navigation(state = viewModel.state.collectAsState().value, onEvent = { event ->
                    viewModel.onEvent(event)
                })
            }
        }
    }
}
