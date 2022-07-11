package com.example.zemhabit.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.example.zemhabit.Adapter.HabitAdapter
import com.example.zemhabit.Data.HabitSettingData
import com.example.zemhabit.R
import com.example.zemhabit.Room.RoomAccess
import com.example.zemhabit.databinding.FragmentCompletionBinding
import com.example.zemhabit.databinding.FragmentProceedBinding
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CompletionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CompletionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentCompletionBinding? = null
    private val binding get() = _binding!!

    lateinit var habitSettingAdapter: HabitAdapter
    lateinit var layoutManager: GridLayoutManager

    var activityresultlauncer: ActivityResultLauncher<Intent>? = null

    init {
        Log.e("CompletionFragment", "In CompletionFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompletionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityresultlauncer = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                Log.e("CompletionFragment()", "Sign OK")
                val data: Intent? = it.data
                val text: String? = data?.getStringExtra("key2")

                val tab: TabLayout.Tab? = parentFragmentManager.fragments[1].view?.findViewById<TabLayout>(R.id.tl_habit)?.getTabAt(0)
//                Log.e("WaitingFragment()", "${parentFragmentManager.fragments}")
                tab?.select()

                HabitFragment().refreshFragment(CompletionFragment(), childFragmentManager)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("CompletionFragment", "onStart")
        val types: List<String> = listOf("완료", "칭찬완료")
        if (RoomAccess(requireActivity()).getMulType(types).isEmpty()) {
            binding.rvWaiting.visibility = View.GONE
        } else {
            Log.e("onStart", "${RoomAccess(requireActivity()).habitGetCount()}")

            binding.rvWaiting.visibility = View.VISIBLE
            binding.rvWaiting.setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            binding.rvWaiting.setLayoutManager(layoutManager)
            habitSettingAdapter = HabitAdapter(requireContext(), RoomAccess(requireContext()).getMulType(types) as ArrayList<HabitSettingData>, activityresultlauncer!!)
            binding.rvWaiting.adapter = habitSettingAdapter
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CompletionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompletionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}