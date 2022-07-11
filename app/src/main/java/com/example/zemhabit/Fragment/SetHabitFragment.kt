package com.example.zemhabit.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.example.zemhabit.Data.HabitSettingData
import com.example.zemhabit.DayPicker.MaterialDayPicker
import com.example.zemhabit.R
import com.example.zemhabit.Room.RoomAccess
import com.example.zemhabit.databinding.FragmentSetHabitBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetHabitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetHabitFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentSetHabitBinding? = null
    private val binding get() = _binding!!

    var listItem: String? = null
    var habitTitle: String? = null
    var imgIcon: Int? = null
    var category: String? = null
    var habit_no: Int? = null

    val current: LocalDate = LocalDate.now()
    var formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    var startDateString: String? = null
    var startDate: LocalDate? = null
    var endDateString: String? = null
    var endDate: LocalDate? = null

    var selectedDays: List<MaterialDayPicker.Weekday>? = listOf()
    var settedData: HabitSettingData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        listItem = arguments?.getString("listItem")
        habitTitle = arguments?.getString("habitTitle")
        imgIcon = arguments?.getInt("imgIcon")
        habit_no = arguments?.getInt("habit_no")
        category = arguments?.getString("category")
//        icon = arguments?.getInt("icon")
        Log.e("SetHabitFragment", "listItem = ${listItem}, habitTitle = ${habitTitle}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (habit_no != 0) {
            Log.e("SetHabitFragment()", "edit habit ${habit_no}")
            binding.clSettingParent.setPadding(0)

            settedData = RoomAccess(requireActivity()).habitGetById(habit_no!!)

            binding.tvSettingTitle.text = category

            when (category) {
                "건강한 몸" -> binding.ivSettingIcon.setImageResource(R.drawable.health_icon)
                "꾸준한 공부" -> binding.ivSettingIcon.setImageResource(R.drawable.study_icon)
                "긍정적인 언어사용" -> binding.ivSettingIcon.setImageResource(R.drawable.message_icon)
                "스스로 시간 관리" -> binding.ivSettingIcon.setImageResource(R.drawable.timer_icon)
                "올바른 스마트폰 사용" -> binding.ivSettingIcon.setImageResource(R.drawable.phone_icon)
                "부지런한 집안 생활" -> binding.ivSettingIcon.setImageResource(R.drawable.house_icon)
                "학교 생활" -> binding.ivSettingIcon.setImageResource(R.drawable.bagpack_icon)
                "기타" -> binding.ivSettingIcon.setImageResource(R.drawable.etc_icon)
            }

            binding.tvHabitName2.text = settedData!!.habit_title
            binding.etHabitName.visibility = View.INVISIBLE
            binding.tvStartDate.text = settedData!!.start_date
            binding.tvEndDate.text = settedData!!.end_date

            val endDate = LocalDate.parse(settedData!!.end_date, formatter)
            val startDate = LocalDate.parse(settedData!!.start_date, formatter)

            val DateGap =
                (endDate.dayOfYear - startDate.dayOfYear) * (endDate.dayOfYear - startDate.dayOfYear)
            when (DateGap) {
                9801 -> binding.rb100days.isChecked = true
                841 -> binding.rb30days.isChecked = true
                169 -> binding.rb14days.isChecked = true
                36 -> binding.rb7days.isChecked = true
            }

            binding.tvSetDateText.text = settedData!!.day_of_week
            binding.tvAlarmText.text = settedData!!.alarm
            binding.tvGiveZemconCount.text = settedData!!.zemcon
            binding.btnHabitSubmit.text = "저장"
            binding.btnHabitSubmit.isEnabled = false

            binding.btnHabitSubmit.setOnClickListener(View.OnClickListener {
                val layout = layoutInflater.inflate(R.layout.dialog_sheet_notice5, null)
                val build = AlertDialog.Builder(it.context).apply {
                    setView(layout)
                }
                val dialog = build.create()
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                dialog.show()

                layout.findViewById<AppCompatButton>(R.id.btn_cancel).setOnClickListener(View.OnClickListener {
                    dialog.cancel()
                })
                layout.findViewById<AppCompatButton>(R.id.btn_submit).setOnClickListener(View.OnClickListener {
                    val getStartDate = binding.tvStartDate.text.toString()
                    val getEndDate = binding.tvEndDate.text.toString()
                    val getDayOfWeek = binding.tvSetDateText.text.toString()
                    val getAlarm = binding.tvAlarmText.text.toString()
                    val getZemcon = binding.tvGiveZemconCount.text.toString()
                    Log.e("DDDDDDDDDDDDD", "${habit_no}, ${getStartDate}, ${getEndDate}, ${getDayOfWeek}, ${getAlarm}, ${getZemcon}")

                    dialog.cancel()
                })
            })

        } else if (habit_no == 0) {
            // 타이틀 설정
            binding.tvSettingTitle.text = habitTitle
            binding.ivSettingIcon.setImageResource(imgIcon!!)
        }

        // 습관 이름
        if (listItem != null && listItem != "직접 입력") {
            binding.etHabitName.setText(listItem)
        } else {
            binding.etHabitName.hint = "어떤 습관인가요?"
            binding.etHabitName.requestFocus()
        }

        // 기간 설정
        Log.e("SetHabitFragment", "Current: ${current}")
        val startFormatted = current?.format(formatter)
        val afterFormatted = current?.plusDays(29)?.format(formatter)
        if(habit_no == 0) {
            binding.tvStartDate.text = startFormatted
            binding.tvEndDate.text = afterFormatted
        }

        binding.tvStartDate.setOnClickListener(View.OnClickListener {
            val layout = layoutInflater.inflate(R.layout.dialog_sheet_setdate, null)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout)
            }
            val dialog = build.create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.show()

            val np_year = layout.findViewById<NumberPicker>(R.id.np_year)
            val np_month = layout.findViewById<NumberPicker>(R.id.np_month)
            val np_date = layout.findViewById<NumberPicker>(R.id.np_date)

            startDateString = binding.tvStartDate.text.toString()
            startDate = LocalDate.parse(startDateString, formatter)

            endDateString = binding.tvEndDate.text.toString()
            endDate = LocalDate.parse(endDateString, formatter)

            np_year.minValue = current.year
            np_year.maxValue = current.year + 2
            np_year.value = startDate!!.year

            np_month.minValue = 1
            np_month.maxValue = 12
            np_month.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })
            np_month.value = startDate!!.monthValue

            np_date.minValue = 1
            np_date.maxValue = getDaysInMonth(current!!.month.value, current!!.year)
            np_date.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })
            np_date.value = startDate!!.dayOfMonth

            np_month.setOnValueChangedListener { numberPicker, oldVal, newVal ->
                np_date.maxValue = getDaysInMonth(newVal, current.year)
            }

            layout.findViewById<AppCompatButton>(R.id.btn_submit).setOnClickListener {
                val mDateStart = LocalDate.of(np_year.value, np_month.value, np_date.value)

                if (habit_no != 0 && !settedData?.start_date!!.equals(mDateStart)) {
                    binding.btnHabitSubmit.isEnabled = true
                } else if (habit_no != 0 && settedData?.start_date!!.equals(mDateStart)) {
                    binding.btnHabitSubmit.isEnabled = false
                }

                Log.e("mDateStart", "${mDateStart}")

                binding.radioGroup.clearCheck()
                if (LocalDate.now() > mDateStart) {
                    Log.e("NumberPicker Dailog Submit", "Case1")

                    val layout2 = layoutInflater.inflate(R.layout.dialog_sheet_notice, null)
                    val build2 = AlertDialog.Builder(it.context).apply {
                        setView(layout2)
                    }
                    val dialog2 = build2.create()
                    dialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                    dialog2.show()

                    dialog.cancel()
                    binding.tvStartDate.text = startFormatted

                    layout2.findViewById<AppCompatButton>(R.id.btn_submit2)
                        .setOnClickListener(View.OnClickListener {
                            dialog2.cancel()
                        })

                } else if (mDateStart.minusDays(364) > current) {
                    Log.e("NumberPicker Dailog Submit", "Case2")
                    Toast.makeText(
                        requireContext(),
                        "습관 시작일은 오늘 기준으로 최대 365일까지만 가능합니다.",
                        Toast.LENGTH_LONG
                    ).show()
                    dialog.cancel()
                } else if (mDateStart > endDate) {
                    Log.e("NumberPicker Dailog Submit", "Case3")
                    binding.tvStartDate.text = mDateStart.format(formatter)
                    binding.tvEndDate.text = binding.tvStartDate.text
                    dialog.cancel()
                } else {
                    Log.e("NumberPicker Dailog Submit", "Case4")
                    binding.tvStartDate.text = mDateStart.format(formatter)
                    val startDateGap =
                        (endDate!!.dayOfYear - mDateStart!!.dayOfYear) * (endDate!!.dayOfYear - mDateStart!!.dayOfYear)
                    Log.e("endDateGap", "${startDateGap}")
                    when (startDateGap) {
                        9801 -> binding.rb100days.isChecked = true
                        841 -> binding.rb30days.isChecked = true
                        169 -> binding.rb14days.isChecked = true
                        36 -> binding.rb7days.isChecked = true
                    }
                    dialog.cancel()
                }
            }

            layout.findViewById<AppCompatButton>(R.id.btn_cancel).setOnClickListener {
                dialog.cancel()
            }
        })

        binding.tvEndDate.setOnClickListener(View.OnClickListener { it ->
            val layout = layoutInflater.inflate(R.layout.dialog_sheet_setdate, null)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout)
            }
            val dialog = build.create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.show()

            Log.e("tvEndDate", "StartDate = ${startDate}, EndDate = ${endDate}")

            val np_year = layout.findViewById<NumberPicker>(R.id.np_year)
            val np_month = layout.findViewById<NumberPicker>(R.id.np_month)
            val np_date = layout.findViewById<NumberPicker>(R.id.np_date)

            startDateString = binding.tvStartDate.text.toString()
            startDate = LocalDate.parse(startDateString, formatter)

            endDateString = binding.tvEndDate.text.toString()
            endDate = LocalDate.parse(endDateString, formatter)

            np_year.minValue = current.year
            np_year.maxValue = current.year + 2
            np_year.value = endDate!!.year

            np_month.minValue = 1
            np_month.maxValue = 12
            np_month.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })
            np_month.value = endDate!!.monthValue
