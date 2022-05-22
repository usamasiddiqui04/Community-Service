package com.bytee.communityservice.ui.form

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.FragmentHandicapFormBinding
import com.bytee.communityservice.databinding.FragmentOrphangeFormBinding
import com.bytee.communityservice.model.Orphan
import com.bytee.communityservice.module.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class OrphangeForm : Fragment() {

    lateinit var _binding: FragmentOrphangeFormBinding
    private val binding get() = _binding
    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth

    lateinit var managerName: String
    lateinit var email: String
    lateinit var orphanName: String
    lateinit var address: String
    lateinit var orphan: Orphan
    lateinit var description: String


    private val timeStamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOrphangeFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        firebaseDatabase = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.reference.child("Orphans Drive")



        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), RegistrationActivity::class.java))
            activity?.finish()
        }




        binding.uploadButton.setOnClickListener {
            managerName = binding.managerNameEditText.text.toString()
            email = binding.emailEditText.text.toString()
            orphanName = binding.orphanNameEditText.text.toString()
            address = binding.addressNameEditText.text.toString()
            description = binding.desNameEditText.text.toString()


            orphan = Orphan(
                managerName = managerName,
                orphanName = orphanName,
                email = email,
                address = address,
                date = timeStamp,
                description = description
            )





            if (managerName.isNotEmpty() || email.isNotEmpty() || orphanName.isNotEmpty() || address.isNotEmpty() || description.isNotEmpty()) {
                uploadData()
            }
        }

    }

    private fun uploadData() {

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val key = firebaseDatabase.reference.push().key
                databaseReference.child(key!!).setValue(orphan).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.managerNameEditText.text?.clear()
                        binding.emailEditText.text?.clear()
                        binding.orphanNameEditText.text?.clear()
                        binding.addressNameEditText.text?.clear()
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