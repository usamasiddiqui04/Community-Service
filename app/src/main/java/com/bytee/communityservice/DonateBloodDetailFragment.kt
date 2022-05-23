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
import com.bytee.communityservice.databinding.FragmentDonateBloodDetailBinding
import com.bytee.communityservice.databinding.FragmentPlantationDriveDetailBinding
import com.bytee.communityservice.model.BloodDonor
import com.bytee.communityservice.model.Environmental
import java.text.SimpleDateFormat
import java.util.*

class DonateBloodDetailFragment : Fragment() {


    lateinit var _binding: FragmentDonateBloodDetailBinding
    private val binding get() = _binding
    lateinit var bloodDonor: BloodDonor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonateBloodDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bloodDonor = arguments?.getParcelable("blood")!!

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


        binding.tvTitle.text = bloodDonor.bloodGroup
        binding.tvDateTime.text =   SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        binding.tvAddress.text = bloodDonor.hospitalName
        binding.tvDescription.text = bloodDonor.description

        binding.tvDirection.setOnClickListener {
            val geoUri =
                "http://maps.google.com/maps?q=loc:" + bloodDonor.latitude + "," + bloodDonor.longitude
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