package com.bytee.communityservice.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.DashBoardScreenBinding
import com.bytee.communityservice.databinding.FragmentHandiCappedDashboardBinding


class HandiCappedDashboardFragment : Fragment() {
    lateinit var _binding: FragmentHandiCappedDashboardBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHandiCappedDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.cvJoinEvent.setOnClickListener {
            findNavController().navigate(R.id.joinHandyCappedEventListFragment)
        }

        binding.cvLocateWheelChair.setOnClickListener {
            findNavController().navigate(R.id.availableWheelChairListFragment)
        }

    }
}