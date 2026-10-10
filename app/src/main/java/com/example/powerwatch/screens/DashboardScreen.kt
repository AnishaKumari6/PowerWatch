
package com.example.powerwatch.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PWGreen = Color(0xFF087F5B)
private val PWDeepGreen = Color(0xFF065F46)
private val PWBackground = Color(0xFFF7FAF8)
private val PWLightGreen = Color(0xFFE6F5EC)
private val PWText = Color(0xFF19352B)
private val PWMuted = Color(0xFF718078)
private val PWOrange = Color(0xFFB96B20)

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showReportForm by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PWBackground,
        bottomBar = {
            if (!showReportForm) {
                NavigationBar(
                    containerColor = Color.White
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
    ) { innerPadding ->
        when {
            showReportForm -> {
                ReportPlaceholderScreen(
                    modifier = Modifier.padding(innerPadding),
                    onBack = { showReportForm = false }
                )
            }

            selectedTab == 0 -> {
                HomeDashboardContent(
                    modifier = Modifier.padding(innerPadding),
                    onReportWaste = { showReportForm = true },
                    onViewReports = { selectedTab = 1 }
                )
            }

            selectedTab == 1 -> {
                ReportsPlaceholderScreen(
                    modifier = Modifier.padding(innerPadding),
                    onReportWaste = { showReportForm = true }
                )
            }

            else -> {
                ProfilePlaceholderScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
private fun HomeDashboardContent(
    modifier: Modifier = Modifier,
    onReportWaste: () -> Unit,
    onViewReports: () -> Unit
) {
    val scrollState = rememberScrollState()
    var hasEntered by remember { mutableStateOf(false) }

    androidx.compose.runtime.LaunchedEffect(Unit) {
        hasEntered = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp)
            .padding(top = 20.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "POWERWATCH",
                    color = PWGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 2.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Hey, energy saver!",
                    color = PWText,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Let's make your campus greener.",
                    color = PWMuted,
                    fontSize = 13.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PWLightGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Eco,
                    contentDescription = "PowerWatch",
                    tint = PWGreen,
                    modifier = Modifier.size(27.dp)
                )
            }
        }

        // Animated impact card
        AnimatedVisibility(
            visible = hasEntered,
            enter = fadeIn(tween(500)) +
                    slideInVertically(
                        animationSpec = tween(500),
                        initialOffsetY = { it / 5 }
                    )
        ) {
            EnergyImpactCard()
        }

        // Report action
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onReportWaste() },
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = PWGreen
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(Color.White.copy(alpha = 0.16f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ReportProblem,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(27.dp)
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Spot energy waste?",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "Report it. Make a difference.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                }

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Open report form",
                    tint = Color.White
                )
            }
        }

        // Summary statistics
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionHeading(
                title = "Your impact",
                subtitle = "A little action goes a long way"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Reports made",
                    value = 8,
                    icon = Icons.Default.ReportProblem,
                    modifier = Modifier.weight(1f)
                )

                StatCard(
                    title = "Resolved",
                    value = 5,
                    icon = Icons.Default.CheckCircle,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Recent reports
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent reports",
                    color = PWText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "View all",
                    color = PWGreen,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onViewReports() }
                )
            }

            ReportItem(
                title = "Lights left on",
                location = "Hostel Block A · Floor 2",
                status = "Resolved",
                resolved = true
            )

            ReportItem(
                title = "Fan running in empty room",
                location = "Academic Block · Room 104",
                status = "Pending",
                resolved = false
            )
        }

        Text(
            text = "DEMO DATA · Actual savings will be calculated later",
            color = PWMuted,
            fontSize = 10.sp,
            letterSpacing = 0.4.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
        )
    }
}

