package com.socialai.app.features.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.socialai.app.core.navigation.Screen
import com.socialai.app.core.ui.component.FullWidthButton

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {
    var accountType by remember { mutableStateOf("PERSON") } // PERSON, INSTITUTION, GOVERNMENT

    // Common
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Institution specific
    var institutionType by remember { mutableStateOf("University") } // University, Industry, HEI
    var website by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }
    var expertise by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("Available") }

    // Government specific
    var govOrg by remember { mutableStateOf("") }
    var govDept by remember { mutableStateOf("") }
    var govPosition by remember { mutableStateOf("") }
    var jurisdiction by remember { mutableStateOf("") }

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state) {
        if (state is AuthViewModel.UiState.Success) {
            navController.navigate(Screen.ProblemFeed.route) { popUpTo(0) }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(60.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Create an Account",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Join the SocialSolve AI ecosystem",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
            )

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "What type of account are you creating?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = accountType == "PERSON",
                            onClick = { accountType = "PERSON" },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            label = { Text("Person", fontWeight = FontWeight.SemiBold) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = accountType == "INSTITUTION",
                            onClick = { accountType = "INSTITUTION" },
                            leadingIcon = { Icon(Icons.Default.School, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            label = { Text("Institution", fontWeight = FontWeight.SemiBold) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = accountType == "GOVERNMENT",
                            onClick = { accountType = "GOVERNMENT" },
                            leadingIcon = { Icon(Icons.Default.AccountBalance, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            label = { Text("Government", fontWeight = FontWeight.SemiBold) },
                            modifier = Modifier.weight(1.1f)
                        )
                    }

                    HorizontalDivider()

                    // Dynamic Fields based on Account Type
                    when (accountType) {
                        "PERSON" -> {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Full Name") },
                                placeholder = { Text("e.g. Dhinesh Kumar") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Email Address") },
                                placeholder = { Text("e.g. dhinesh@example.com") },
                                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = phone,
                                onValueChange = { phone = it },
                                label = { Text("Phone Number") },
                                placeholder = { Text("e.g. +91 9876543210") },
                                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = location,
                                onValueChange = { location = it },
                                label = { Text("Location / District") },
                                placeholder = { Text("e.g. Trichy, Tamil Nadu") },
                                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        "INSTITUTION" -> {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                label = { Text("Institution Name") },
                                placeholder = { Text("e.g. NIT Trichy / ABC Institute") },
                                leadingIcon = { Icon(Icons.Default.School, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Type:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                                listOf("University", "Industry", "Research Lab").forEach { type ->
                                    FilterChip(
                                        selected = institutionType == type,
                                        onClick = { institutionType = type },
                                        label = { Text(type, style = MaterialTheme.typography.bodySmall) }
                                    )
                                }
                            }

                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Official Email") },
                                placeholder = { Text("e.g. contact@nitt.edu") },
                                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = website,
                                onValueChange = { website = it },
                                label = { Text("Website") },
                                placeholder = { Text("e.g. https://www.nitt.edu") },
                                leadingIcon = { Icon(Icons.Default.Language, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = location,
                                onValueChange = { location = it },
                                label = { Text("Location / Campus") },
                                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = department,
                                onValueChange = { department = it },
                                label = { Text("Departments & Labs") },
                                placeholder = { Text("e.g. IoT Lab, Embedded Systems Dept") },
                                leadingIcon = { Icon(Icons.Default.Business, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = expertise,
                                onValueChange = { expertise = it },
                                label = { Text("Research & Technology Expertise") },
                                placeholder = { Text("e.g. Smart Cities, IoT, AI, Computer Vision") },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        "GOVERNMENT" -> {
                            OutlinedTextField(
                                value = govOrg,
                                onValueChange = { govOrg = it; name = it },
                                label = { Text("Organization / Body Name") },
                                placeholder = { Text("e.g. City Municipal Corporation") },
                                leadingIcon = { Icon(Icons.Default.AccountBalance, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = govDept,
                                onValueChange = { govDept = it },
                                label = { Text("Department") },
                                placeholder = { Text("e.g. Infrastructure & Public Works") },
                                leadingIcon = { Icon(Icons.Default.Business, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                label = { Text("Official Email") },
                                placeholder = { Text("e.g. commissioner@citygov.in") },
                                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = phone,
                                onValueChange = { phone = it },
                                label = { Text("Official Phone") },
                                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = govPosition,
                                onValueChange = { govPosition = it },
                                label = { Text("Position / Title") },
                                placeholder = { Text("e.g. Executive Engineer / Collector") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = jurisdiction,
                                onValueChange = { jurisdiction = it },
                                label = { Text("Jurisdiction / Region") },
                                placeholder = { Text("e.g. District Central Zone") },
                                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    // Password Fields
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password (min 6 chars)") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (password.isNotBlank() && confirmPassword.isNotBlank() && password != confirmPassword) {
                        Text(
                            text = "Passwords do not match",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if (state is AuthViewModel.UiState.Error) {
                        Surface(
                            color = MaterialTheme.colorScheme.errorContainer,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = (state as AuthViewModel.UiState.Error).message,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    FullWidthButton(
                        text = "Register as " + accountType.lowercase().replaceFirstChar { it.uppercase() },
                        onClick = {
                            viewModel.register(
                                name = if (accountType == "GOVERNMENT") govOrg else name,
                                email = email,
                                pass = password,
                                role = when (accountType) {
                                    "INSTITUTION" -> if (institutionType == "Industry") "INDUSTRY" else "UNIVERSITY"
                                    "GOVERNMENT" -> "GOVERNMENT"
                                    else -> "CITIZEN"
                                },
                                phone = phone,
                                location = location,
                                website = website,
                                department = if (accountType == "GOVERNMENT") govDept else department,
                                jurisdiction = jurisdiction,
                                orgName = if (accountType == "GOVERNMENT") govOrg else name,
                                expertise = expertise,
                                capacity = capacity
                            )
                        },
                        isLoading = state is AuthViewModel.UiState.Loading,
                        enabled = (if (accountType == "GOVERNMENT") govOrg.isNotBlank() else name.isNotBlank()) &&
                                email.isNotBlank() &&
                                password.isNotBlank() &&
                                password == confirmPassword
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = { navController.navigate(Screen.Login.route) }) {
                            Text("Already have an account? ")
                            Text(
                                "Sign In",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = {
                            viewModel.continueAsCitizen {
                                navController.navigate(Screen.ProblemFeed.route) { popUpTo(0) }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Continue as Citizen (No Login)", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
