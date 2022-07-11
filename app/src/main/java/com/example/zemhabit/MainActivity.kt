package com.example.zemhabit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.zemhabit.Fragment.HabitFragment
import com.example.zemhabit.R
import com.example.zemhabit.Room.RoomAccess
import com.example.zemhabit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private val habitFragment = HabitFragment()

    override fun onCreate(savedInstanceState: Bundle?) {

        Log.e("MainActivity", "Start MainActivity")
       setTheme(R.style.Theme_ZemHabit)

        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.e("MainActivity", "All Habits = ${RoomAccess(applicationContext).habitGetAll()}")

        binding.btnHabit.setOnClickListener(View.OnClickListener {
            Log.e("MainActivity", "Click Button")
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.flMainFrame.id, habitFragment).addToBackStack(null).commit()
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.zem_logo)

    }
}