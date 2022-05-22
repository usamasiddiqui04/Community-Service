package com.bytee.communityservice.ui.form

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import com.bytee.communityservice.databinding.FragmentHandicapFormBinding
import com.bytee.communityservice.model.Handicap
import com.bytee.communityservice.module.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class HandicapForm : Fragment() {


    lateinit var _binding: FragmentHandicapFormBinding
    private val binding get() = _binding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var managerName: String
    lateinit var email: String
    lateinit var ngoName: String
    lateinit var address: String
    lateinit var latitude: String
    lateinit var longitude: String

    lateinit var handicap: Handicap

    private val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHandicapFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.reference.child("Handicap Drive")



        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext() , RegistrationActivity::class.java ))
            activity?.finish()
        }


        binding.uploadButton.setOnClickListener {

            managerName = binding.managerNameEditText.text.toString()
            email = binding.emailEditText.text.toString()
            ngoName = binding.ngoNameEditText.text.toString()
            address = binding.addressNameEditText.text.toString()
            latitude = binding.latNameEditText.text.toString()
            longitude = binding.longNameEditText.text.toString()



            handicap = Handicap(
                ngoName = ngoName, managerName = managerName, email = email,
                address = address, lat = latitude, long = longitude , time = timeStamp
            )


            if (managerName.isNotEmpty() || email.isNotEmpty() || ngoName.isNotEmpty() || address.isNotEmpty() ||
                latitude.isNotEmpty() || longitude.isNotEmpty()
            ) {
                uploadData()
            }

        }

    }

    private fun uploadData() {

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val key = firebaseDatabase.reference.push().key
                databaseReference.child(key!!).setValue(handicap).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.managerNameEditText.text?.clear()
                        binding.emailEditText.text?.clear()
                        binding.ngoNameEditText.text?.clear()
                        binding.addressNameEditText.text?.clear()
                        binding.longNameEditText.text?.clear()
                        binding.latNameEditText.text?.clear()
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