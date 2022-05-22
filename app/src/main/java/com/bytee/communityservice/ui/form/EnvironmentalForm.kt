package com.bytee.communityservice.ui.form

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.FragmentEnvironmentalFormBinding
import com.bytee.communityservice.databinding.FragmentHandicapFormBinding
import com.bytee.communityservice.databinding.FragmentSingupBinding
import com.bytee.communityservice.model.Environmental
import com.bytee.communityservice.model.Handicap
import com.bytee.communityservice.module.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class EnvironmentalForm : Fragment() {


    lateinit var _binding: FragmentEnvironmentalFormBinding
    private val binding get() = _binding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var driveName: String
    lateinit var email: String
    lateinit var address: String
    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var description: String

    lateinit var environment: Environmental

    private val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnvironmentalFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.reference.child("Environmental Drive")

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), RegistrationActivity::class.java))
            activity?.finish()
        }

        binding.uploadButton.setOnClickListener {

            driveName = binding.driveNameEditText.text.toString()
            email = binding.emailEditText.text.toString()
            address = binding.addressNameEditText.text.toString()
            latitude = binding.latNameEditText.text.toString()
            longitude = binding.longNameEditText.text.toString()
            description = binding.desNameEditText.text.toString()

            environment =
                Environmental(
                    driveName = driveName,
                    address = address,
                    time = timeStamp,
                    latitude = latitude,
                    longitude = longitude,
                    description = description
                )


            if (driveName.isNotEmpty() || email.isNotEmpty() || address.isNotEmpty() ||
                latitude.isNotEmpty() || longitude.isNotEmpty() || description.isNotEmpty()
            ) {
                uploadData()
            }

        }


    }

    private fun uploadData() {

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val key = firebaseDatabase.reference.push().key
                databaseReference.child(key!!).setValue(environment).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.driveNameEditText.text?.clear()
                        binding.emailEditText.text?.clear()
                        binding.addressNameEditText.text?.clear()
                        binding.longNameEditText.text?.clear()
                        binding.latNameEditText.text?.clear()
                        binding.desNameEditText.text?.clear()
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