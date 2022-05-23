package com.bytee.communityservice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.databinding.FragmentAppointmentScreenBinding
import com.bytee.communityservice.databinding.FragmentSingupBinding

class AppointmentScreen : Fragment() {


    private lateinit var _binding: FragmentAppointmentScreenBinding
    private val binding get() = _binding
    lateinit var name : String
    lateinit var driveName : String
    lateinit var phoneNumber : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppointmentScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.buttonLogin.setOnClickListener {
            name = binding.nameEditText.text.toString()
            phoneNumber = binding.phoneNumberEditText.text.toString()
            driveName = binding.driveEditText.text.toString()

            if(name.isNotEmpty() || phoneNumber.isNotEmpty() || driveName.isNotEmpty()){
                findNavController().navigate(R.id.action_appointmentScreen_to_successScreen)
            }
        }


    }

}