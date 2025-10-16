package com.app.episodic.ui.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun EpisodicBottomNavigation(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            title = "Inicio",
            icon = Icons.Default.Home,
            route = Route.HomeScreen().route
        ),
        BottomNavItem(
            title = "Explorar",
            icon = Icons.Default.Search,
            route = Route.ExploreScreen().route
        ),
        BottomNavItem(
            title = "Mis Listas",
            icon = Icons.Default.Favorite,
            route = Route.MyListsScreen().route
        )
    )

    NavigationBar(
        modifier = Modifier.height(90.dp)
    ) {
        
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        onNavigate(item.route)
                    }
                }
            )
        }
    }
}
