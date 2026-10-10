
package com.example.powerwatch.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DevicesOther
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

private val ReportGreen = Color(0xFF087F5B)
private val ReportDeepGreen = Color(0xFF064B39)
private val ReportMint = Color(0xFFE3F5E9)
private val ReportBackground = Color(0xFFF5F8F5)
private val ReportInk = Color(0xFF172E25)
private val ReportMuted = Color(0xFF75837B)
private val ReportOrange = Color(0xFFB96B20)

private data class EquipmentOption(
    val name: String,
    val icon: ImageVector
)

@Composable
fun ReportFormScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onSubmitted: () -> Unit
) {
    val equipmentOptions = listOf(
        EquipmentOption("Lights", Icons.Default.Lightbulb),
        EquipmentOption("Fans", Icons.Default.WindPower),
        EquipmentOption("AC", Icons.Default.AcUnit),
        EquipmentOption("Other", Icons.Default.DevicesOther)
    )

    val buildings = listOf(
        "Hostel Block A",
        "Hostel Block B",
        "Hostel Block C",
        "Academic Block",
        "Library",
        "Other"
    )

    var selectedBuilding by remember { mutableStateOf("") }
    var buildingMenuExpanded by remember { mutableStateOf(false) }
    var room by remember { mutableStateOf("") }
    var selectedEquipment by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var attemptedSubmit by remember { mutableStateOf(false) }
    var submitted by remember { mutableStateOf(false) }
    var reportId by remember { mutableStateOf("") }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        photoUri = uri
    }

    val roomValid = room.isNotBlank()
    val buildingValid = selectedBuilding.isNotBlank()
    val equipmentValid = selectedEquipment.isNotBlank()
    val descriptionValid = description.trim().length >= 10

    val formValid = roomValid &&
            buildingValid &&
            equipmentValid &&
            descriptionValid

    if (submitted) {
        SubmissionSuccessScreen(
            modifier = modifier,
            reportId = reportId,
            onDone = onSubmitted
        )
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ReportBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 18.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Go back",
                    tint = ReportInk
                )
            }

            Spacer(Modifier.width(5.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "POWERWATCH / REPORT",
                    color = ReportGreen,
                    fontSize = 10.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Report energy waste",
                    color = ReportInk,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Icon(
                Icons.Default.Bolt,
                contentDescription = null,
                tint = ReportGreen,
                modifier = Modifier.size(29.dp)
            )
        }

        // Intro banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(22.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(ReportDeepGreen, ReportGreen)
                    )
                )
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ElectricalServices,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(35.dp)
            )

            Spacer(Modifier.width(13.dp))

            Column {
                Text(
                    "See it. Report it.",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Help prevent unnecessary electricity use.",
                    color = Color.White.copy(alpha = 0.82f),
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }

        // Location section
        FormSectionHeader(
            number = "01",
            title = "Where did you spot it?",
            subtitle = "Help the campus team find the issue."
        )

        Column(verticalArrangement = Arrangement.spacedBy(9.dp)) {
            Text(
                "BUILDING / BLOCK",
                color = ReportMuted,
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold
            )

            Box {
                OutlinedButton(
                    onClick = { buildingMenuExpanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = ReportInk
                    )
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = ReportGreen
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = selectedBuilding.ifBlank {
                            "Select a building or block"
                        },
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        Icons.Default.Tune,
                        contentDescription = "Choose building",
                        tint = ReportMuted
                    )
                }

                DropdownMenu(
                    expanded = buildingMenuExpanded,
                    onDismissRequest = {
                        buildingMenuExpanded = false
                    },
                    modifier = Modifier.background(Color.White)
                ) {
                    buildings.forEach { building ->
                        DropdownMenuItem(
                            text = { Text(building) },
                            onClick = {
                                selectedBuilding = building
                                buildingMenuExpanded = false
                            }
                        )
                    }
                }
            }

            if (attemptedSubmit && !buildingValid) {
                ErrorHint("Please select a building.")
            }

            OutlinedTextField(
                value = room,
                onValueChange = { room = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Room / floor / specific location") },
                placeholder = { Text("e.g. Room 204 or Floor 2 corridor") },
                leadingIcon = {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                isError = attemptedSubmit && !roomValid
            )

            if (attemptedSubmit && !roomValid) {
                ErrorHint("Please enter the location.")
            }
        }

        HorizontalDivider(color = Color(0xFFE1E9E2))

        // Equipment section
        FormSectionHeader(
            number = "02",
            title = "What's being wasted?",
            subtitle = "Choose the equipment you noticed."
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            equipmentOptions.chunked(2).forEach { rowOptions ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowOptions.forEach { option ->
                        val selected = selectedEquipment == option.name

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(18.dp))
                                .background(
                                    if (selected) ReportMint else Color.White
                                )
                                .clickable {
                                    selectedEquipment = option.name
                                }
                                .then(
                                    Modifier.padding(
                                        horizontal = 12.dp,
                                        vertical = 16.dp
                                    )
                                ),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(13.dp))
                                    .background(
                                        if (selected) ReportGreen
                                        else Color(0xFFF0F4F0)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    option.icon,
                                    contentDescription = option.name,
                                    tint = if (selected) {
                                        Color.White
                                    } else {
                                        ReportGreen
                                    },
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(Modifier.height(12.dp))

                            Text(
                                option.name,
                                color = ReportInk,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(3.dp))

                            Text(
                                if (selected) "Selected" else "Tap to select",
                                color = if (selected) ReportGreen
                                else ReportMuted,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            if (attemptedSubmit && !equipmentValid) {
                ErrorHint("Please select an equipment type.")
            }
        }

        HorizontalDivider(color = Color(0xFFE1E9E2))

        // Description section
        FormSectionHeader(
            number = "03",
            title = "Tell us what happened",
            subtitle = "A little detail helps the team respond."
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = description,
                onValueChange = {
                    if (it.length <= 500) description = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 135.dp),
                label = { Text("Describe the issue") },
                placeholder = {
                    Text(
                        "e.g. The corridor lights were on " +
                                "even though the area was empty."
                    )
                },
                shape = RoundedCornerShape(17.dp),
                minLines = 4,
                maxLines = 6,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences
                ),
                isError = attemptedSubmit && !descriptionValid
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (attemptedSubmit && !descriptionValid) {
                    ErrorHint("Enter at least 10 characters.")
                } else {
                    Text(
                        "At least 10 characters",
                        color = ReportMuted,
                        fontSize = 10.sp
                    )
                }

                Text(
                    "${description.length}/500",
                    color = ReportMuted,
                    fontSize = 10.sp
                )
            }
        }

        // Optional attachment
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                "ADD A PHOTO (OPTIONAL)",
                color = ReportMuted,
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedButton(
                onClick = { photoPicker.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp)
            ) {
                Icon(
                    Icons.Default.CameraAlt,
                    contentDescription = null,
                    tint = ReportGreen
                )
                Spacer(Modifier.width(9.dp))
                Text(
                    if (photoUri == null) "Choose a photo"
                    else "Photo attached"
                )
            }

            if (photoUri != null) {
                Text(
                    "A photo has been selected. It is not uploaded or permanently stored.",
                    color = ReportMuted,
                    fontSize = 11.sp
                )

                TextButton(onClick = { photoUri = null }) {
                    Text("Remove photo", color = ReportOrange)
                }
            }
        }

        // Submit
        Button(
            onClick = {
                attemptedSubmit = true

                if (formValid) {
                    reportId = "PW-${UUID.randomUUID().toString().take(8).uppercase()}"
                    submitted = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(57.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ReportGreen
            )
        ) {
            Icon(
                Icons.Default.Send,
                contentDescription = null
            )
            Spacer(Modifier.width(10.dp))
            Text(
                "Submit report",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            "DEMO MODE · REPORTS ARE NOT YET SAVED TO A DATABASE",
            color = ReportMuted,
            fontSize = 9.sp,
            letterSpacing = 0.4.sp
        )
    }
}

@Composable
private fun FormSectionHeader(
    number: String,
    title: String,
    subtitle: String
) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(37.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(ReportMint),
            contentAlignment = Alignment.Center
        ) {
            Text(
                number,
                color = ReportGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                title,
                color = ReportInk,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                subtitle,
                color = ReportMuted,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun ErrorHint(message: String) {
    Text(
        message,
        color = MaterialTheme.colorScheme.error,
        fontSize = 11.sp
    )
}

@Composable
private fun SubmissionSuccessScreen(
    modifier: Modifier = Modifier,
    reportId: String,
    onDone: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ReportBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(450)) + scaleIn(tween(450))
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(ReportMint),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Report created",
                    tint = ReportGreen,
                    modifier = Modifier.size(58.dp)
                )
            }
        }

        Spacer(Modifier.height(27.dp))

        Text(
            "Report created!",
            color = ReportInk,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(Modifier.height(10.dp))

        Text(
            "Your demo report has passed validation. Database storage and sharing will be added in a later phase.",
            color = ReportMuted,
            fontSize = 14.sp,
            lineHeight = 22.sp
        )

        Spacer(Modifier.height(25.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(18.dp)
        ) {
            Text(
                "DEMO REPORT REFERENCE",
                color = ReportMuted,
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                reportId,
                color = ReportGreen,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "This reference is generated locally and is not yet registered on a server.",
                color = ReportMuted,
                fontSize = 11.sp,
                lineHeight = 17.sp
            )
        }

        Spacer(Modifier.height(26.dp))

        Button(
            onClick = onDone,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(17.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ReportGreen
            )
        ) {
            Text(
                "Back to reports",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
