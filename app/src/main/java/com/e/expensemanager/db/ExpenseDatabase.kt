package com.e.expensemanager.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Expense::class, CreDebData::class],
    version = 2
)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun getExpenseDao(): ExpenseDao

    abstract fun getCredDebDao(): CreDebDao

    companion object {
        @Volatile
        private var instance: ExpenseDatabase? = null

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ExpenseDatabase::class.java, "expenseDB.db"
        ).fallbackToDestructiveMigration().build()

        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

    }


}