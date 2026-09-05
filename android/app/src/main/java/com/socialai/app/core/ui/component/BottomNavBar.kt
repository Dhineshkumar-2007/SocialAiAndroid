package com.socialai.app.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.socialai.app.core.navigation.Screen

enum class CitizenTab(val title: String, val icon: ImageVector, val route: String) {
    HOME("Home", Icons.Default.Home, Screen.HomeDashboard.route),
    EXPLORE("Explore", Icons.Default.Explore, Screen.Explore.route),
    REPORT("Report", Icons.Default.Add, Screen.ReportStart.route),
    MY_PROBLEMS("My Problems", Icons.Default.Assignment, Screen.MyProblems.route),
    PROFILE("Profile", Icons.Default.Person, Screen.Profile.route)
}

enum class UniversityTab(val title: String, val icon: ImageVector, val route: String) {
    DASHBOARD("Dashboard", Icons.Default.Dashboard, Screen.UniversityDashboard.route),
    MATCHES("Matches", Icons.Default.ManageSearch, Screen.IncomingMatches.route),
    MY_PROBLEMS("My Problems", Icons.Default.Assignment, Screen.AcceptedProblems.route),
    PROFILE("Profile", Icons.Default.Person, Screen.Profile.route)
}

@Composable
fun CitizenBottomNavBar(
    navController: NavController,
    currentTab: CitizenTab
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        CitizenTab.values().forEach { tab ->
            if (tab == CitizenTab.REPORT) {
                // Center prominent '+' Floating button
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate(tab.route)
                    },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Report Problem",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            } else {
                val isSelected = currentTab == tab
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(tab.route) {
                                popUpTo(Screen.HomeDashboard.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title
                        )
                    },
                    label = {
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    }
}

@Composable
fun UniversityBottomNavBar(
    navController: NavController,
    currentTab: UniversityTab
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        UniversityTab.values().forEach { tab ->
            val isSelected = currentTab == tab
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(tab.route) {
                            popUpTo(Screen.UniversityDashboard.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title
                    )
                },
                label = {
                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}
