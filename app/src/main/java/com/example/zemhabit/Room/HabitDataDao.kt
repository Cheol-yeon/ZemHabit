package com.example.zemhabit.Room

import androidx.room.*
import com.example.zemhabit.Data.HabitSettingData
import kotlin.collections.ArrayList

@Dao
interface HabitDataDao {

    @Query("SELECT * FROM HabitSettingData ORDER BY habit_no")
    fun getAll(): List<HabitSettingData>

    @Query("SELECT * FROM HabitSettingData WHERE habit_no = (:item_no)")
    fun getById(item_no: Int): HabitSettingData

    @Query("SELECT * FROM HabitSettingData WHERE habit_no IN (:items)")
    fun getAllById(items: ArrayList<Int>): List<HabitSettingData>

    @Query("SELECT COUNT(*) FROM HabitSettingData")
    fun getCount(): Int

    @Query("SELECT * FROM HabitSettingData WHERE TYPE = (:type)")
    fun getByType(type: String): List<HabitSettingData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: HabitSettingData)

    @Delete
    fun delete(user: HabitSettingData)

    @Query("DELETE FROM HabitSettingData")
    fun deleteAll()

    @Query("DELETE FROM HabitSettingData WHERE habit_no = (:item_no)")
    fun deleteById(item_no: Int)

    @Query("UPDATE HabitSettingData SET type = '진행중' WHERE habit_no = (:item_no)")
    fun updateTypeToProceedById(item_no: Int)

    @Query("UPDATE HabitSettingData SET zemcon = (:zemcon_count), start_date = (:start_date), end_date = (:end_date), day_of_week = (:doy), alarm = (:alarm), type = '수정대기' WHERE habit_no = (:item_no)")
    fun updateById(item_no: Int, start_date: String, end_date: String, doy: String, alarm: String, zemcon_count: String)

    @Query("SELECT * FROM HabitSettingData WHERE TYPE IN (:types)")
    fun getMulType(types: List<String>): List<HabitSettingData>

    @Query("UPDATE HabitSettingData SET type = '완료요청' WHERE habit_no = (:item_no)")
    fun updateTypeToCompletionById(item_no: Int)

    @Query("UPDATE HabitSettingData SET type = '완료' WHERE habit_no = (:item_no)")
    fun updateTypeToCompleteById(item_no: Int)

    @Query("UPDATE HabitSettingData SET type = '칭찬완료' WHERE habit_no = (:item_no)")
    fun R_updateTypeToCompleteById(item_no: Int)

    @Query("SELECT SUM(zemcon) FROM HabitSettingData")
    fun sumZemcon(): Int
}