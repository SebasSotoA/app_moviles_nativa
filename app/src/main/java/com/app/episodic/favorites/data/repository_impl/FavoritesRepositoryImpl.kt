package com.app.episodic.favorites.data.repository_impl

import android.content.Context
import android.content.SharedPreferences
import com.app.episodic.favorites.domain.models.FavoriteItem
import com.app.episodic.favorites.domain.repository.FavoritesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FavoritesRepository {
    
    private val prefs: SharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }
    
    override suspend fun addToFavorites(item: FavoriteItem) {
        val favorites = getFavoritesFromPrefs().toMutableList()
        if (!favorites.any { it.id == item.id }) {
            favorites.add(item)
            saveFavoritesToPrefs(favorites)
        }
    }
    
    override suspend fun removeFromFavorites(itemId: Int) {
        val favorites = getFavoritesFromPrefs().toMutableList()
        favorites.removeAll { it.id == itemId }
        saveFavoritesToPrefs(favorites)
    }
    
    override suspend fun isFavorite(itemId: Int): Boolean {
        return getFavoritesFromPrefs().any { it.id == itemId }
    }
    
    override fun getAllFavorites(): Flow<List<FavoriteItem>> = flow {
        emit(getFavoritesFromPrefs())
    }
    
    override suspend fun getFavoriteById(itemId: Int): FavoriteItem? {
        return getFavoritesFromPrefs().find { it.id == itemId }
    }
    
    private fun getFavoritesFromPrefs(): List<FavoriteItem> {
        val favoritesJson = prefs.getString("favorites_list", "[]") ?: "[]"
        return try {
            json.decodeFromString<List<FavoriteItem>>(favoritesJson)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun saveFavoritesToPrefs(favorites: List<FavoriteItem>) {
        val favoritesJson = json.encodeToString(favorites)
        prefs.edit().putString("favorites_list", favoritesJson).apply()
    }
}
