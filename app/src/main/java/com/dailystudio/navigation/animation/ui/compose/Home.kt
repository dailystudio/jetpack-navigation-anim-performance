package com.dailystudio.navigation.animation.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.ui.theme.navigationTopAppBarColors
import androidx.navigation.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {

    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.activity_title_main_compose))
                },
                colors = navigationTopAppBarColors(),
                actions = {
                }
            )
        },
        content = { padding ->
            Column (
                modifier = Modifier.padding(padding)
            ) {
                NavHost(navController = navController,
                    startDestination = "primary") {
                    composable("primary",
                        enterTransition = { leftInTransition() },
                        exitTransition = { leftOutTransition() },
                    ) {
                        PrimaryListPage(
                            onItemClick = {
                                navController.navigate("secondary")
                            }
                        ) { item, modifier ->
                            Box {
                                Text(text = item.text)
                            }
                        }
                    }
                    composable("secondary",
                        enterTransition = { rightInTransition() },
                        exitTransition = { rightOutTransition() },
                    ) {
                        SecondaryListPage(
                            onItemClick = {}
                        ) {item, modifier ->
                            Box {
                                Text(text = item.text)
                            }
                        }
                    }
                }
            }
        },

        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    )
}