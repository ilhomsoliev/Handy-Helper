package com.ikcollab.handyhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ikcollab.handyhelper.navigation.Navigation
import com.ikcollab.handyhelper.ui.theme.HandyHelperTheme

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
