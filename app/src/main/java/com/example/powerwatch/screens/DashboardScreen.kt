
package com.example.powerwatch.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Green = Color(0xFF087F5B)
private val DeepGreen = Color(0xFF064B39)
private val Mint = Color(0xFF78F0B3)
private val Background = Color(0xFFF5F8F5)
private val Ink = Color(0xFF172E25)
private val Muted = Color(0xFF75837B)
private val Orange = Color(0xFFB96B20)

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showReportForm by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Background,
        bottomBar = {
            if (!showReportForm) {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = {
                            Icon(Icons.Default.Home, contentDescription = "Home")
                        },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = {
                            Icon(Icons.Default.ListAlt, contentDescription = "Reports")
                        },
                        label = { Text("Reports") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = {
                            Icon(Icons.Default.Person, contentDescription = "Profile")
                        },
                        label = { Text("Profile") }
                    )
                }
            }
        }
    ) { padding ->
        when {
            showReportForm -> ReportFormScreen(
                modifier = Modifier.padding(padding),
                onBack = {
                    showReportForm = false
                },
                onSubmitted = {
                    showReportForm = false
                    selectedTab = 1
                }
            )

            selectedTab == 0 -> HomeContent(
                modifier = Modifier.padding(padding),
                onReport = { showReportForm = true },
                onViewReports = { selectedTab = 1 }
            )

            selectedTab == 1 -> ReportsContent(
                modifier = Modifier.padding(padding),
                onReport = { showReportForm = true }
            )

            else -> ProfileContent(
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onReport: () -> Unit,
    onViewReports: () -> Unit
) {
    var entered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        entered = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 22.dp, bottom = 26.dp),
        verticalArrangement = Arrangement.spacedBy(23.dp)
    ) {
        // Brand and greeting
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Bolt,
                        contentDescription = null,
                        tint = Green,
                        modifier = Modifier.size(19.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        "POWERWATCH / CAMPUS",
                        color = Green,
                        fontSize = 10.sp,
                        letterSpacing = 1.6.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    "Good to see you.",
                    color = Ink,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    "Every small action counts.",
                    color = Muted,
                    fontSize = 13.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE1F4E8)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Eco,
                    contentDescription = "Campus sustainability",
                    tint = Green,
                    modifier = Modifier.size(26.dp)
                )
            }
        }

        // Hero enters smoothly
        AnimatedVisibility(
            visible = entered,
            enter = fadeIn(tween(500)) +
                    slideInVertically(
                        initialOffsetY = { it / 5 },
                        animationSpec = tween(500)
                    )
        ) {
            EnergyCommandCard()
        }

        // Reporting action
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(23.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF087F5B), Color(0xFF0A674B))
                    )
                )
                .clickable { onReport() }
                .padding(19.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.ReportProblem,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(25.dp)
                )
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Found energy waste?",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Help your campus do better",
                    color = Color.White.copy(alpha = 0.78f),
                    fontSize = 12.sp
                )
            }

            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Open reporting",
                tint = Mint
            )
        }

        // Stats
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionTitle(
                "Your contribution",
                "Your demo activity at a glance"
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MetricTile(
                    modifier = Modifier.weight(1f),
                    title = "Reports made",
                    value = 8,
                    icon = Icons.Default.ReportProblem
                )
                MetricTile(
                    modifier = Modifier.weight(1f),
                    title = "Resolved",
                    value = 5,
                    icon = Icons.Default.CheckCircle
                )
            }
        }

        // Weekly chart
        WeeklyActivityCard()

        // Recent reports
        Column(verticalArrangement = Arrangement.spacedBy(13.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Recent activity",
                    color = Ink,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    "VIEW ALL  →",
                    color = Green,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.6.sp,
                    modifier = Modifier.clickable { onViewReports() }
                )
            }

            ActivityRow(
                title = "Lights left on",
                location = "Hostel Block A · Floor 2",
                status = "Resolved",
                resolved = true
            )
            ActivityRow(
                title = "Fan running in empty room",
                location = "Academic Block · Room 104",
                status = "Pending",
                resolved = false
            )
        }

        Text(
            "DEMO DATA · NOT LIVE ENERGY MEASUREMENTS",
            modifier = Modifier.fillMaxWidth(),
            color = Muted,
            fontSize = 9.sp,
            letterSpacing = 0.6.sp
        )
    }
}

