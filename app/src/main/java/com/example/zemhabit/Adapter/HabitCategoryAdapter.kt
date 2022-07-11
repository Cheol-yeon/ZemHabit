package com.example.zemhabit.Adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zemhabit.Data.HabitCategoryData
import com.example.zemhabit.Fragment.HabitListFragment
import com.example.zemhabit.Fragment.SetHabitFragment
import com.example.zemhabit.MakeHabitActivity
import com.example.zemhabit.R
import kotlinx.android.synthetic.main.habit_category_item.view.*

class HabitCategoryAdapter(private var context: Context, private val habitCategoryDatas: ArrayList<HabitCategoryData>) :
    RecyclerView.Adapter<HabitCategoryAdapter.ViewHolder>() {

    init {
        Log.e("Adapter", "In Category Adapter")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = this.context
        val view = LayoutInflater.from(context).inflate(R.layout.habit_category_item, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habitCategoryDatas[position])
    }

    class ViewHolder(private val view: View, private val mContext: Context) : RecyclerView.ViewHolder(view) {

        fun bind(habitCategoryData: HabitCategoryData) {
            if(habitCategoryData.main_title.equals("직접 입력")) {
                view.tv_subTitle.visibility = View.GONE
            }
            view.tv_subTitle.text = habitCategoryData.sub_title
            view.tv_mainTitle.text = habitCategoryData.main_title
            view.iv_categoryIcon.setImageResource(habitCategoryData.icon)
            view.setOnClickListener(View.OnClickListener {
                if(habitCategoryData.main_title.equals("직접 입력")) {
                    Log.e("CategoryAdapter", "Select ETC")
                    val appCompatActivity = mContext as MakeHabitActivity
                    appCompatActivity.supportFragmentManager.
                    beginTransaction()
                        .replace(R.id.fcv, SetHabitFragment().apply {
                            arguments = Bundle().apply {
                                putString("habitTitle", "기타")
                                Log.e("Select ETC", "기타")
                                putInt("imgIcon", habitCategoryData.icon)
                            }
                        })
                        .addToBackStack(null)
                        .commit()
                } else {
                    Log.e("CategoryAdapter", "${habitCategoryData.main_title} clicked")
                    val appCompatActivity = mContext as MakeHabitActivity
                    appCompatActivity.supportFragmentManager.
                    beginTransaction()
                        .replace(R.id.fcv, HabitListFragment().apply {
                            arguments = Bundle().apply {
                                putString("mainTitle", "${habitCategoryData.main_title}")
                                putString("subTitle", "${habitCategoryData.sub_title}")
                                putInt("imgIcon", habitCategoryData.icon)
                            }
                        })
                        .addToBackStack(null)
                        .commit()
                }
            })
        }
    }

    override fun getItemCount(): Int = habitCategoryDatas.size
}