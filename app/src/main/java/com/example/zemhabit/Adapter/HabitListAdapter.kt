package com.example.zemhabit.Adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zemhabit.Fragment.HabitListFragment
import com.example.zemhabit.Fragment.SetHabitFragment
import com.example.zemhabit.MakeHabitActivity
import com.example.zemhabit.R
import kotlinx.android.synthetic.main.fragment_habit.*
import kotlinx.android.synthetic.main.habit_list_item.view.*


class HabitListAdapter(
    private var context: Context,
    private val habitListDatas: ArrayList<String>,
    private val habitTitle: String,
    private val imgIcon: Int
) :
    RecyclerView.Adapter<HabitListAdapter.ViewHolder>() {

    init {
        Log.e("Adapter", "In HabitList Adapter + ${imgIcon}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = this.context
        val habitTitle = this.habitTitle
        val imgIcon = this.imgIcon
        val view = LayoutInflater.from(context).inflate(R.layout.habit_list_item, parent, false)
        return ViewHolder(view, context, habitTitle, imgIcon)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habitListDatas[position])
    }

    class ViewHolder(
        private val view: View,
        private val mContext: Context,
        private val habitTitle: String,
        private val imgIcon: Int
    ) : RecyclerView.ViewHolder(view) {
        val title: String = this.habitTitle
        val icon: Int = this.imgIcon
        fun bind(habitListDatas: String) {
            view.tv_listItem.text = "${habitListDatas}"
            view.tv_listItem.setOnClickListener(View.OnClickListener {
                val appCompatActivity = mContext as MakeHabitActivity
                appCompatActivity.supportFragmentManager.
                beginTransaction()
                    .replace(R.id.fcv, SetHabitFragment().apply {
                        arguments = Bundle().apply {
                            putString("listItem", "${habitListDatas}")
                            Log.e("HabitListAdapter", "listItem = ${habitListDatas}")
                            putString("habitTitle", "${title}")
                            Log.e("HabitListAdapter", "Title = ${title}")
                            Log.e("Adapter", "In HabitList Adapter + ${icon}")
                            putInt("imgIcon", icon)
                        }
                    })
                    .addToBackStack(null)
                    .commit()
            })
        }
    }

    override fun getItemCount(): Int = habitListDatas.size
}