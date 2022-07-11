package com.example.zemhabit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.zemhabit.Fragment.HabitCategory
import com.example.zemhabit.Fragment.HabitListFragment
import com.example.zemhabit.Room.RoomAccess
import com.example.zemhabit.databinding.ActivityMainBinding
import com.example.zemhabit.databinding.ActivityMakeHabitBinding

class MakeHabitActivity : AppCompatActivity() {

    private var mBinding: ActivityMakeHabitBinding? = null
    private val binding get() = mBinding!!

    val habitCategory = HabitCategory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMakeHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackArrow.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.fcv.id, habitCategory).addToBackStack(null).commit()
        Log.e("MakeHabitActivity", "${RoomAccess(applicationContext).habitGetAll()}")

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val currentFragment: Fragment? = supportFragmentManager.findFragmentById(binding.fcv.id)
        Log.e("onBackPressed", "${currentFragment}")
        if(currentFragment==null) {
            finish()
        }
    }
}