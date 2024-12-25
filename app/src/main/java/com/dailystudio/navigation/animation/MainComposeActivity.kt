package com.dailystudio.navigation.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dailystudio.navigation.animation.ui.compose.Home
import com.dailystudio.navigation.animation.ui.theme.NavigationAnimationTheme

class MainComposeActivity: ComponentActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigationAnimationTheme {
                Home()
            }
        }
    }

}