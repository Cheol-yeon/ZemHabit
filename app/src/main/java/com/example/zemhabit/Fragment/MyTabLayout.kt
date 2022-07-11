package com.example.zemhabit.Fragment

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.example.zemhabit.R
import com.google.android.material.tabs.TabLayout

class MyTabLayout : TabLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

//    fun MyTabLayout(context: Context) {
//        super(context)
//    }
//
//    fun MyTabLayout(context: Context, attrs: AttributeSet?) {
//        super(context, attrs)
//    }
//
//    fun MyTabLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
//        super(context, attrs, defStyleAttr)
//        setTabRippleColorResource(R.color.transparent)
//        setSelectedTabIndicator(R.color.transparent)
//    }

    override fun addTab(tab: Tab, position: Int, setSelected: Boolean) {
        super.addTab(tab, position, setSelected)
        val customView: View =
            LayoutInflater.from(context).inflate(R.layout.layout_tab_layout_my, tab.view, false)
        val tabTextView = customView.findViewById<TextView>(R.id.tv_title)
        val tabText = if (tab.text == null) null else tab.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(tabText)) {
            tabTextView.text = tabText
        }
        tab.view.setPadding(0, 0, 0, 0)
        Log.e("TAB", "getTabCount() = $tabCount")
        Log.e("TAB", "getChildCount() = $childCount")
        //        ConstraintLayout layout = customView.findViewById(R.id.cl_root);
        val layout: AppCompatImageView = customView.findViewById(R.id.background)
        if (0 == position) {
            layout.background = ContextCompat.getDrawable(context, R.drawable.selector_tab_left)
        } else if (2 == position) {
            layout.background = ContextCompat.getDrawable(context, R.drawable.selector_tab_right)
        } else {
            layout.background = ContextCompat.getDrawable(context, R.drawable.selector_tab_center)
        }
        tab.customView = customView
    }

    fun setNumber(position: Int, number: String?) {
        val tab = getTabAt(position)
        if (tab != null) {
            val customView = tab.customView
            if (customView != null) {
                val tv = customView.findViewById<TextView>(R.id.tv_number)
                if (tv != null) {
                    tv.text = number
                }
            }
        }
    }
}