//            np_month.value = month

            np_date.minValue = 1
            np_date.maxValue = getDaysInMonth(current!!.month.value, current!!.year)
            np_date.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })
            np_date.value = endDate!!.dayOfMonth

            np_month.setOnValueChangedListener { numberPicker, oldVal, newVal ->
                np_date.maxValue = getDaysInMonth(newVal, current.year)
            }

            layout.findViewById<AppCompatButton>(R.id.btn_submit).setOnClickListener {
                val mDateEnd = LocalDate.of(np_year.value, np_month.value, np_date.value)

                if (habit_no != 0 && !settedData?.end_date!!.equals(mDateEnd)) {
                    binding.btnHabitSubmit.isEnabled = true
                } else if (habit_no != 0 && settedData?.end_date!!.equals(mDateEnd)) {
                    binding.btnHabitSubmit.isEnabled = false
                }

                binding.radioGroup.clearCheck()
                if (LocalDate.now() > mDateEnd || startDate!! > mDateEnd) {
                    Log.e(
                        "NumberPicker Dailog",
                        "오늘 이전날짜 선택, StartDate = ${startDate}, EndDate = ${mDateEnd}"
                    )

                    val layout2 = layoutInflater.inflate(
                        com.example.zemhabit.R.layout.dialog_sheet_notice,
                        null
                    )
                    val build2 = AlertDialog.Builder(it.context).apply {
                        setView(layout2)
                    }
                    val dialog2 = build2.create()
                    dialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                    dialog2.show()

                    dialog.cancel()
                    binding.tvEndDate.text = binding.tvStartDate.text

                    layout2.findViewById<AppCompatButton>(R.id.btn_submit2)
                        .setOnClickListener(View.OnClickListener {
                            dialog2.cancel()
                        })

                } else if (mDateEnd.minusDays(179) > startDate) {
                    val layout3 = layoutInflater.inflate(
                        com.example.zemhabit.R.layout.dialog_sheet_notice2,
                        null
                    )
                    val build3 = AlertDialog.Builder(it.context).apply {
                        setView(layout3)
                    }
                    val dialog3 = build3.create()
                    dialog3.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                    dialog3.show()

                    dialog.cancel()
                    binding.tvEndDate.text = startDate!!.plusDays(179).format(formatter)
                    layout3.findViewById<AppCompatButton>(R.id.btn_submit2)
                        .setOnClickListener(View.OnClickListener {
                            dialog3.cancel()
                        })
                } else {
                    binding.tvEndDate.text = "${mDateEnd.format(formatter)}"
                    val endDateGap =
                        (mDateEnd.dayOfYear - startDate!!.dayOfYear) * (mDateEnd.dayOfYear - startDate!!.dayOfYear)
                    Log.e("endDateGap", "${endDateGap}")
                    when (endDateGap) {
                        9801 -> binding.rb100days.isChecked = true
                        841 -> binding.rb30days.isChecked = true
                        169 -> binding.rb14days.isChecked = true
                        36 -> binding.rb7days.isChecked = true
                    }
                    dialog.cancel()
                }

            }

            layout.findViewById<AppCompatButton>(R.id.btn_cancel).setOnClickListener {
                dialog.cancel()
            }
        })

        binding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            startDateString = binding.tvStartDate.text.toString()
            startDate = LocalDate.parse(startDateString, formatter)
            when (checkedId) {
                binding.rb30days.id -> {
                    val afterFormatted = startDate?.plusDays(29)?.format(formatter)
                    binding.tvEndDate.text = afterFormatted
                }
                binding.rb7days.id -> {
                    val afterFormatted = startDate?.plusDays(6)?.format(formatter)
                    binding.tvEndDate.text = afterFormatted
                }
                binding.rb14days.id -> {
                    val afterFormatted = startDate?.plusDays(13)?.format(formatter)
                    binding.tvEndDate.text = afterFormatted
                }
                binding.rb100days.id -> {
                    val afterFormatted = startDate?.plusDays(99)?.format(formatter)
                    binding.tvEndDate.text = afterFormatted
                }
            }

            if (habit_no != 0 && !settedData?.end_date!!.equals(binding.tvEndDate.text.toString())) {
                binding.btnHabitSubmit.isEnabled = true
            } else if (habit_no != 0 && settedData?.end_date!!.equals(binding.tvEndDate.text.toString())) {
                binding.btnHabitSubmit.isEnabled = false
            }
        })

        //요일 설정
        binding.tvSetDateText.setOnClickListener(View.OnClickListener {
            val layout = layoutInflater.inflate(R.layout.dialog_sheet_doy, null)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout)
            }
            val dialog = build.create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.show()

            Log.e("Selected Days", "${selectedDays}")
            layout.findViewById<ToggleButton>(R.id.toggle_all).isChecked = selectedDays?.size == 0

            val doy = layout.findViewById<MaterialDayPicker>(R.id.dp_doy)

            val daysToSelect = selectedDays
            doy.setSelectedDays(daysToSelect!!)
            doy.locale = Locale.KOREAN
            doy.firstDayOfWeek = MaterialDayPicker.Weekday.MONDAY

