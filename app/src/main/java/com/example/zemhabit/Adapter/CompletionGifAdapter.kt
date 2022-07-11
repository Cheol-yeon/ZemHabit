package com.example.zemhabit.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zemhabit.R
import kotlinx.android.synthetic.main.completion_gif_item.view.*
import kotlinx.android.synthetic.main.habit_category_item.view.*
import pl.droidsonroids.gif.GifDrawable


class CompletionGifAdapter(private var context: Context, private val gifDatas: ArrayList<GifDrawable>) :
    RecyclerView.Adapter<CompletionGifAdapter.ViewHolder>() {
    var selectedPos = -1

    init {
        Log.e("Adapter", "In CompletionGif Adapter")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = this.context
        val view = LayoutInflater.from(context).inflate(R.layout.completion_gif_item, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.gif_item.setImageDrawable(gifDatas[position])

        holder.itemView.gif_item.isSelected = selectedPos == position

        holder.itemView.gif_item.setOnClickListener(View.OnClickListener {
            selectedPos = position

            if (holder.itemView.gif_item.isSelected && selectedPos == position) {
                selectedPos = -1
            }

            Log.e("CompletionGifAdapter", "${selectedPos}")
            notifyDataSetChanged()
        })

//        holder.bind(gifDatas[position], position)
    }

    class ViewHolder(private val view: View, private val mContext: Context) : RecyclerView.ViewHolder(view) {

        fun bind(gifData: GifDrawable, position: Int) {

        }
    }

    override fun getItemCount(): Int = gifDatas.size
}