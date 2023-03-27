package com.example.project5bitfitp1

import android.app.Application

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEmpty

class FoodApplication internal constructor(context: Context) {

    val db: AppDatabase

    fun initFirstFood() {
        db.foodDao().getAll().onEmpty {
            insertFood(FoodEntity(0,"Insert your food here", "Insert your calories here"))
        }
    }

    fun getAllFoods(): Flow<List<FoodEntity>> {
        return db.foodDao().getAll()
    }

    fun insertFood(food: FoodEntity) {
        db.foodDao().insert(food)
    }

//    fun deleteCard(flashcardQuestion: String) {
//        val allFoods = db.foodDao().getAll()
//        for (food in allFoods) {
//            if (food.question == flashcardQuestion) {
//                db.flashcardDao().delete(card)
//            }
//        }
//    }

//    fun updateCard(flashcard: Flashcard) {
//        db.flashcardDao().update(flashcard)
//    }
//
//    fun deleteAll() {
//        for (flashcard in db.flashcardDao().getAll()) {
//            db.flashcardDao().delete(flashcard)
//        }
//    }

    init {
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "Foods-db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}
