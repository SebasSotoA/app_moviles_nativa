package com.app.episodic.ui.lists.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.Tune
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
fun RecentActivityHeader(onSort: () -> Unit, onFilter: () -> Unit) {
    Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
                text = "Actividad Reciente",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onSort) { Icon(Icons.Filled.Sort, contentDescription = "Ordenar") }
        IconButton(onClick = onFilter) { Icon(Icons.Filled.Tune, contentDescription = "Filtrar") }
    }
}

@Preview
@Composable
fun RecentActivityHeader_Preview() {
    EpisodicTheme { RecentActivityHeader(onSort = {}, onFilter = {}) }
}
