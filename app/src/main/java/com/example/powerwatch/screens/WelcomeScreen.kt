package com.example.powerwatch.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Emerald = Color(0xFF087F5B)
private val DeepGreen = Color(0xFF065F46)
private val PaleGreen = Color(0xFFE8F7EF)
private val MutedText = Color(0xFF64748B)

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onGetStarted: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA))
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Brand
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Emerald),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(29.dp)
                )
            }

            Spacer(Modifier.width(10.dp))

            Column {
                Text(
                    text = "PowerWatch",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = DeepGreen
                )
                Text(
                    text = "SMARTER ENERGY HABITS",
                    fontSize = 9.sp,
                    letterSpacing = 1.4.sp,
                    color = MutedText,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Hero section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(176.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFD8F5E5),
                                Color(0xFFB5E8CC)
                            )
                        ),
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(124.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.75f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Eco,
                        contentDescription = "Energy conservation",
                        tint = Emerald,
                        modifier = Modifier.size(76.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = DeepGreen,
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-8).dp, y = (-12).dp)
                )
            }

            Spacer(Modifier.height(30.dp))

            Text(
                text = "Small actions.",
                fontSize = 35.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF172B24),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Big energy savings.",
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold,
                color = Emerald,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(14.dp))

            Text(
                text = "Spot energy waste in your hostel. " +
                        "Report it, track the impact, and help " +
                        "make your campus greener.",
                fontSize = 15.sp,
                lineHeight = 23.sp,
                color = MutedText,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }

        // Feature cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            FeatureItem(
                icon = Icons.Default.ReportProblem,
                title = "Report",
                subtitle = "Energy waste",
                modifier = Modifier.weight(1f)
            )

            FeatureItem(
                icon = Icons.Default.BarChart,
                title = "Track",
                subtitle = "Your impact",
                modifier = Modifier.weight(1f)
            )

            FeatureItem(
                icon = Icons.Default.Eco,
                title = "Save",
                subtitle = "Electricity",
                modifier = Modifier.weight(1f)
            )
        }

        // CTA
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Emerald,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "A cleaner campus starts with you.",
                fontSize = 12.sp,
                color = MutedText
            )
        }
    }
}

@Composable
private fun FeatureItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(PaleGreen)
            .padding(vertical = 17.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Emerald,
            modifier = Modifier.size(27.dp)
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = DeepGreen
        )

        Spacer(Modifier.height(3.dp))

        Text(
            text = subtitle,
            fontSize = 10.sp,
            color = MutedText,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NextScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Bolt,
            contentDescription = null,
            tint = Emerald,
            modifier = Modifier.size(64.dp)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = "Welcome to PowerWatch!",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = DeepGreen,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Your next step will be account creation " +
                    "and access to the energy reporting dashboard.",
            fontSize = 15.sp,
            lineHeight = 23.sp,
            color = MutedText,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(28.dp))

        OutlinedButton(onClick = onBack) {
            Text("Back to Welcome")
        }
    }
}
