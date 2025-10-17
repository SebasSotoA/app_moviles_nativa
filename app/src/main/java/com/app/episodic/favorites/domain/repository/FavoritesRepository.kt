package com.app.episodic.favorites.domain.repository

import com.app.episodic.favorites.domain.models.FavoriteItem
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun addToFavorites(item: FavoriteItem)
    suspend fun removeFromFavorites(itemId: Int)
    suspend fun isFavorite(itemId: Int): Boolean
    fun getAllFavorites(): Flow<List<FavoriteItem>>
    suspend fun getFavoriteById(itemId: Int): FavoriteItem?
}
