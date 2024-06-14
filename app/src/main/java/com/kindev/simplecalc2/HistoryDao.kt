//HistoryDao.kt
package com.kindev.simplecalc2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyItem: HistoryItem)

    @Query("SELECT * FROM history_table ORDER BY id DESC")
    fun getAllHistory(): Flow<List<HistoryItem>>

    @Query("DELETE FROM history_table")
    suspend fun deleteAll()
}
