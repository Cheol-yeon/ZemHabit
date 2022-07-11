package com.example.zemhabit.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zemhabit.Adapter.HabitCategoryAdapter
import com.example.zemhabit.Data.HabitCategoryData
import com.example.zemhabit.R
import com.example.zemhabit.databinding.FragmentHabitCategoryBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private var _binding: FragmentHabitCategoryBinding? = null
private val binding get() = _binding!!


class HabitCategory : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var habitCategoryAdapter: HabitCategoryAdapter
    lateinit var layoutManager: GridLayoutManager

    var habitCategoryData: HabitCategoryData? = null
    var habitCategoryDatas: ArrayList<HabitCategoryData> = ArrayList<HabitCategoryData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHabitCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCategory.setHasFixedSize(true)
        layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCategory.setLayoutManager(layoutManager)
        habitCategoryAdapter = HabitCategoryAdapter(requireContext(), habitCategoryDatas!!)
        binding.rvCategory.adapter = habitCategoryAdapter
    }

    fun setData() {
        habitCategoryData = HabitCategoryData(
            "건강한",
            "몸",
            R.drawable.health_icon
        )
        habitCategoryDatas.add(habitCategoryData!!)
        habitCategoryData = HabitCategoryData(
            "꾸준한",
            "공부",
            R.drawable.study_icon
        )
        habitCategoryDatas.add(habitCategoryData!!)
        habitCategoryData = HabitCategoryData(
            "긍정적인",
            "언어 사용",
            R.drawable.message_icon
        )
        habitCategoryDatas.add(habitCategoryData!!)
        habitCategoryData = HabitCategoryData(
            "스스로",
            "시간 관리",
            R.drawable.timer_icon
        )
        habitCategoryDatas.add(habitCategoryData!!)
        habitCategoryData = HabitCategoryData(
            "올바른",
            "스마트폰 사용",
            R.drawable.phone_icon
        )
        habitCategoryDatas.add(habitCategoryData!!)
        habitCategoryData = HabitCategoryData(
            "부지런한",
            "집안 생활",
            R.drawable.house_icon
        )
        habitCategoryDatas.add(habitCategoryData!!)
        habitCategoryData = HabitCategoryData(
            "똑똑한",
            "학교 생활",
            R.drawable.bagpack_icon
        )
        habitCategoryDatas.add(habitCategoryData!!)
        habitCategoryData = HabitCategoryData(
            " ",
            "직접 입력",
            R.drawable.etc_icon
        )
        habitCategoryDatas.add(habitCategoryData!!)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HabitCategory.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HabitCategory().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}