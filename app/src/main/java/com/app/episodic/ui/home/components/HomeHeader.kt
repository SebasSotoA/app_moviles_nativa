package com.app.episodic.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearchClick: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Search Bar (takes most of the width)
            SearchBar(
                modifier = Modifier.weight(1f),
                searchText = searchText,
                onSearchTextChange = onSearchTextChange,
                onSearchClick = onSearchClick,
                onFilterClick = onFilterClick,
                placeholder = "Buscar"
            )
            
            // Spacer between search bar and title
            Spacer(modifier = Modifier.width(16.dp))
            
            // App Title
            Text(
                text = "Episodic",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeHeaderPreview() {
    MaterialTheme {
        HomeHeader(
            searchText = "",
            onSearchTextChange = { },
            onSearchClick = { },
            onFilterClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeHeaderWithSearchTextPreview() {
    MaterialTheme {
        HomeHeader(
            searchText = "Avengers",
            onSearchTextChange = { },
            onSearchClick = { },
            onFilterClick = { }
        )
    }
}
