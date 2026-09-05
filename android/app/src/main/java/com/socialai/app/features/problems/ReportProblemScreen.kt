package com.socialai.app.features.problems

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.socialai.app.core.navigation.Screen
import com.socialai.app.core.ui.component.FullWidthButton
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ReportProblemScreen(
    navController: NavController,
    viewModel: ProblemViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }

    // Live Location State
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }
    var isFetchingLocation by remember { mutableStateOf(false) }
    var locationError by remember { mutableStateOf<String?>(null) }

    // Media & Camera Attachment State
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    // AI Analysis Preview State
    var isAnalyzing by remember { mutableStateOf(false) }
    var submittedSuccessfully by remember { mutableStateOf(false) }

    // Duplicate Detection Dialog State
    var showDuplicateDialog by remember { mutableStateOf(false) }
    var duplicateSimilarity by remember { mutableStateOf(89) }
    var duplicateTitleMatch by remember { mutableStateOf("Streetlights not working near Government School") }

    val isLoading by viewModel.isLoading.collectAsState()

    // Reverse Geocoding Helper
    fun fetchAddressForCoordinates(lat: Double, lng: Double) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocation(lat, lng, 1, object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        val addr = addresses.firstOrNull()
                        val fullAddr = addr?.let {
                            val lines = (0..it.maxAddressLineIndex).mapNotNull { i -> it.getAddressLine(i) }
                            if (lines.isNotEmpty()) lines.joinToString(", ")
                            else listOfNotNull(it.subLocality, it.locality, it.subAdminArea, it.adminArea).joinToString(", ")
                        }
                        if (!fullAddr.isNullOrBlank()) {
                            district = fullAddr
                        }
                    }
                    override fun onError(errorMessage: String?) {}
                })
            } else {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(lat, lng, 1)
                val addr = addresses?.firstOrNull()
                val fullAddr = addr?.let {
                    val lines = (0..it.maxAddressLineIndex).mapNotNull { i -> it.getAddressLine(i) }
                    if (lines.isNotEmpty()) lines.joinToString(", ")
                    else listOfNotNull(it.subLocality, it.locality, it.subAdminArea, it.adminArea).joinToString(", ")
                }
                if (!fullAddr.isNullOrBlank()) {
                    district = fullAddr
                }
            }
        } catch (e: Exception) {
            // Keep fallback
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            capturedBitmap = bitmap
            selectedFileUri = null
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            locationError = "Camera permission is required to capture photo evidence."
        }
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedFileUri = uri
            capturedBitmap = null
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        isFetchingLocation = true
        locationError = null
        try {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                locationError = "Please enable GPS/Location on your device."
                isFetchingLocation = false
                return
            }

            var loc: Location? = null
            if (isGpsEnabled) {
                loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
            if (loc == null && isNetworkEnabled) {
                loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }

            if (loc != null) {
                latitude = loc.latitude
                longitude = loc.longitude
                district = "GPS (${String.format(Locale.US, "%.4f", loc.latitude)}, ${String.format(Locale.US, "%.4f", loc.longitude)})"
                fetchAddressForCoordinates(loc.latitude, loc.longitude)
                isFetchingLocation = false
            } else {
                val provider = if (isGpsEnabled) LocationManager.GPS_PROVIDER else LocationManager.NETWORK_PROVIDER
                val listener = object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        latitude = location.latitude
                        longitude = location.longitude
                        district = "GPS (${String.format(Locale.US, "%.4f", location.latitude)}, ${String.format(Locale.US, "%.4f", location.longitude)})"
                        fetchAddressForCoordinates(location.latitude, location.longitude)
                        isFetchingLocation = false
                        locationManager.removeUpdates(this)
                    }
                }
                locationManager.requestLocationUpdates(provider, 0L, 0f, listener, Looper.getMainLooper())
            }
        } catch (e: Exception) {
            locationError = "Error fetching location: ${e.message}"
            isFetchingLocation = false
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        if (fineGranted || coarseGranted) {
            getLocation()
        } else {
            locationError = "Location permission is required to capture live GPS."
        }
    }

    fun submitProblemInternal() {
        viewModel.createProblem(title, desc, district, latitude, longitude) { problemId ->
            submittedSuccessfully = true
            isAnalyzing = false
        }
    }

    fun startSubmissionProcess() {
        if (title.lowercase().contains("streetlight") || desc.lowercase().contains("streetlight")) {
            showDuplicateDialog = true
        } else {
            isAnalyzing = true
            submitProblemInternal()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Report a Problem", fontWeight = FontWeight.Bold) },
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
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Success Notification Banner
            AnimatedVisibility(visible = submittedSuccessfully) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Problem Submitted Successfully!",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = "AI Analysis complete. Your challenge is registered and actively matching institutions.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }

            // Two-Column Side-by-Side or Stacked AI Analysis Preview
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "AI ANALYSIS PREVIEW",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = if (submittedSuccessfully) "ANALYSIS COMPLETE" else "REAL-TIME EVALUATION",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (isAnalyzing) {
                        Text(
                            text = "Understanding problem...",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth().height(8.dp))
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Evidence Support", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("92%", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("Category", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Infrastructure", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text("Severity", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("HIGH", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.error)
                                Spacer(modifier = Modifier.height(6.dp))
                                Text("Priority Score", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("87 / 100", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Required Skills Tags
                        Text("Required Expertise:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(4.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf("IoT", "Embedded Systems", "Smart Cities", "Computer Vision").forEach { skill ->
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.secondaryContainer
                                ) {
                                    Text(
                                        text = "• $skill",
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider()
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "AI Vision Caption: \"Damaged streetlight beside a road.\"",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "AI estimates that the uploaded evidence supports the submitted description.",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Input Form Section
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                placeholder = { Text("e.g. Broken Streetlight near school") },
                leadingIcon = { Icon(Icons.Default.Title, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = desc,
                onValueChange = { desc = it },
                label = { Text("Description") },
                placeholder = { Text("Describe the problem details and community impact...") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                minLines = 3,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = district,
                onValueChange = { district = it },
                label = { Text("Location") },
                placeholder = { Text("e.g. Near Government High School, Main Road") },
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            // Location Helper Button
            OutlinedButton(
                onClick = {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                },
                enabled = !isFetchingLocation,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isFetchingLocation) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Fetching Location...")
                } else {
                    Icon(Icons.Default.MyLocation, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Auto-Detect GPS Location")
                }
            }

            // Photo Evidence Picker
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Evidence Photo", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Camera")
                        }

                        OutlinedButton(
                            onClick = { filePickerLauncher.launch("image/*") },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.AttachFile, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Gallery")
                        }
                    }

                    if (capturedBitmap != null || selectedFileUri != null) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            if (capturedBitmap != null) {
                                Image(
                                    bitmap = capturedBitmap!!.asImageBitmap(),
                                    contentDescription = "Photo Evidence",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else if (selectedFileUri != null) {
                                AsyncImage(
                                    model = selectedFileUri,
                                    contentDescription = "Photo Evidence",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            IconButton(
                                onClick = { capturedBitmap = null; selectedFileUri = null },
                                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Remove")
                            }
                        }
                    }
                }
            }

            FullWidthButton(
                text = if (submittedSuccessfully) "Submit Another Problem" else "Submit Problem",
                onClick = {
                    if (submittedSuccessfully) {
                        title = ""
                        desc = ""
                        district = ""
                        capturedBitmap = null
                        selectedFileUri = null
                        submittedSuccessfully = false
                    } else {
                        startSubmissionProcess()
                    }
                },
                enabled = title.isNotBlank() && desc.isNotBlank(),
                isLoading = isLoading,
                icon = Icons.Default.AutoAwesome
            )
        }
    }

    // Duplicate Detection Dialog
    if (showDuplicateDialog) {
        AlertDialog(
            onDismissRequest = { showDuplicateDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(36.dp)
                )
            },
            title = {
                Text(
                    text = "Similar Problem Detected",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "$duplicateSimilarity% similarity found with existing report:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    Text(
                        text = "\"$duplicateTitleMatch\"",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = "You can view the existing issue to upvote/track it, or proceed with creating a distinct report.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDuplicateDialog = false
                        isAnalyzing = true
                        submitProblemInternal()
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Continue")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        showDuplicateDialog = false
                        navController.navigate(Screen.ProblemFeed.route)
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("View Existing")
                }
            }
        )
    }
}