@Composable
private fun EnergyCommandCard() {
    val progress by animateFloatAsState(
        targetValue = 0.72f,
        animationSpec = tween(1300),
        label = "impactProgress"
    )

    val resolved by animateIntAsState(
        targetValue = 5,
        animationSpec = tween(1000),
        label = "resolvedCount"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(29.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF063D30),
                        Color(0xFF087F5B),
                        Color(0xFF075541)
                    )
                )
            )
    ) {
        // Decorative orbital lines
        Canvas(
            modifier = Modifier
                .size(210.dp)
                .align(Alignment.TopEnd)
        ) {
            val center = Offset(size.width * 0.73f, size.height * 0.22f)
            drawCircle(
                color = Mint.copy(alpha = 0.07f),
                radius = 105.dp.toPx(),
                center = center
            )
            drawCircle(
                color = Mint.copy(alpha = 0.10f),
                radius = 78.dp.toPx(),
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 1.dp.toPx()
                )
            )
            drawCircle(
                color = Mint.copy(alpha = 0.15f),
                radius = 51.dp.toPx(),
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 1.dp.toPx()
                )
            )
        }

        Column(
            modifier = Modifier.padding(21.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Mint)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "YOUR ENERGY IMPACT",
                    color = Mint,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.5.sp
                )
            }

            Spacer(Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Make every",
                        color = Color.White.copy(alpha = 0.75f),
                        fontSize = 13.sp
                    )
                    Text(
                        "watt count.",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 33.sp
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "$resolved reports resolved",
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(5.dp))
                    Text(
                        "Keep building better habits.",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )
                }

                Box(
                    modifier = Modifier.size(104.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxSize(),
                        color = Mint,
                        trackColor = Color.White.copy(alpha = 0.16f),
                        strokeWidth = 8.dp,
                        strokeCap = StrokeCap.Round
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Bolt,
                            contentDescription = null,
                            tint = Mint,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            "${(progress * 100).toInt()}%",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 19.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(22.dp))

            HorizontalDivider(color = Color.White.copy(alpha = 0.18f))

            Spacer(Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "CONSERVATION CHALLENGE",
                    color = Color.White.copy(alpha = 0.76f),
                    fontSize = 9.sp,
                    letterSpacing = 0.8.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "DEMO GOAL",
                    color = Mint,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    subtitle: String
) {
    Column {
        Text(
            title,
            color = Ink,
            fontSize = 19.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            subtitle,
            color = Muted,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun MetricTile(
    title: String,
    value: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    val count by animateIntAsState(
        targetValue = value,
        animationSpec = tween(850),
        label = title
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                title.uppercase(),
                color = Muted,
                fontSize = 9.sp,
                letterSpacing = 0.6.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                icon,
                contentDescription = null,
                tint = Green,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            "$count",
            color = Ink,
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(CircleShape)
                .background(Color(0xFFE6EEE8))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(if (value == 0) 0f else value / 10f)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(Green)
            )
        }
    }
}

@Composable
private fun WeeklyActivityCard() {
    val values = listOf(0.35f, 0.60f, 0.42f, 0.83f, 0.55f, 0.95f, 0.48f)
    val days = listOf("M", "T", "W", "T", "F", "S", "S")
    var animateBars by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animateBars = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White)
            .padding(19.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Weekly activity",
                    color = Ink,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Illustrative report activity",
                    color = Muted,
                    fontSize = 11.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE5F5EB))
                    .padding(horizontal = 10.dp, vertical = 7.dp)
            ) {
                Text(
                    "7 DAYS",
                    color = Green,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.7.sp
                )
            }
        }

        Spacer(Modifier.height(23.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(105.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            values.forEachIndexed { index, value ->
                val animatedHeight by animateFloatAsState(
                    targetValue = if (animateBars) value else 0.05f,
                    animationSpec = tween(
                        durationMillis = 650,
                        delayMillis = index * 75
                    ),
                    label = "bar$index"
                )

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(82.dp),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.68f)
                                .fillMaxHeight(animatedHeight)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (index == 5) Green
                                    else Color(0xFFB8E7CB)
                                )
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        days[index],
                        color = if (index == 5) Green else Muted,
                        fontSize = 10.sp,
                        fontWeight = if (index == 5) {
                            FontWeight.ExtraBold
                        } else {
                            FontWeight.Normal
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityRow(
    title: String,
    location: String,
    status: String,
    resolved: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(19.dp))
            .background(Color.White)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    if (resolved) Color(0xFFE1F4E8)
                    else Color(0xFFFFF0DD)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                if (resolved) Icons.Default.CheckCircle
                else Icons.Default.AccessTime,
                contentDescription = null,
                tint = if (resolved) Green else Orange
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                color = Ink,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(5.dp))
            Text(
                location,
                color = Muted,
                fontSize = 10.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(Modifier.width(6.dp))

        Column(horizontalAlignment = Alignment.End) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(if (resolved) Green else Orange)
            )
            Spacer(Modifier.height(5.dp))
            Text(
                status,
                color = if (resolved) Green else Orange,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ReportsContent(
    modifier: Modifier = Modifier,
    onReport: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "My reports",
            color = Ink,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            "Your sample report history.",
            color = Muted,
            fontSize = 13.sp
        )

        ActivityRow(
            "Lights left on",
            "Hostel Block A · Floor 2",
            "Resolved",
            true
        )
        ActivityRow(
            "Fan running in empty room",
            "Academic Block · Room 104",
            "Pending",
            false
        )

        Button(
            onClick = onReport,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green
            )
        ) {
            Icon(Icons.Default.ReportProblem, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Report energy waste")
        }
    }
}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(86.dp)
                .clip(CircleShape)
                .background(Color(0xFFE1F4E8)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                tint = Green,
                modifier = Modifier.size(42.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "Your PowerWatch profile",
            color = Ink,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Student accounts and settings will be added later.",
            color = Muted,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ReportFormPlaceholder(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        OutlinedButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Back to dashboard")
        }

        Icon(
            Icons.Default.ReportProblem,
            contentDescription = null,
            tint = Green,
            modifier = Modifier.size(48.dp)
        )

        Text(
            "Report energy waste",
            color = Ink,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            "The full reporting form comes in Phase 3. Next, we'll add building, room, equipment type, description, and photo options.",
            color = Muted,
            fontSize = 15.sp,
            lineHeight = 23.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFFE1F4E8))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Bolt,
                contentDescription = null,
                tint = Green
            )
            Spacer(Modifier.width(12.dp))
            Text(
                "Report waste. Protect energy.",
                color = DeepGreen,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
