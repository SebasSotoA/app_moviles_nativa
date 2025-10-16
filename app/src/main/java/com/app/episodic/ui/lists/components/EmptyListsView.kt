package com.app.episodic.ui.lists.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.episodic.ui.theme.EpisodicTheme

@Composable
fun EmptyListsView(title: String, subtitle: String, onCreateList: () -> Unit) {
    Column(
            modifier = Modifier.fillMaxWidth().padding(top = 60.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Image(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(28.dp))
        Button(onClick = onCreateList, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Crear una lista")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyListsView_Preview() {
    EpisodicTheme {
        Surface {
            EmptyListsView(
                    title = "Todavía no tienes ninguna lista.",
                    subtitle = "¡Hagamos una!",
                    onCreateList = {}
            )
        }
    }
}
