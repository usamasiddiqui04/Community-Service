package com.bytee.communityservice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.databinding.DashBoardScreenBinding
import com.bytee.communityservice.databinding.FragmentSplahScreenBinding


class DashBoardScreen : Fragment() {


    lateinit var _binding: DashBoardScreenBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DashBoardScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvAssistHandycapped.setOnClickListener {
            findNavController().navigate(R.id.handiCappedDashboardFragment)
        }
        binding.cvAssistOrphans.setOnClickListener {
            findNavController().navigate(R.id.handiCappedDashboardFragment)
        }
        binding.cvBloodDonation.setOnClickListener {
            findNavController().navigate(R.id.bloodDonationDashboardFragment)
        }
        binding.cvEnvDrive.setOnClickListener {
            findNavController().navigate(R.id.environmentalDriveDashboardFragment)
        }
        binding.cvShareAMeal.setOnClickListener {
            findNavController().navigate(R.id.shareAMealFragment)
        }
    }

}