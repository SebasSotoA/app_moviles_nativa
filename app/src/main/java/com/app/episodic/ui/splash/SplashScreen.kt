package com.app.episodic.ui.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.episodic.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(1800)
        onFinished()
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0B1E5B), // azul oscuro superior
            Color(0xFF6A0DAA)  // púrpura inferior
        )
    )

    // Animación suave del indicador
    val infinite = rememberInfiniteTransition(label = "splash")
    val progress by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "progress"
    )

    Box(
        modifier
            .fillMaxSize()
            .background(gradient)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher),
                contentDescription = "Episodic logo",
                modifier = Modifier
                    .size(88.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Episodic",
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            )

            Text(
                text = "Descubre tu próxima película\nfavorita",
                modifier = Modifier.padding(top = 6.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFEAEAEA),
                    textAlign = TextAlign.Center
                )
            )

            Text(
                text = "Cargando...",
                modifier = Modifier.padding(top = 28.dp, bottom = 6.dp),
                style = MaterialTheme.typography.labelLarge.copy(color = Color(0xFFEAEAEA))
            )

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .height(6.dp)
                    .clip(RoundedCornerShape(50))
                    .fillMaxWidth(0.9f),
                color = Color(0xFF3E6BFF),
                trackColor = Color(0x33000000)
            )
        }
    }
}
