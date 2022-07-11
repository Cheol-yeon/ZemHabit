package com.example.zemhabit.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zemhabit.Adapter.HabitListAdapter
import com.example.zemhabit.databinding.FragmentHabitListBinding
import kotlinx.android.synthetic.main.fragment_habit_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HabitListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HabitListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var mainTitle: String? = null
    var subTitle: String? = null
    var imgIcon: Int? = null

    var habitList: ArrayList<String>? = null

    lateinit var habitListAdapter: HabitListAdapter
    lateinit var layoutManager: LinearLayoutManager

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        mainTitle = arguments?.getString("mainTitle")
        subTitle = arguments?.getString("subTitle")
        imgIcon = arguments?.getInt("imgIcon")
        Log.e("HabitListFragment", "${subTitle} ${mainTitle} ${imgIcon}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_listTitle.text = "${subTitle} ${mainTitle}"
        iv_listIcon.setImageResource(imgIcon!!)

        setList(mainTitle)

        binding.rvHabitList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
        binding.rvHabitList.setLayoutManager(layoutManager)
        habitListAdapter = HabitListAdapter(requireContext(), habitList!!, tv_listTitle.text as String, imgIcon!!)
        binding.rvHabitList.adapter = habitListAdapter
    }

    private fun setList(mainTitle: String?) {
        Log.e("setList", "${mainTitle}")
        habitList = ArrayList<String>()
        when(mainTitle) {
            "몸" -> {
                habitList!!.add("줄넘기 100번하기")
                habitList!!.add("계단 오르기")
                habitList!!.add("홈트레이닝 하기")
                habitList!!.add("철봉 메달리기")
                habitList!!.add("우유 3컵 마시기")
                habitList!!.add("편식 안하기")
                habitList!!.add("간식 조절하기")
                habitList!!.add("치실 하기")
                habitList!!.add("기타")
            }
            "공부" -> {
                habitList!!.add("한글책 읽기")
                habitList!!.add("영어책 읽기")
                habitList!!.add("영어 단어 외우기")
                habitList!!.add("수학 연산 풀기")
                habitList!!.add("학원 숙제 하기")
                habitList!!.add("공부 시간 지키기")
                habitList!!.add("기타")
            }
            "언어 사용" -> {
                habitList!!.add("주변 어른에게 인사 잘하기")
                habitList!!.add("친구와 고운말 쓰기")
                habitList!!.add("소리지르지 않기")
                habitList!!.add("식사 인사하기")
                habitList!!.add("문안 인사하기")
                habitList!!.add("기타")
            }
            "시간 관리" -> {
                habitList!!.add("일찍 자고 일찍 일어나기")
                habitList!!.add("스스로 등교 준비하기")
                habitList!!.add("학교 5분 전에 도착하기")
                habitList!!.add("학원 5분 전에 도착하기")
                habitList!!.add("쉬는 시간 지키기")
                habitList!!.add("기타")
            }
            "스마트폰 사용" -> {
                habitList!!.add("게임 시간 지키기")
                habitList!!.add("동영상 시청시간 지키기")
                habitList!!.add("정해진 시간에 SNS 하기")
                habitList!!.add("전화 예절 지키기")
                habitList!!.add("가족끼리 전화 끊을때 사랑해 말하기")
                habitList!!.add("기타")
            }
            "집안 생활" -> {
                habitList!!.add("나만의 집안일 정하기")
                habitList!!.add("식사준비 돕기")
                habitList!!.add("다먹은 그릇 정리하기")
                habitList!!.add("책상 정리하기")
                habitList!!.add("이불 정돈하기")
                habitList!!.add("빨랫간 제자리 두기")
                habitList!!.add("외출복 걸어 두기")
                habitList!!.add("애완동물 산책시키기")
                habitList!!.add("기타")
            }
            "학교 생활" -> {
                habitList!!.add("알림장 준비물 챙기기")
                habitList!!.add("필통 챙기기")
                habitList!!.add("일기 쓰기")
                habitList!!.add("예쁘게 글씨쓰기")
                habitList!!.add("기타")
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HabitListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HabitListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}