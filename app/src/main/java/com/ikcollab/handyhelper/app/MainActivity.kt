package com.ikcollab.handyhelper.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ikcollab.handyhelper.app.navigation.Navigation
import com.ikcollab.handyhelper.app.theme.HandyHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HandyHelperTheme {
                Navigation()
            }
        }
    }
}