//            layout.findViewById<ToggleButton>(R.id.toggle_all).setOnClickListener(View.OnClickListener {
//                Log.e("DayPicker", "매일 선택")
//                doy.clearSelection()
//            })

            layout.findViewById<ToggleButton>(R.id.toggle_all)
                .setOnClickListener(View.OnClickListener {
                    Log.e("setOnClickListener", "ToggleButton Clicked")
                    doy.clearSelection()
                    layout.findViewById<ToggleButton>(R.id.toggle_all).isChecked = true
                })

            doy.setDayPressedListener { weekday: MaterialDayPicker.Weekday, isSelected: Boolean ->
                Log.e("setDayPressedListener", "${weekday} clicked")
                layout.findViewById<ToggleButton>(R.id.toggle_all).isChecked = false
            }

            layout.findViewById<AppCompatButton>(R.id.btn_submit)
                .setOnClickListener(View.OnClickListener {
                    selectedDays = doy.selectedDays
                    Log.e("DayOfWeek", "${selectedDays}")
                    var text: String? = ""

                    if (doy.selectedDays.size == 0 && !layout.findViewById<ToggleButton>(R.id.toggle_all).isChecked) {
                        Toast.makeText(requireContext(), "최소 하나의 요일을 선택하세요", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        if (doy.selectedDays.size in 1..2) {
                            for (i in 0 until doy.selectedDays.size) {
                                when (doy.selectedDays.get(i)) {
                                    MaterialDayPicker.Weekday.MONDAY -> text += "월요일"
                                    MaterialDayPicker.Weekday.TUESDAY -> text += "화요일"
                                    MaterialDayPicker.Weekday.WEDNESDAY -> text += "수요일"
                                    MaterialDayPicker.Weekday.THURSDAY -> text += "목요일"
                                    MaterialDayPicker.Weekday.FRIDAY -> text += "금요일"
                                    MaterialDayPicker.Weekday.SATURDAY -> text += "토요일"
                                    MaterialDayPicker.Weekday.SUNDAY -> text += "일요일"
                                }
                                if (i == doy.selectedDays.size - 1) break;
                                text += ", "
                            }
                        } else if (doy.selectedDays.size > 2 && doy.selectedDays.size < 7) {
                            for (i in 0 until doy.selectedDays.size) {
                                when (doy.selectedDays.get(i)) {
                                    MaterialDayPicker.Weekday.MONDAY -> text += "월"
                                    MaterialDayPicker.Weekday.TUESDAY -> text += "화"
                                    MaterialDayPicker.Weekday.WEDNESDAY -> text += "수"
                                    MaterialDayPicker.Weekday.THURSDAY -> text += "목"
                                    MaterialDayPicker.Weekday.FRIDAY -> text += "금"
                                    MaterialDayPicker.Weekday.SATURDAY -> text += "토"
                                    MaterialDayPicker.Weekday.SUNDAY -> text += "일"
                                }
                                if (i == doy.selectedDays.size - 1) break;
                                text += ", "
                            }
                        } else text = "매일"
                        binding.tvSetDateText.text = text

                        if (habit_no != 0 && !settedData?.day_of_week!!.equals(text)) {
                            binding.btnHabitSubmit.isEnabled = true
                        } else if (habit_no != 0 && settedData?.day_of_week!!.equals(text)) {
                            binding.btnHabitSubmit.isEnabled = false
                        }

                        dialog.cancel()
                    }
                })

            layout.findViewById<AppCompatButton>(R.id.btn_cancel)
                .setOnClickListener(View.OnClickListener {
                    dialog.cancel()
                })
        })

        // 알람 스위치
        binding.switchAlarm.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    val layout = layoutInflater.inflate(R.layout.dialog_sheet_alarm, null)
                    val build = AlertDialog.Builder(requireContext()).apply {
                        setView(layout)
                    }
                    val dialog = build.create()
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                    dialog.show()

                    val np_daynight = layout.findViewById<NumberPicker>(R.id.np_daynight)
                    val np_hour = layout.findViewById<NumberPicker>(R.id.np_hour)
                    val np_minute = layout.findViewById<NumberPicker>(R.id.np_minute)
                    Log.e("LocalDateTime", "${LocalDateTime.now().toLocalTime()}")

                    np_daynight.setMinValue(0);
                    np_daynight.setMaxValue(1);
                    np_daynight.setDisplayedValues(arrayOf("오전", "오후"));
                    if (LocalDateTime.now().toLocalTime().hour >= 12) {
                        np_daynight.value = 1
                    }

                    np_hour.minValue = 1
                    np_hour.maxValue = 12
                    np_hour.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })
                    if (LocalDateTime.now().toLocalTime().hour >= 12) {
                        np_hour.value = LocalDateTime.now().toLocalTime().hour - 12
                    } else np_hour.value = LocalDateTime.now().toLocalTime().hour

                    np_minute.minValue = 0
                    np_minute.maxValue = 59
                    np_minute.setFormatter(NumberPicker.Formatter { i -> String.format("%02d", i) })
                    np_minute.value = LocalDateTime.now().toLocalTime().minute

                    layout.findViewById<AppCompatButton>(R.id.btn_submit)
                        .setOnClickListener(View.OnClickListener {
                            binding.switchAlarm.isChecked = true

                            binding.tvAlarmText.text =
                                "${np_daynight.displayedValues[np_daynight.value]} ${
                                    String.format(
                                        "%02d",
                                        np_hour.value
                                    )
                                }시 ${String.format("%02d", np_minute.value)}분"

                            if (habit_no != 0 && !settedData?.alarm!!.equals(binding.tvAlarmText.text)) {
                                binding.btnHabitSubmit.isEnabled = true
                            } else if (habit_no != 0 && settedData?.alarm!!.equals(binding.tvAlarmText.text)) {
                                binding.btnHabitSubmit.isEnabled = false
                            }

                            dialog.cancel()
                        })

                    layout.findViewById<AppCompatButton>(R.id.btn_cancel)
                        .setOnClickListener(View.OnClickListener {
                            dialog.cancel()
                            binding.switchAlarm.isChecked = false
                        })

                    if (habit_no != 0 && !settedData?.alarm!!.equals(binding.tvAlarmText.text)) {
                        binding.btnHabitSubmit.isEnabled = true
                    } else if (habit_no != 0 && settedData?.alarm!!.equals(binding.tvAlarmText.text)) {
                        binding.btnHabitSubmit.isEnabled = false
                    }
                }
                else -> {
                    binding.tvAlarmText.text = "없음"

                    if (habit_no != 0 && !settedData?.alarm!!.equals(binding.tvAlarmText.text)) {
                        binding.btnHabitSubmit.isEnabled = true
                    } else if (habit_no != 0 && settedData?.alarm!!.equals(binding.tvAlarmText.text)) {
                        binding.btnHabitSubmit.isEnabled = false
                    }
                }

            }
        })

        // 잼콘 개수
        binding.btnPlus.setOnClickListener(View.OnClickListener {
            val curZemcon = binding.tvGiveZemconCount.text.toString()
            Log.e("SetHabitFragment", "${curZemcon}")
            if ((curZemcon.toInt() + 5) > 0) {
                binding.btnMinus.isEnabled = true
                binding.btnMinus.setTextColor(Color.parseColor("#000000"))
            }
            if ((curZemcon.toInt() + 5).toString().equals("50")) {
                binding.btnPlus.isEnabled = false
                binding.btnPlus.setTextColor(Color.parseColor("#19000000"))
            }
            binding.tvGiveZemconCount.text = (curZemcon.toInt() + 5).toString()

            if (habit_no != 0 && !settedData?.zemcon!!.equals(binding.tvGiveZemconCount.text)) {
                binding.btnHabitSubmit.isEnabled = true
            } else if (habit_no != 0 && settedData?.zemcon!!.equals(binding.tvGiveZemconCount.text)) {
                binding.btnHabitSubmit.isEnabled = false
            }
        })

        binding.btnMinus.setOnClickListener(View.OnClickListener {
            val curZemcon = binding.tvGiveZemconCount.text.toString()
            Log.e("SetHabitFragment", "${curZemcon}")
            if ((curZemcon.toInt() - 5) < 50) {
                binding.btnPlus.isEnabled = true
                binding.btnPlus.setTextColor(Color.parseColor("#000000"))
            }
            if ((curZemcon.toInt() - 5).toString().equals("0")) {
                binding.btnMinus.isEnabled = false
                binding.btnMinus.setTextColor(Color.parseColor("#19000000"))
            }
            binding.tvGiveZemconCount.text = (curZemcon!!.toInt() - 5).toString()

            if (habit_no != 0 && !settedData?.zemcon!!.equals(binding.tvGiveZemconCount.text)) {
                binding.btnHabitSubmit.isEnabled = true
            } else if (habit_no != 0 && settedData?.zemcon!!.equals(binding.tvGiveZemconCount.text)) {
                binding.btnHabitSubmit.isEnabled = false
            }
//            else {
//                binding.btnMinus.focusable = View.FOCUSABLE
//                binding.btnMinus.setTextColor(Color.parseColor("#000000"))
//            }
        })

        binding.btnHabitCancel.setOnClickListener(View.OnClickListener {
            Log.e("SetHabitFragment()", "취소 클릭됨")
            val layout = layoutInflater.inflate(R.layout.dialog_sheet_notice4, null)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout)
            }
            val dialog = build.create()
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
            dialog.show()

            layout.findViewById<AppCompatButton>(R.id.btn_cancel).setOnClickListener(View.OnClickListener {
                dialog.cancel()
            })
            layout.findViewById<AppCompatButton>(R.id.btn_submit).setOnClickListener(View.OnClickListener {
                requireActivity().onBackPressed()
                dialog.cancel()
                onDestroy()
            })
        })

        binding.btnHabitSubmit.setOnClickListener(View.OnClickListener {
            val getCategory = binding.tvSettingTitle.text.toString()
            val getHabitName = binding.etHabitName.text.toString()
            val getStartDate = binding.tvStartDate.text.toString()
            val getEndDate = binding.tvEndDate.text.toString()
            val getDayOfWeek = binding.tvSetDateText.text.toString()
            val getAlarm = binding.tvAlarmText.text.toString()
            val getZemcon = binding.tvGiveZemconCount.text.toString()

            if(habit_no != 0) {
                RoomAccess(requireActivity()).updateById(habit_no!!, getStartDate, getEndDate, getDayOfWeek, getAlarm, getZemcon)
                val intent = Intent()
                intent.putExtra("key2", "${RoomAccess(requireContext()).habitGetById(habit_no!!).type}")
                requireActivity().setResult(AppCompatActivity.RESULT_OK, intent)
                requireActivity().finish()
            } else {
                Log.e("SetHabitFragment()", "${RoomAccess(requireActivity()).habitGetCount()}")
                RoomAccess(requireActivity()).habitInsert(
                    HabitSettingData(
                        RoomAccess(requireActivity()).habitGetCount() + 1,
                        "대기",
                        getCategory,
                        getHabitName,
                        getStartDate,
                        getEndDate,
                        getDayOfWeek,
                        getAlarm,
                        getZemcon
                    )
                )
                val intent = Intent()
                intent.putExtra("key", "Send Data")
                requireActivity().setResult(AppCompatActivity.RESULT_OK, intent)
                requireActivity().finish()
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SetHabitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetHabitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getDaysInMonth(month: Int, year: Int): Int {
        return when (month - 1) {
            Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
            Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
            Calendar.FEBRUARY -> if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) 29 else 28 // 윤년계산
            else -> throw IllegalArgumentException("Invalid Month")
        }
    }

}

