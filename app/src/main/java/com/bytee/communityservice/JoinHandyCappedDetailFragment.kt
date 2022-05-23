package com.bytee.communityservice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.databinding.FragmentJoinHandyCappedDetailBinding
import com.bytee.communityservice.model.Handicap


class JoinHandyCappedDetailFragment : Fragment() {


    lateinit var _binding: FragmentJoinHandyCappedDetailBinding
    private val binding get() = _binding
    lateinit var handicap: Handicap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJoinHandyCappedDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        handicap = arguments?.getParcelable("handicap")!!

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

        binding.tvDateTime.text = handicap.time
        binding.tvAddress.text = handicap.address
        binding.tvDescription.text = handicap.description
        binding.tvTitle.text = handicap.ngoName

        binding.tvDirection.setOnClickListener {
            val geoUri =
                "http://maps.google.com/maps?q=loc:" + handicap.lat + "," + handicap.long
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