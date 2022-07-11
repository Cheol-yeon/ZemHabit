package com.example.zemhabit.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.tv.TvView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zemhabit.Data.HabitSettingData
import com.example.zemhabit.Fragment.CompletionFragment
import com.example.zemhabit.Fragment.HabitFragment
import com.example.zemhabit.Fragment.ProceedFragment
import com.example.zemhabit.Fragment.WaitingFragment
import com.example.zemhabit.InfoActivity
import com.example.zemhabit.MainActivity
import com.example.zemhabit.MakeHabitActivity
import com.example.zemhabit.R
import com.example.zemhabit.Room.RoomAccess
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.habit_item.view.*
import pl.droidsonroids.gif.GifDrawable
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class HabitAdapter(
    private var context: Context,
    private val habitDatas: ArrayList<HabitSettingData>,
    private var activityresultlauncer: ActivityResultLauncher<Intent>
) :
    RecyclerView.Adapter<HabitAdapter.ViewHolder>() {

    init {
        Log.e("Adapter", "In Habit Adapter")
//        habitDatas.forEach {
//            Log.e("Adapter", "${habitDatas}")
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = this.context
        val view = LayoutInflater.from(context).inflate(R.layout.habit_item, parent, false)
        val activityresultlauncer = this.activityresultlauncer
        return ViewHolder(view, context, activityresultlauncer)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habitDatas[position])
    }

    class ViewHolder(
        private val view: View,
        private val mContext: Context,
        private val activityresultlauncer: ActivityResultLauncher<Intent>
    ) : RecyclerView.ViewHolder(view) {

        val current: LocalDate = LocalDate.now()
        var formatter = DateTimeFormatter.ofPattern("yy.MM.dd")
        val habitFragment = HabitFragment()
        fun bind(habitSettingData: HabitSettingData) {

//            Log.e("HabitAdapter", "${habitSettingData}")

            when(habitSettingData.type) {
                "대기" -> {
                    view.tv_progress.text = "등록 대기"
                    view.tv_progress.setTextColor(ContextCompat.getColor(mContext, R.color.waitingProgress))
                    view.tv_progress.background.setTint(ContextCompat.getColor(mContext, R.color.waitingProgressBackground))
                    view.tv_content.text = habitSettingData.habit_title
                    view.tv_day.text = habitSettingData.day_of_week
                    view.tv_date.visibility = View.INVISIBLE
                    view.btn_recognize.visibility = View.INVISIBLE
                }
                "진행중" -> {
                    view.tv_progress.text = "진행중"
                    view.tv_progress.setTextColor(ContextCompat.getColor(mContext, R.color.proceedProgress))
                    view.tv_progress.background.setTint(ContextCompat.getColor(mContext, R.color.proceedProgressBackground))
                    view.tv_content.text = habitSettingData.habit_title
                    view.tv_day.text = habitSettingData.day_of_week
                    view.tv_date.text = current.format(formatter)
                }
                "수정대기" -> {
                    view.tv_progress.text = "수정 대기"
                    view.tv_progress.setTextColor(ContextCompat.getColor(mContext, R.color.editProgress))
                    view.tv_progress.background.setTint(ContextCompat.getColor(mContext, R.color.editProgressBackground))
                    view.tv_content.text = habitSettingData.habit_title
                    view.tv_day.text = habitSettingData.day_of_week
                    view.tv_date.visibility = View.INVISIBLE
                    view.btn_recognize.visibility = View.INVISIBLE
                }
                "완료요청" -> {
                    view.tv_progress.text = "완료 요청"
                    view.tv_progress.setTextColor(ContextCompat.getColor(mContext, R.color.completionTextColor))
                    view.tv_progress.background.setTint(ContextCompat.getColor(mContext, R.color.disableButton))
                    view.btn_recognize.setBackgroundResource(R.drawable.button_shape_completion)
                    view.btn_recognize.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                    view.btn_recognize.text = "승인하기"
                    view.cl_habitItem.setBackgroundResource(R.drawable.item_shape_completion)
                    view.tv_content.text = habitSettingData.habit_title
                    view.tv_day.text = habitSettingData.day_of_week
                    view.tv_date.text = current.format(formatter)
                }
                "완료" -> {
                    view.tv_progress.visibility = View.GONE
                    view.btn_recognize.text = "칭찬하기"
                    view.tv_content.text = habitSettingData.habit_title
                    view.tv_day.text = habitSettingData.day_of_week
                    view.tv_date.text = current.format(formatter)
                }
                "칭찬완료" -> {
                    view.tv_progress.visibility = View.GONE
                    view.btn_recognize.text = "칭찬하기"
                    view.btn_recognize.isEnabled = false
                    view.btn_recognize.setTextColor(ContextCompat.getColor(mContext, R.color.disableButton))
                    view.tv_content.text = habitSettingData.habit_title
                    view.tv_day.text = habitSettingData.day_of_week
                    view.tv_date.text = current.format(formatter)
                }
            }

//            Log.e("ddd", "${habitSettingData.category.equals("건강한 몸")}")
            when (habitSettingData.category) {
                "건강한 몸" -> view.iv_habitIcon.setImageResource(R.drawable.health_icon)
                "꾸준한 공부" -> view.iv_habitIcon.setImageResource(R.drawable.study_icon)
                "긍정적인 언어 사용" -> view.iv_habitIcon.setImageResource(R.drawable.message_icon)
                "스스로 시간 관리" -> view.iv_habitIcon.setImageResource(R.drawable.timer_icon)
                "올바른 스마트폰 사용" -> view.iv_habitIcon.setImageResource(R.drawable.phone_icon)
                "부지런한 집안 생활" -> view.iv_habitIcon.setImageResource(R.drawable.house_icon)
                "똑똑한 학교 생활" -> view.iv_habitIcon.setImageResource(R.drawable.bagpack_icon)
                "기타" -> view.iv_habitIcon.setImageResource(R.drawable.etc_icon)
            }

            view.cl_habitItem.setOnClickListener(View.OnClickListener {
                val appCompatActivity = mContext as MainActivity

                val intent = Intent(appCompatActivity, InfoActivity::class.java)
                intent.putExtra("item_no", habitSettingData.habit_no)
                intent.putExtra("type", habitSettingData.type)
                activityresultlauncer.launch(intent)
            })

            view.btn_recognize.setOnClickListener(View.OnClickListener {
                Log.e("HabitAdapter", "btn_recognize Clicked")
                if (view.btn_recognize.text == "승인하기") {
                    val layout = LayoutInflater.from(mContext).inflate(R.layout.dialog_sheet_completion, null)
                    val build = AlertDialog.Builder(it.context).apply {
                        setView(layout)
                    }
                    val dialog = build.create()
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                    dialog.show()

                    val gifFromResource = GifDrawable(mContext.resources, R.drawable.boogi)
                    val gifFromResource2 = GifDrawable(mContext.resources, R.drawable.boogi2)
                    val gifDatas: ArrayList<GifDrawable> = arrayListOf(
                        gifFromResource, gifFromResource2,
                        gifFromResource, gifFromResource2,
                        gifFromResource, gifFromResource2,
                        gifFromResource, gifFromResource2,
                        gifFromResource, gifFromResource2)

                    layout.findViewById<AppCompatTextView>(R.id.tv_noticeContent2).text = "${habitSettingData.zemcon}잼콘을 지급할까요?"
                    layout.findViewById<AppCompatTextView>(R.id.tv_completionContent).text = "\u0027${habitSettingData.habit_title}\u0027"
                    val layoutManager = GridLayoutManager(mContext, 2)
                    layout.findViewById<RecyclerView>(R.id.rv_compliment).layoutManager = layoutManager
                    val adapter = CompletionGifAdapter(mContext,gifDatas)
                    layout.findViewById<RecyclerView>(R.id.rv_compliment).adapter = adapter

                    layout.findViewById<AppCompatButton>(R.id.btn_cancel).setOnClickListener(View.OnClickListener {
                        dialog.cancel()
                    })

                    layout.findViewById<AppCompatButton>(R.id.btn_submit).setOnClickListener(View.OnClickListener {
                        Log.e("CompletionDialog", "Selected Adapter Item No = ${adapter.selectedPos}")
                        if (adapter.selectedPos == -1) {
                            RoomAccess(mContext).updateTypeToCompleteById(habitSettingData.habit_no)
                        } else if (adapter.selectedPos in 0 .. 9) {
                            RoomAccess(mContext).R_updateTypeToCompleteById(habitSettingData.habit_no)
                        }

                        mContext as MainActivity
//                        habitFragment.refreshFragment(CompletionFragment(), mContext.supportFragmentManager)
                        mContext.supportFragmentManager.fragments[0].onResume()
                        mContext.supportFragmentManager.fragments[0].view?.findViewById<TabLayout>(R.id.tl_habit)?.getTabAt(1)?.select()

                        dialog.cancel()
                    })

                }
            })
        }

    }

    override fun getItemCount(): Int = habitDatas.size


}