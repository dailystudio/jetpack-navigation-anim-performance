package com.dailystudio.navigation.animation.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.ui.theme.navigationTopAppBarColors
import androidx.navigation.compose.*
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.dailystudio.navigation.animation.data.ListData
import com.dailystudio.navigation.animation.ui.compose.utils.activityViewModel
import com.dailystudio.navigation.animation.viewmodel.DataViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    val navController = rememberNavController()

    val viewModel = activityViewModel<DataViewModel>()
    val primaryData by viewModel.primaryList.collectAsState(ListData())
    val secondaryData by viewModel.secondaryList.collectAsState(ListData())

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val title = when (currentBackStackEntry?.destination?.route) {
        "settings" -> stringResource(id = R.string.title_settings)
        else -> stringResource(id = R.string.activity_title_main_compose)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                },
                colors = navigationTopAppBarColors(),
                actions = {
                    Box {
                        IconButton(onClick = {
                            navController.navigate("settings")
                        }) {
                            Icon(
                                painter = rememberAsyncImagePainter(R.drawable.ic_settings),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            )
        },
        content = { padding ->
            Column (
                modifier = Modifier.padding(padding)
            ) {
                NavHost(
                    modifier = Modifier.fillMaxWidth(),
                    navController = navController,
                    startDestination = "primary") {
                    composable("primary",
                        enterTransition = { leftInTransition() },
                        exitTransition = { leftOutTransition() },
                    ) {
                        AbsDataListPage(
                            modifier = Modifier,
                            data = primaryData.items,
                            rippleEnabled = primaryData.itemLayout.rippleEnabled,
                            onItemClick = {
                                navController.navigate("secondary")
                            },
                        ) { modifier, item, rippleEnabled, onItemClick ->
                            if (primaryData.itemLayout.useCard) {
                                CardItem(modifier, item, rippleEnabled, onItemClick)
                            } else {
                                SimpleItem(modifier, item, rippleEnabled, onItemClick)
                            }
                        }
                    }
                    composable("secondary",
                        enterTransition = { rightInTransition() },
                        exitTransition = { leftOutTransition() },
                        popEnterTransition = { leftInTransition() },
                        popExitTransition = { rightOutTransition() }
                    ) {
                        AbsDataGridPage(
                            modifier = Modifier,
                            cells = GridCells.Fixed(2),
                            data = secondaryData.items,
                            rippleEnabled = secondaryData.itemLayout.rippleEnabled,
                            onItemClick = {
                                navController.navigate("settings")

                            }
                        ) { modifier, item, rippleEnabled, onItemClick ->
                            if (primaryData.itemLayout.useCard) {
                                CardItem(modifier, item, rippleEnabled, onItemClick)
                            } else {
                                SimpleItem(modifier, item, rippleEnabled, onItemClick)
                            }
                        }
                    }
                    composable("settings",
                        enterTransition = { rightInTransition() },
                        exitTransition = { rightOutTransition() }
                    ) {
                    }
                }
            }
        },

        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    )
}