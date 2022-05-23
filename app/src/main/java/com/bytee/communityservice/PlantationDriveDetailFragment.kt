package com.bytee.communityservice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.databinding.FragmentPlantationDriveDetailBinding
import com.bytee.communityservice.databinding.FragmentPlantationDriveListBinding
import com.bytee.communityservice.model.Environmental

class PlantationDriveDetailFragment : Fragment() {

    lateinit var _binding: FragmentPlantationDriveDetailBinding
    private val binding get() = _binding
    lateinit var environmental: Environmental


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantationDriveDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        environmental = arguments?.getParcelable("enviro")!!

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


        binding.tvTitle.text = environmental.driveName
        binding.tvDateTime.text = environmental.time
        binding.tvAddress.text = environmental.address
        binding.tvDescription.text = environmental.description

        binding.tvDirection.setOnClickListener {
            val geoUri =
                "http://maps.google.com/maps?q=loc:" + environmental.latitude + "," + environmental.longitude
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
            startActivity(intent)
        }

        binding.tvWhatsapp.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=${+923155254086}"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        binding.tvCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:03155254086")
            startActivity(intent)
        }

        binding.buttonRegister.setOnClickListener {
            findNavController().navigate(R.id.appointmentScreen)
        }



    }
}