package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.ShwedagonGold
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScaffold(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScaffold(viewModel: MainViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val isDeployGuideOpen by viewModel.isDevDeployGuideOpen.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.selectTab(AppTab.ASSISTANT) },
                containerColor = ShwedagonGold,
                contentColor = Color.Black,
                shape = CircleShape,
                modifier = Modifier
                    .size(56.dp)
                    .testTag("floating_mic_assistant_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Voice Assistant",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                val navItems = listOf(
                    AppTab.HOME_MAP to Icons.Default.Map,
                    AppTab.ROUTES to Icons.Default.DirectionsTransit,
                    AppTab.JOBS to Icons.Default.Work,
                    AppTab.GOLDEN_JOURNEY to Icons.Default.Explore,
                    AppTab.SAFE_HARBOR to Icons.Default.Shield,
                    AppTab.BUSINESS_CIRCLE to Icons.Default.Storefront
                )

                navItems.forEach { (tab, icon) ->
                    val selected = currentTab == tab
                    NavigationBarItem(
                        selected = selected,
                        onClick = { viewModel.selectTab(tab) },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = tab.titleEnglish,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.titleBurmese,
                                fontSize = 10.sp,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                maxLines = 1
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_${tab.name}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                AppTab.HOME_MAP -> HomeScreen(viewModel = viewModel)
                AppTab.ROUTES -> RoutesScreen(viewModel = viewModel)
                AppTab.COMMUNITY -> HomeScreen(viewModel = viewModel)
                AppTab.JOBS -> JobsScreen(viewModel = viewModel)
                AppTab.GOLDEN_JOURNEY -> GoldenJourneyScreen(viewModel = viewModel)
                AppTab.SAFE_HARBOR -> SafeHarborScreen(viewModel = viewModel)
                AppTab.BUSINESS_CIRCLE -> BusinessCircleScreen(viewModel = viewModel)
                AppTab.ASSISTANT -> VoiceConciergeScreen(viewModel = viewModel)
            }
        }

        val isOfflineMapOpen by viewModel.isOfflineMapDialogVisible.collectAsState()
        val isGoldenPassOpen by viewModel.isGoldenPassDialogVisible.collectAsState()
        val isTelemedOpen by viewModel.isTelemedDialogVisible.collectAsState()
        val isRemittanceOpen by viewModel.isRemittanceCalculatorVisible.collectAsState()

        // Developer & Deployment Guide Dialog
        if (isDeployGuideOpen) {
            DevDeployGuideDialog(viewModel = viewModel)
        }

        // Offline Vector Maps Manager Dialog
        if (isOfflineMapOpen) {
            OfflineMapsDialog(viewModel = viewModel)
        }

        // Golden Pass VIP Membership Dialog
        if (isGoldenPassOpen) {
            GoldenPassDialog(viewModel = viewModel)
        }

        // Telemedicine Doctors Dialog
        if (isTelemedOpen) {
            TelemedicineDialog(viewModel = viewModel)
        }

        // Remittance Calculator Dialog
        if (isRemittanceOpen) {
            RemittanceCalculatorDialog(viewModel = viewModel)
        }
    }
}
