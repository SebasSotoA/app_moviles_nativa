package com.app.episodic.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.app.episodic.ui.theme.loadingBackground
import com.app.episodic.ui.theme.loadingBackgroundDark
import com.app.episodic.ui.theme.loadingPrimary
import com.app.episodic.ui.theme.loadingSecondary

@Composable
fun LoadingView(modifier: Modifier = Modifier, isLoading: Boolean) {
    val isDarkTheme = isSystemInDarkTheme()
    
    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (isDarkTheme) {
                            listOf(
                                loadingBackgroundDark,
                                loadingBackgroundDark.copy(alpha = 0.95f)
                            )
                        } else {
                            listOf(
                                loadingBackground,
                                loadingBackground.copy(alpha = 0.95f)
                            )
                        }
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.6f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Animated loading indicator
                CircularProgressIndicator(
                    modifier = Modifier.size(56.dp),
                    color = loadingPrimary,
                    strokeWidth = 5.dp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Loading text
                Text(
                    text = "Cargando...",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDarkTheme) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtitle text
                Text(
                    text = "Por favor espera...",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isDarkTheme) {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    }
                )
            }
        }
    }
}