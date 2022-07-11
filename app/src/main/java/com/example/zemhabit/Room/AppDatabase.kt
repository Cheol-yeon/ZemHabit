package com.example.zemhabit.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zemhabit.Data.HabitSettingData

@Database(entities = arrayOf(HabitSettingData::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract  fun HabitDataDao(): HabitDataDao
}