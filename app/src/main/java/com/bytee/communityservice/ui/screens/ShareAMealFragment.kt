package com.bytee.communityservice.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.FragmentBloodDonationDashboardBinding
import com.bytee.communityservice.databinding.FragmentShareAMealBinding

class ShareAMealFragment : Fragment() {
    lateinit var _binding: FragmentShareAMealBinding
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShareAMealBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)


        binding.ivAvailableRest.setOnClickListener {
            val geoUri =
                "http://maps.google.com/maps?q=loc:" + "restaurants"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
            startActivity(intent)
        }

        binding.ivFoodDrive.setOnClickListener {
            findNavController().navigate(R.id.joinFoodDriveListFragment)
        }

    }
}