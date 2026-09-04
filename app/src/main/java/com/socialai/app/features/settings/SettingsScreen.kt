package com.socialai.app.features.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.socialai.app.core.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val serverUrl by viewModel.serverUrl.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userRole by viewModel.userRole.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    var newUrl by remember(serverUrl) { mutableStateOf(serverUrl ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("User Account", style = MaterialTheme.typography.titleMedium)

            if (isLoggedIn) {
                Text(
                    "Logged in as: ${userName ?: "User"} (${userRole ?: "CITIZEN"})",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    "Account Mode: Guest Citizen (No login)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            Text("Backend Connection", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = newUrl,
                onValueChange = { newUrl = it },
                label = { Text("Server Base URL") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.saveServerUrl(newUrl) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Server URL")
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(24.dp))

            if (isLoggedIn) {
                Button(
                    onClick = {
                        viewModel.logout {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout")
                }
            } else {
                Button(
                    onClick = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Log In / Register")
                }
            }
        }
    }
}
