package com.app.episodic.custom_lists.domain.repository

import com.app.episodic.custom_lists.domain.models.CustomList
import com.app.episodic.custom_lists.domain.models.ListItem
import kotlinx.coroutines.flow.Flow

interface CustomListsRepository {
    suspend fun createList(name: String): CustomList
    suspend fun getAllLists(): Flow<List<CustomList>>
    suspend fun getListById(listId: String): CustomList?
    suspend fun addItemToList(listId: String, item: ListItem)
    suspend fun removeItemFromList(listId: String, itemId: Int)
    suspend fun renameList(listId: String, newName: String)
    suspend fun deleteList(listId: String)
    suspend fun isItemInList(listId: String, itemId: Int): Boolean
}
