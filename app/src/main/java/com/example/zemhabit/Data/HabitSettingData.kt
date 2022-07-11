package com.example.zemhabit.Data

import android.media.Image
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class HabitSettingData(
    @PrimaryKey
    val habit_no: Int,

    @ColumnInfo(name = "TYPE")
    val type: String,

    @ColumnInfo(name = "CATEGORY")
    val category: String,

    @ColumnInfo(name = "HABIT_TITLE")
    val habit_title: String,

    @ColumnInfo(name = "START_DATE")
    val start_date: String,

    @ColumnInfo(name = "END_DATE")
    val end_date: String,

    @ColumnInfo(name = "DAY_OF_WEEK")
    val day_of_week: String,

    @ColumnInfo(name = "ALARM")
    val alarm: String?,

    @ColumnInfo(name = "ZEMCON")
    val zemcon: String,

)
