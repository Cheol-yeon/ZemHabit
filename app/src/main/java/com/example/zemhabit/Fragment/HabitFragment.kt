package com.example.zemhabit.Fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.zemhabit.Adapter.SectionsPagerAdapter
import com.example.zemhabit.MakeHabitActivity
import com.example.zemhabit.Room.RoomAccess
import com.example.zemhabit.databinding.FragmentHabitBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [HabitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private var _binding: FragmentHabitBinding? = null
// This property is only valid between onCreateView and
// onDestroyView.
private val binding get() = _binding!!

class HabitFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val types: List<String> = listOf("대기", "수정대기", "수정요청")
    val types2: List<String> = listOf("진행중", "완료요청", "완료대기")
    val types3: List<String> = listOf("완료", "칭찬완료")

    init {
        Log.e("HabitFragment", "In HabitFragment")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        Log.e("HabitFragment", "Start HabitFragment")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("HabitFragment", "onCreateView")
        _binding = FragmentHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("HabitFragment", "onViewCreated")

        val activityresultlauncer: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                Log.e("ActivityResultLauncher", "Sign OK")
                val data: Intent? = it.data
                val text: String? = data?.getStringExtra("key")
                Log.e("ActivityResultLauncher", "Get Data = ${text}")

                binding.tlHabit.setNumber(2, "${RoomAccess(requireContext()).habitGetCount()}")
                val tab: TabLayout.Tab = binding.tlHabit.getTabAt(2)!!
                tab.select()
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        binding.vp2Habit.isUserInputEnabled = false
        binding.vp2Habit.adapter = sectionsPagerAdapter

        TabLayoutMediator(
            binding.tlHabit, binding.vp2Habit
        ) { tab, position -> tab.text = getPageTitle(position) }.attach()

//        binding.tlHabit.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.position?.let { binding.vp2Habit?.setCurrentItem(it, false) }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//        })

        binding.tlHabit.setNumber(0, "0")
        binding.tlHabit.setNumber(1, "0")
        Log.e("habitCount", "${RoomAccess(requireContext()).getByType("대기").size}")
        binding.tlHabit.setNumber(2, "${RoomAccess(requireContext()).getByType("대기").size}")

        binding.flbAdd.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(activity, MakeHabitActivity::class.java)
            activityresultlauncer.launch(intent)
        })

        binding.tvHabitTitle.setOnClickListener(View.OnClickListener {
            RoomAccess(requireActivity()).deleteAll()
            Log.e("deleteAll()", "${RoomAccess(requireActivity()).habitGetCount()}")
            refreshFragment(WaitingFragment(), childFragmentManager)

        })
    }

    fun refreshFragment(fragment: Fragment, fragmentManager: FragmentManager) {
        Log.e("refreshFragment", "refresh ${fragment}, ${fragmentManager}")
        var ft: FragmentTransaction = fragmentManager.beginTransaction()
        ft.detach(fragment).attach(fragment).commit()
//        binding.tlHabit.setNumber(2, "${RoomAccess(requireContext()).habitGetCount()}")
    }


    override fun onResume() {
        super.onResume()
        Log.e("HabitFragment()", "On Resume")
        binding.tlHabit.setNumber(2, "${RoomAccess(requireContext()).getMulType(types).size}")
        refreshFragment(WaitingFragment(), childFragmentManager)
        binding.tlHabit.setNumber(1, "${RoomAccess(requireContext()).getMulType(types3).size}")
        refreshFragment(CompletionFragment(), childFragmentManager)
        binding.tlHabit.setNumber(0, "${RoomAccess(requireContext()).getMulType(types2).size}")
        refreshFragment(ProceedFragment(), childFragmentManager)
        binding.tvZemconCount.text = "${RoomAccess(requireContext()).sumZemcon()}"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HabitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HabitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "진행중"
            1 -> return "완료"
            2 -> return "대기"
        }
        return null
    }
}