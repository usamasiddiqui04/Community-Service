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
import com.bytee.communityservice.databinding.FragmentBloodDonationDashboardBinding
import com.bytee.communityservice.databinding.FragmentFreeFoodDetailBinding
import com.bytee.communityservice.model.ShareMeal


class JoinFreeFoodDetailsFragment : Fragment() {


    lateinit var _binding: FragmentFreeFoodDetailBinding
    private val binding get() = _binding
    lateinit var shareMeal: ShareMeal

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFreeFoodDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shareMeal = arguments?.getParcelable("shareameal")!!

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

        binding.tvDateTime.text = shareMeal.time
        binding.tvAddress.text = shareMeal.address
        binding.tvDescription.text = shareMeal.description
        binding.tvTitle.text = shareMeal.restaurantName

        binding.tvDirection.setOnClickListener {
            val geoUri =
                "http://maps.google.com/maps?q=loc:" + shareMeal.latitude + "," + shareMeal.longitude
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