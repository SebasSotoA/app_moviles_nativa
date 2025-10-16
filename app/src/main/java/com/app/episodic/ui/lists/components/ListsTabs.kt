package com.app.episodic.ui.lists.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.app.episodic.ui.lists.ListsTab
import com.app.episodic.ui.theme.EpisodicTheme

@Composable
fun ListsTabs(selectedTab: ListsTab, onTabSelected: (ListsTab) -> Unit) {
    val tabs = listOf(ListsTab.FAVORITOS, ListsTab.LISTAS)
    TabRow(selectedTabIndex = tabs.indexOf(selectedTab)) {
        tabs.forEachIndexed { idx, tab ->
            Tab(
                    selected = selectedTab == tab,
                    onClick = { onTabSelected(tab) },
                    text = {
                        Text(
                                text =
                                        when (tab) {
                                            ListsTab.FAVORITOS -> "Favoritos"
                                            ListsTab.LISTAS -> "Listas"
                                        },
                                style = MaterialTheme.typography.titleMedium
                        )
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListsTabs_Preview() {
    EpisodicTheme { ListsTabs(selectedTab = ListsTab.FAVORITOS, onTabSelected = {}) }
}
