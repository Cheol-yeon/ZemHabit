package com.example.zemhabit.Room

import android.content.Context
import androidx.room.Room
import com.example.zemhabit.Data.HabitSettingData

class RoomAccess(context: Context) {
//    constructor(context: RecyclerView.Adapter<FoodAdapter.ViewHolder>) : this(IntroActivity())

    val habitDB = Room.databaseBuilder(context, AppDatabase::class.java, "habitDB").build()

    fun habitInsert(data: HabitSettingData) {
        val insertTread = Thread(Runnable {
            habitDB.HabitDataDao().insertAll(data)
        })
        insertTread.start()
        habitDB.close()
    }

    fun habitGetAll(): List<HabitSettingData> {
        var habitDatas = listOf<HabitSettingData>()
        val getAllThread = Thread(Runnable {
            habitDatas = habitDB.HabitDataDao().getAll()
        })
        getAllThread.start()
        getAllThread.join()
        habitDB.close()
        return habitDatas
    }

    fun habitGetCount(): Int {
        var count: Int = 0
        val countThread = Thread(Runnable {
            count = habitDB.HabitDataDao().getCount()
        })
        countThread.start()
        countThread.join()
        habitDB.close()
        return count
    }

    fun habitGetById(id: Int): HabitSettingData {
        var data: HabitSettingData? = null
        val getThread = Thread(Runnable {
            data = habitDB.HabitDataDao().getById(id)
        })
        getThread.start()
        getThread.join()
        habitDB.close()
        return data!!
    }

    fun habitGetAllById(items: ArrayList<Int>): List<HabitSettingData> {
        var habitData: List<HabitSettingData>? = null
        val getAllThread = Thread(Runnable {
            habitData = habitDB.HabitDataDao().getAllById(items)
        })
        getAllThread.start()
        getAllThread.join()
        habitDB.close()
        return habitData!!
    }

    fun getByType(type: String): List<HabitSettingData> {
        var habitData: List<HabitSettingData>? = null
        val getByTypeThread = Thread(Runnable {
            habitData = habitDB.HabitDataDao().getByType(type)
        })
        getByTypeThread.start()
        getByTypeThread.join()
        habitDB.close()
        return habitData!!
    }

    fun deleteAll() {
        val deleteAllThread = Thread(Runnable {
            habitDB.HabitDataDao().deleteAll()
        })
        deleteAllThread.start()
        deleteAllThread.join()
        habitDB.close()
    }

    fun deleteById(item_no: Int) {
        val deleteByIdThread = Thread(Runnable {
            habitDB.HabitDataDao().deleteById(item_no)
        })
        deleteByIdThread.start()
        deleteByIdThread.join()
        habitDB.close()
    }

    fun updateTypeToProceedById(item_no: Int) {
        val updateTypeToProceedByIdThread = Thread(Runnable {
            habitDB.HabitDataDao().updateTypeToProceedById(item_no)
        })
        updateTypeToProceedByIdThread.start()
        updateTypeToProceedByIdThread.join()
        habitDB.close()
    }

    fun updateById(item_no: Int, start_date: String, end_date: String, doy: String, alarm: String, zemcon_count: String) {
        val updateByIdThread = Thread(Runnable {
            habitDB.HabitDataDao().updateById(item_no, start_date, end_date, doy, alarm, zemcon_count)
        })
        updateByIdThread.start()
        updateByIdThread.join()
        habitDB.close()
    }

    fun getMulType(types: List<String>): List<HabitSettingData> {
        var habitData: List<HabitSettingData>? = null
        val getMulTypeThread = Thread(Runnable {
            habitData = habitDB.HabitDataDao().getMulType(types)
        })
        getMulTypeThread.start()
        getMulTypeThread.join()
        habitDB.close()
        return habitData!!
    }

    fun updateTypeToCompletionById(item_no: Int) {
        val updateTypeToCompletionByIdThread = Thread(Runnable {
            habitDB.HabitDataDao().updateTypeToCompletionById(item_no)
        })
        updateTypeToCompletionByIdThread.start()
        updateTypeToCompletionByIdThread.join()
        habitDB.close()
    }

    fun updateTypeToCompleteById(item_no: Int) {
        val updateTypeToCompleteByIdThread = Thread(Runnable {
            habitDB.HabitDataDao().updateTypeToCompleteById(item_no)
        })
        updateTypeToCompleteByIdThread.start()
        updateTypeToCompleteByIdThread.join()
        habitDB.close()
    }

    fun R_updateTypeToCompleteById(item_no: Int) {
        val R_updateTypeToCompleteByIdThread = Thread(Runnable {
            habitDB.HabitDataDao().R_updateTypeToCompleteById(item_no)
        })
        R_updateTypeToCompleteByIdThread.start()
        R_updateTypeToCompleteByIdThread.join()
        habitDB.close()
    }

    fun sumZemcon(): Int {
        var count: Int? = 0
        val sumZemconThread = Thread(Runnable {
            count = habitDB.HabitDataDao().sumZemcon()
        })
        sumZemconThread.start()
        sumZemconThread.join()
        habitDB.close()
        return count!!
    }
}
