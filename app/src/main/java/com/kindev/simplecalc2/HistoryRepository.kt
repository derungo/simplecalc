//HitoryRepository.kt
package com.kindev.simplecalc2

import kotlinx.coroutines.flow.Flow

class HistoryRepository(private val historyDao: HistoryDao) {

    val allHistory: Flow<List<HistoryItem>> = historyDao.getAllHistory()

    suspend fun insert(historyItem: HistoryItem) {
        historyDao.insert(historyItem)
    }

    suspend fun deleteAll() {
        historyDao.deleteAll()
    }
}