@Composable
private fun EnergyImpactCard() {
    val energySaved by animateIntAsState(
        targetValue = 12,
        animationSpec = tween(durationMillis = 1200),
        label = "energySaved"
    )

    val progress by androidx.compose.animation.core.animateFloatAsState(
        targetValue = 0.72f,
        animationSpec = tween(durationMillis = 1200),
        label = "conservationProgress"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = PWGreen,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = "ENERGY IMPACT",
                        color = PWMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$energySaved",
                        color = PWDeepGreen,
                        fontSize = 43.sp,
                        lineHeight = 46.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = "reports",
                        color = PWMuted,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(bottom = 7.dp)
                    )
                }

                Text(
                    text = "resolved in the demo",
                    color = PWMuted,
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = PWGreen,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(Modifier.width(5.dp))

                    Text(
                        text = "Keep the momentum going!",
                        color = PWGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Box(
                modifier = Modifier.size(94.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = PWGreen,
                    trackColor = PWLightGreen,
                    strokeWidth = 9.dp
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${(progress * 100).toInt()}%",
                        color = PWDeepGreen,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "demo goal",
                        color = PWMuted,
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeading(
    title: String,
    subtitle: String
) {
    Column {
        Text(
            text = title,
            color = PWText,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(3.dp))

        Text(
            text = subtitle,
            color = PWMuted,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    value: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    val animatedValue by animateIntAsState(
        targetValue = value,
        animationSpec = tween(900),
        label = title
    )

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PWGreen,
                modifier = Modifier.size(24.dp)
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "$animatedValue",
                color = PWText,
                fontSize = 29.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(3.dp))

            Text(
                text = title,
                color = PWMuted,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ReportItem(
    title: String,
    location: String,
    status: String,
    resolved: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(43.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(if (resolved) PWLightGreen else Color(0xFFFFF1DF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (resolved) {
                        Icons.Default.CheckCircle
                    } else {
                        Icons.Default.AccessTime
                    },
                    contentDescription = null,
                    tint = if (resolved) PWGreen else PWOrange
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = PWText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = location,
                    color = PWMuted,
                    fontSize = 11.sp
                )
            }

            Spacer(Modifier.width(5.dp))

            Surface(
                shape = RoundedCornerShape(50),
                color = if (resolved) PWLightGreen else Color(0xFFFFF1DF)
            ) {
                Text(
                    text = status,
                    color = if (resolved) PWGreen else PWOrange,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        horizontal = 9.dp,
                        vertical = 6.dp
                    )
                )
            }
        }
    }
}

@Composable
private fun ReportsPlaceholderScreen(
    modifier: Modifier = Modifier,
    onReportWaste: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "My Reports",
            color = PWText,
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "Your sample report history appears here. We'll add real filtering and report details later.",
            color = PWMuted,
            fontSize = 14.sp
        )

        ReportItem(
            title = "Lights left on",
            location = "Hostel Block A · Floor 2",
            status = "Resolved",
            resolved = true
        )

        ReportItem(
            title = "Fan running in empty room",
            location = "Academic Block · Room 104",
            status = "Pending",
            resolved = false
        )

        Button(
            onClick = onReportWaste,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = PWGreen)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Report energy waste")
        }
    }
}

@Composable
private fun ProfilePlaceholderScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(PWLightGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = PWGreen,
                modifier = Modifier.size(42.dp)
            )
        }

        Spacer(Modifier.height(18.dp))

        Text(
            text = "Your PowerWatch profile",
            color = PWText,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Student accounts and profile settings will be added with Firebase.",
            color = PWMuted,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ReportPlaceholderScreen(
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
            imageVector = Icons.Default.ReportProblem,
            contentDescription = null,
            tint = PWGreen,
            modifier = Modifier.size(48.dp)
        )

        Text(
            text = "Report energy waste",
            color = PWText,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "The full reporting form is coming in Phase 3. For now, this screen confirms that dashboard navigation works.",
            color = PWMuted,
            fontSize = 15.sp,
            lineHeight = 23.sp
        )

        Card(
            colors = CardDefaults.cardColors(containerColor = PWLightGreen),
            shape = RoundedCornerShape(18.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = PWGreen
                )

                Spacer(Modifier.width(12.dp))

                Text(
                    text = "Next: select a building, location, and equipment type.",
                    color = PWDeepGreen,
                    fontSize = 14.sp
                )
            }
        }
    }
}
