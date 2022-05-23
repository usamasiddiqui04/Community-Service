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
import com.bytee.communityservice.databinding.FragmentAssistsOrphanDetailBinding
import com.bytee.communityservice.databinding.FragmentDonateBloodDetailBinding
import com.bytee.communityservice.model.BloodDonor
import com.bytee.communityservice.model.Orphan
import java.text.SimpleDateFormat
import java.util.*

class AssistsOrphanDetailFragment : Fragment() {

    lateinit var _binding: FragmentAssistsOrphanDetailBinding
    private val binding get() = _binding
    lateinit var orphan: Orphan

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssistsOrphanDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        orphan = arguments?.getParcelable("orphan")!!

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


        binding.tvTitle.text = orphan.managerName
        binding.tvDateTime.text =   orphan.date
        binding.tvAddress.text = orphan.address
        binding.tvDescription.text = orphan.description

        binding.tvDirection.setOnClickListener {
            val geoUri =
                "http://maps.google.com/maps?q=loc:" + orphan.address
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