package com.app.episodic.ui.lists.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.episodic.ui.theme.EpisodicTheme

@Composable
fun ListsTopBar(title: String, onSearch: () -> Unit) {
    Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onSearch) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListsTopBar_Preview() {
    EpisodicTheme { ListsTopBar(title = "Mis Listas") {} }
}
