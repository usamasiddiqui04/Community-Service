package com.bytee.communityservice.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.FragmentBloodDonationDashboardBinding
import com.bytee.communityservice.databinding.FragmentEnvironmentalDriveDashboardBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EnvironmentalDriveDashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EnvironmentalDriveDashboardFragment : Fragment() {
    lateinit var _binding: FragmentEnvironmentalDriveDashboardBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnvironmentalDriveDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.tvOption1.setOnClickListener {
            findNavController().navigate(R.id.plantationDriveListFragment)
        }

        binding.tvOption2.setOnClickListener {
            findNavController().navigate(R.id.cleanlinessDriveListFragment)
        }

    }
}