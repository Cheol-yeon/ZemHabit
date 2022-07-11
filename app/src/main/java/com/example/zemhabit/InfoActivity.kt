package com.example.zemhabit

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zemhabit.Adapter.CompletionGifAdapter
import com.example.zemhabit.Data.HabitSettingData
import com.example.zemhabit.Fragment.SetHabitFragment
import com.example.zemhabit.Room.RoomAccess
import com.example.zemhabit.databinding.ActivityInfoBinding
import kotlinx.android.synthetic.main.habit_item.view.*
import pl.droidsonroids.gif.GifDrawable


class InfoActivity : AppCompatActivity() {

    private var mBinding: ActivityInfoBinding? = null
    private val binding get() = mBinding!!

    var habitData: HabitSettingData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item_no = intent.getIntExtra("item_no", 0)
        val type = intent.getStringExtra("type")
        Log.e("InfoActivity", "${item_no}")

        setData(item_no, type!!)

        binding.ivBackIcon.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        binding.btnHabitRefuse.setOnClickListener(View.OnClickListener {
            Log.e("InfoActivity()", "Resfuse Habit")
        })

        binding.btnHabitAdmit.setOnClickListener(View.OnClickListener {
            val layout = layoutInflater.inflate(R.layout.dialog_sheet_admit, null)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout)
            }
            val dialog = build.create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.show()

