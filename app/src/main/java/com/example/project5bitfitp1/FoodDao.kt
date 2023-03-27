package com.example.project5bitfitp1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Query("SELECT * FROM food_table")
    fun getAll(): Flow<List<FoodEntity>>

    @Insert
    fun insert(foodEntity: FoodEntity)

    @Insert
    fun insertAll(Foods: List<FoodEntity>)

    @Query("DELETE FROM food_table")
    fun deleteAll()
}

