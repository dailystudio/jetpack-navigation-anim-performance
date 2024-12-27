package com.dailystudio.navigation.animation.ui.compose

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.ui.theme.navigationTopAppBarColors
import androidx.navigation.compose.*
import coil3.compose.rememberAsyncImagePainter
import com.dailystudio.navigation.animation.data.ListData
import com.dailystudio.navigation.animation.dev.FrameData
import com.dailystudio.navigation.animation.ui.compose.utils.activityViewModel
import com.dailystudio.navigation.animation.viewmodel.DataViewModel
import com.dailystudio.navigation.animation.viewmodel.PerformanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home() {
    val navController = rememberNavController()

    val viewModel = activityViewModel<DataViewModel>()
    val performanceViewModel = activityViewModel<PerformanceViewModel>()

    val primaryData by viewModel.primaryList.collectAsState(ListData())
    val secondaryData by viewModel.secondaryList.collectAsState(ListData())

    val fps by performanceViewModel.fps.collectAsState(FrameData())
    val dropped by performanceViewModel.droppedFrames.collectAsState(FrameData())

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
                            performanceViewModel.resetFps()
                            performanceViewModel.resetDroppedFrames()

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                ) {
                    NavHost(
                        modifier = Modifier.fillMaxWidth(),
                        navController = navController,
                        startDestination = "primary"
                    ) {
                        composable(
                            "primary",
                            enterTransition = { leftInTransition() },
                            exitTransition = { leftOutTransition() },
                        ) {
                            AbsDataListPage(
                                modifier = Modifier,
                                data = primaryData.items,
                                rippleEnabled = primaryData.itemLayout.rippleEnabled,
                                onItemClick = {
                                    performanceViewModel.resetFps()
                                    performanceViewModel.resetDroppedFrames()

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
                            BackHandler {
                                Log.d("BackHandler", "[Secondary] Back pressed intercepted")

                                performanceViewModel.resetFps()
                                performanceViewModel.resetDroppedFrames()

                                navController.navigateUp()
                            }

                            AbsDataGridPage(
                                modifier = Modifier,
                                cells = GridCells.Fixed(2),
                                data = secondaryData.items,
                                rippleEnabled = secondaryData.itemLayout.rippleEnabled,
                                {}
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
                            BackHandler {
                                Log.d("BackHandler", "[Secondary] Back pressed intercepted")

                                performanceViewModel.resetFps()
                                performanceViewModel.resetDroppedFrames()

                                navController.navigateUp()
                            }

                            SettingsPage(
                                rippleEnabled = primaryData.itemLayout.rippleEnabled,
                                useCard = primaryData.itemLayout.useCard,
                                onRippleEnabledChanged = {
                                    Log.d("HOME", "update ripple: $it")
                                    viewModel.updateRippleEnabled(it)
                                },
                                onUseCardChanged = {
                                    Log.d("HOME", "update card: $it")
                                    viewModel.updateItemLayout(
                                        if (it) DataViewModel.LAYOUT_CARD else DataViewModel.LAYOUT_IV_TV
                                    )
                                }
                            )
                        }
                    }
                }

                FramesMonitors(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    fps = fps,
                    dropped = dropped
                )
            }
        },

        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    )
}