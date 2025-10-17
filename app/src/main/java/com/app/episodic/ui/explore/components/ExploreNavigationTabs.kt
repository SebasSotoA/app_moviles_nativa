package com.app.episodic.ui.explore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class ExploreTab {
    PELICULAS, SERIES, GENEROS
}

@Composable
fun ExploreNavigationTabs(
    modifier: Modifier = Modifier,
    selectedTab: ExploreTab = ExploreTab.PELICULAS,
    onTabSelected: (ExploreTab) -> Unit = {}
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ExploreTabItem(
                text = "Películas",
                isSelected = selectedTab == ExploreTab.PELICULAS,
                onClick = { onTabSelected(ExploreTab.PELICULAS) }
            )
            ExploreTabItem(
                text = "Series",
                isSelected = selectedTab == ExploreTab.SERIES,
                onClick = { onTabSelected(ExploreTab.SERIES) }
            )
            ExploreTabItem(
                text = "Géneros",
                isSelected = selectedTab == ExploreTab.GENEROS,
                onClick = { onTabSelected(ExploreTab.GENEROS) }
            )
        }
        
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
    }
}

@Composable
private fun ExploreTabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            },
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
        )
        
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(32.dp)
                    .padding(top = 8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(vertical = 2.dp)
            )
        } else {
            Box(
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExploreNavigationTabsPreview() {
    MaterialTheme {
        ExploreNavigationTabs()
    }
}
