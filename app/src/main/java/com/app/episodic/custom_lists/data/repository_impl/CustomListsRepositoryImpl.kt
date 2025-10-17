package com.app.episodic.custom_lists.data.repository_impl

import android.content.Context
import android.content.SharedPreferences
import com.app.episodic.custom_lists.domain.models.CustomList
import com.app.episodic.custom_lists.domain.models.ListItem
import com.app.episodic.custom_lists.domain.repository.CustomListsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomListsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : CustomListsRepository {
    
    private val prefs: SharedPreferences = context.getSharedPreferences("custom_lists", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }
    
    override suspend fun createList(name: String): CustomList {
        val newList = CustomList(
            id = UUID.randomUUID().toString(),
            name = name,
            items = emptyList()
        )
        saveListsToPrefs(getAllListsFromPrefs() + newList)
        return newList
    }
    
    override suspend fun getAllLists(): Flow<List<CustomList>> = flow {
        emit(getAllListsFromPrefs())
    }
    
    override suspend fun getListById(listId: String): CustomList? {
        return getAllListsFromPrefs().find { it.id == listId }
    }
    
    override suspend fun addItemToList(listId: String, item: ListItem) {
        val lists = getAllListsFromPrefs().toMutableList()
        val listIndex = lists.indexOfFirst { it.id == listId }
        if (listIndex != -1) {
            val updatedList = lists[listIndex].copy(
                items = lists[listIndex].items + item,
                updatedAt = System.currentTimeMillis()
            )
            lists[listIndex] = updatedList
            saveListsToPrefs(lists)
        }
    }
    
    override suspend fun removeItemFromList(listId: String, itemId: Int) {
        val lists = getAllListsFromPrefs().toMutableList()
        val listIndex = lists.indexOfFirst { it.id == listId }
        if (listIndex != -1) {
            val updatedList = lists[listIndex].copy(
                items = lists[listIndex].items.filter { it.id != itemId },
                updatedAt = System.currentTimeMillis()
            )
            lists[listIndex] = updatedList
            saveListsToPrefs(lists)
        }
    }
    
    override suspend fun renameList(listId: String, newName: String) {
        val lists = getAllListsFromPrefs().toMutableList()
        val listIndex = lists.indexOfFirst { it.id == listId }
        if (listIndex != -1) {
            val updatedList = lists[listIndex].copy(
                name = newName,
                updatedAt = System.currentTimeMillis()
            )
            lists[listIndex] = updatedList
            saveListsToPrefs(lists)
        }
    }
    
    override suspend fun deleteList(listId: String) {
        val lists = getAllListsFromPrefs().filter { it.id != listId }
        saveListsToPrefs(lists)
    }
    
    override suspend fun isItemInList(listId: String, itemId: Int): Boolean {
        val list = getListById(listId)
        return list?.items?.any { it.id == itemId } ?: false
    }
    
    private fun getAllListsFromPrefs(): List<CustomList> {
        val listsJson = prefs.getString("custom_lists", "[]") ?: "[]"
        return try {
            json.decodeFromString<List<CustomList>>(listsJson)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun saveListsToPrefs(lists: List<CustomList>) {
        val listsJson = json.encodeToString(lists)
        prefs.edit().putString("custom_lists", listsJson).apply()
    }
}
