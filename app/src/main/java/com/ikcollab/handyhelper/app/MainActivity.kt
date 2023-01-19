package com.ikcollab.handyhelper.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.ikcollab.handyhelper.app.navigation.Navigation
import com.ikcollab.handyhelper.app.theme.HandyHelperTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HandyHelperTheme {
                Navigation()
            }
        }
    }
}