            if (type == "대기" || type == "수정대기") {
                layout.findViewById<TextView>(R.id.tv_admitText).text = "'${habitData!!.habit_title}' 습관을 승인할까요?"

                layout.findViewById<Button>(R.id.btn_cancel).setOnClickListener(View.OnClickListener {
                    dialog.cancel()
                })
                layout.findViewById<Button>(R.id.btn_submit).setOnClickListener(View.OnClickListener {
                    RoomAccess(applicationContext).updateTypeToProceedById(item_no)
                    dialog.cancel()
                    val intent = Intent()
                    intent.putExtra("key2", "Send Data2")
                    setResult(RESULT_OK, intent)
                    finish()
                })
            } else if (type == "진행중") {
                Log.e("InfoActivity", "완료요청")
                layout.findViewById<TextView>(R.id.tv_admitText).text = "'${habitData!!.habit_title}' 습관을 완료 요청할까요?"

                layout.findViewById<Button>(R.id.btn_cancel).setOnClickListener(View.OnClickListener {
                    dialog.cancel()
                })
                layout.findViewById<Button>(R.id.btn_submit).setOnClickListener(View.OnClickListener {
                    RoomAccess(applicationContext).updateTypeToCompletionById(item_no)
                    dialog.cancel()
                    val intent = Intent()
                    intent.putExtra("key2", "${type}")
                    setResult(RESULT_OK, intent)
                    finish()
                })
            }
        })

        binding.btnHabitCancel.setOnClickListener(View.OnClickListener {
            // 승인하기
            if (type == "완료요청") {
                Log.e("************","**************")

                val layout = layoutInflater.inflate(R.layout.dialog_sheet_completion, null)
                val build = AlertDialog.Builder(it.context).apply {
                    setView(layout)
                }
                val dialog = build.create()
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                dialog.show()

                val gifFromResource = GifDrawable(applicationContext.resources, R.drawable.boogi)
                val gifFromResource2 = GifDrawable(applicationContext.resources, R.drawable.boogi2)
                val gifDatas: ArrayList<GifDrawable> = arrayListOf(
                    gifFromResource, gifFromResource2,
                    gifFromResource, gifFromResource2,
                    gifFromResource, gifFromResource2,
                    gifFromResource, gifFromResource2,
                    gifFromResource, gifFromResource2)

                layout.findViewById<AppCompatTextView>(R.id.tv_noticeContent2).text = "${habitData!!.zemcon}잼콘을 지급할까요?"
                layout.findViewById<AppCompatTextView>(R.id.tv_completionContent).text = "\u0027${habitData!!.habit_title}\u0027"
                val layoutManager = GridLayoutManager(applicationContext, 2)
                layout.findViewById<RecyclerView>(R.id.rv_compliment).layoutManager = layoutManager
                val adapter = CompletionGifAdapter(applicationContext,gifDatas)
                layout.findViewById<RecyclerView>(R.id.rv_compliment).adapter = adapter

                layout.findViewById<AppCompatButton>(R.id.btn_cancel).setOnClickListener(View.OnClickListener {
                    dialog.cancel()
                })

                layout.findViewById<AppCompatButton>(R.id.btn_submit).setOnClickListener(View.OnClickListener {
                    Log.e("CompletionDialog", "Selected Adapter Item No = ${adapter.selectedPos}")
                    if (adapter.selectedPos == -1) {
                        RoomAccess(applicationContext).updateTypeToCompleteById(habitData!!.habit_no)
                    } else if (adapter.selectedPos in 0 .. 9) {
                        RoomAccess(applicationContext).R_updateTypeToCompleteById(habitData!!.habit_no)
                    }
                    dialog.cancel()
                    val intent = Intent()
                    intent.putExtra("key2", "${type}")
                    setResult(RESULT_OK, intent)
                    finish()
                })
            } else {
                val layout = layoutInflater.inflate(R.layout.dialog_sheet_notice3, null)
                val build = AlertDialog.Builder(it.context).apply {
                    setView(layout)
                }
                val dialog = build.create()
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                dialog.show()

                layout.findViewById<Button>(R.id.btn_cancel).setOnClickListener(View.OnClickListener {
                    dialog.cancel()
                })
                layout.findViewById<Button>(R.id.btn_submit).setOnClickListener(View.OnClickListener {
                    RoomAccess(applicationContext).deleteById(item_no)
                    dialog.cancel()
                    finish()
                })
            }
        })

        binding.ivEdit.setOnClickListener(View.OnClickListener {
            val setHabitFragment = SetHabitFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.flSettingFrame.id, setHabitFragment.apply {
                arguments = Bundle().apply {
                    putInt("habit_no", item_no)
                    putString("category", habitData!!.category)
                }
            }).addToBackStack(null).commit()
        })

        binding.ivGarbage.setOnClickListener(View.OnClickListener {
            val layout = layoutInflater.inflate(R.layout.dialog_sheet_notice6, null)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout)
            }
            val dialog = build.create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.show()

            layout.findViewById<AppCompatTextView>(R.id.tv_noticeContent1).text = "${habitData!!.habit_title}"

            layout.findViewById<AppCompatButton>(R.id.btn_cancel).setOnClickListener(View.OnClickListener {
                dialog.cancel()
            })
            layout.findViewById<AppCompatButton>(R.id.btn_submit).setOnClickListener(View.OnClickListener {
                RoomAccess(applicationContext).deleteById(item_no)
                dialog.cancel()
                finish()
            })
        })
    }

    private fun setData(item_no: Int, type: String) {
        Log.e("InfoActivity", "${RoomAccess(applicationContext).habitGetById(item_no)}")
        habitData = RoomAccess(applicationContext).habitGetById(item_no)
        when (habitData!!.category) {
            "건강한 몸" -> binding.ivCategoryIcon.setImageResource(R.drawable.health_icon)
            "꾸준한 공부" -> binding.ivCategoryIcon.setImageResource(R.drawable.study_icon)
            "긍정적인 언어 사용" -> binding.ivCategoryIcon.setImageResource(R.drawable.message_icon)
            "스스로 시간 관리" -> binding.ivCategoryIcon.setImageResource(R.drawable.timer_icon)
            "올바른 스마트폰 사용" -> binding.ivCategoryIcon.setImageResource(R.drawable.phone_icon)
            "부지런한 집안 생활" -> binding.ivCategoryIcon.setImageResource(R.drawable.house_icon)
            "학교 생활" -> binding.ivCategoryIcon.setImageResource(R.drawable.bagpack_icon)
            "기타" -> binding.ivCategoryIcon.setImageResource(R.drawable.etc_icon)
        }
        when (type) {
            "대기" -> {
                binding.tvProgress.text = "등록 대기"
                binding.tvProgress.setTextColor(ContextCompat.getColor(applicationContext, R.color.waitingProgress))
                binding.tvProgress.background.setTint(ContextCompat.getColor(applicationContext, R.color.waitingProgressBackground))
                binding.ivEdit.visibility = View.GONE
                binding.ivGarbage.visibility = View.GONE
            }
            "진행중" -> {
                binding.tvProgress.text = "진행중"
                binding.tvProgress.setTextColor(ContextCompat.getColor(applicationContext, R.color.proceedProgress))
                binding.tvProgress.background.setTint(ContextCompat.getColor(applicationContext, R.color.proceedProgressBackground))
                binding.btnHabitCancel.visibility = View.GONE
                binding.btnHabitAdmit.text = "완료 요청"
                binding.tvCategoryTitle.maxWidth = resources.getDimensionPixelSize(R.dimen.max_width)
            }
            "수정대기" -> {
                binding.tvProgress.text = "수정 대기"
                binding.tvProgress.setTextColor(ContextCompat.getColor(applicationContext, R.color.editProgress))
                binding.tvProgress.background.setTint(ContextCompat.getColor(applicationContext, R.color.editProgressBackground))
                binding.ivEdit.visibility = View.GONE
                binding.ivGarbage.visibility = View.GONE
            }
            "완료요청" -> {
                binding.tvProgress.text = "완료 요청"
                binding.tvProgress.setTextColor(ContextCompat.getColor(applicationContext, R.color.completionTextColor))
                binding.tvProgress.background.setTint(ContextCompat.getColor(applicationContext, R.color.disableButton))
                binding.ivEdit.visibility = View.GONE
                binding.ivGarbage.visibility = View.GONE
                binding.btnHabitRefuse.visibility = View.GONE
                binding.btnHabitAdmit.visibility = View.GONE
                binding.btnHabitCancel.text = "승인하기"
            }
            "완료" -> {
                binding.tvProgress.visibility = View.GONE
                binding.ivEdit.visibility = View.GONE
                binding.ivGarbage.visibility = View.GONE
                binding.btnHabitRefuse.visibility = View.GONE
                binding.btnHabitAdmit.visibility = View.GONE
                binding.btnHabitCancel.visibility = View.GONE
            }
            "칭찬완료" -> {
                binding.tvProgress.visibility = View.GONE
                binding.ivEdit.visibility = View.GONE
                binding.ivGarbage.visibility = View.GONE
                binding.btnHabitRefuse.visibility = View.GONE
                binding.btnHabitAdmit.visibility = View.GONE
                binding.btnHabitCancel.visibility = View.GONE
            }
        }
        binding.tvCategoryTitle.text = habitData!!.category
        binding.tvHabitName.text = habitData!!.habit_title
        binding.tvHabitPeriod.text = "${habitData!!.start_date} ~ ${habitData!!.end_date}"
        binding.tvHabitDayofWeek.text = habitData!!.day_of_week
        binding.tvHabitAlarm.text = habitData!!.alarm
        binding.tvHabitZemcon.text = habitData!!.zemcon
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}