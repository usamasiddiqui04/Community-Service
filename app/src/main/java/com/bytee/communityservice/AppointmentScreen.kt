package com.bytee.communityservice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.databinding.FragmentAppointmentScreenBinding
import com.bytee.communityservice.databinding.FragmentSingupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AppointmentScreen : Fragment() {


    private lateinit var _binding: FragmentAppointmentScreenBinding
    private val binding get() = _binding
    lateinit var name : String
    lateinit var driveName : String
    lateinit var phoneNumber : String

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth

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
                uploadData()
            }
        }
        firebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.reference.child("Appointment")
    }



    private fun uploadData() {

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val key = firebaseDatabase.reference.push().key

                val hashMap = HashMap<String , String>()
                hashMap["name"] = name
                hashMap["driveName"] = driveName
                hashMap["phoneNumber"] = phoneNumber
                databaseReference.child(key!!).setValue(hashMap).addOnCompleteListener {
                    if (it.isSuccessful){
                        binding.nameEditText.text?.clear()
                        binding.phoneNumberEditText.text?.clear()
                        binding.driveEditText.text?.clear()
                        findNavController().navigate(R.id.action_appointmentScreen_to_successScreen)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "$error",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}