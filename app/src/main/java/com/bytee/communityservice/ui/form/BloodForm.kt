package com.bytee.communityservice.ui.form

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bytee.communityservice.databinding.FragmentBloodFormBinding
import com.bytee.communityservice.model.BloodDonor
import com.bytee.communityservice.module.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.timerTask


class BloodForm : Fragment() {


    lateinit var _binding: FragmentBloodFormBinding
    private val binding get() = _binding
    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var managerName: String
    lateinit var email: String
    lateinit var hospitalName: String
    lateinit var bloodGroup: String
    lateinit var patientName: String
    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var bloodDonor: BloodDonor
    lateinit var description: String




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBloodFormBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.reference.child("Blood")


        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext() ,RegistrationActivity::class.java ))
            activity?.finish()
        }

        binding.uploadButton.setOnClickListener {


            managerName = binding.managerNameEditText.text.toString()
            email = binding.emailEditText.text.toString()
            hospitalName = binding.hospitalNameEditText.text.toString()
            bloodGroup = binding.bloodGroupEditText.text.toString()
            patientName = binding.patientNameEditText.text.toString()
            latitude = binding.latNameEditText.text.toString()
            longitude = binding.longNameEditText.text.toString()
            description = binding.desNameEditText.text.toString()


            bloodDonor = BloodDonor(
                managerName,
                email,
                hospitalName,
                bloodGroup,
                patientName,
                latitude,
                longitude,
                description
            )

            if (managerName.isNotEmpty() || email.isNotEmpty() || hospitalName.isNotEmpty() ||
                bloodGroup.isNotEmpty() || patientName.isNotEmpty() || latitude.isNotEmpty() ||
                longitude.isNotEmpty() || description.isNotEmpty()
            ) {

                uploadData()
            }


        }


    }

    private fun uploadData() {

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val key = firebaseDatabase.reference.push().key
                databaseReference.child(key!!).setValue(bloodDonor).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.managerNameEditText.text?.clear()
                        binding.emailEditText.text?.clear()
                        binding.hospitalNameEditText.text?.clear()
                        binding.bloodGroupEditText.text?.clear()
                        binding.patientNameEditText.text?.clear()
                        binding.latNameEditText.text?.clear()
                        binding.longNameEditText.text?.clear()
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