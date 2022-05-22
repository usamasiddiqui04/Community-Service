package com.bytee.communityservice.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytee.communityservice.R
import com.bytee.communityservice.adapter.BloodDonateAdapter
import com.bytee.communityservice.adapter.HandicapAdapter
import com.bytee.communityservice.databinding.FragmentDonateBloodListBinding
import com.bytee.communityservice.databinding.FragmentJoinHandyCappedEventListBinding
import com.bytee.communityservice.model.BloodDonor
import com.bytee.communityservice.model.Handicap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DonateBloodListFragment : Fragment() {

    lateinit var _binding: FragmentDonateBloodListBinding
    private val binding get() = _binding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    val list = arrayListOf<BloodDonor>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDonateBloodListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //adding a layoutmanager
        binding.rvMain.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        // below line is used to get reference for our database.


        databaseReference = firebaseDatabase.reference.child("Blood")

        getList()

        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }


    }

    private fun getList() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (child in snapshot.children) {

                    databaseReference.child(child.key!!)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val hospitalName = snapshot.child("hospitalName").value.toString()
                                val email = snapshot.child("email").value.toString()
                                val patientName = snapshot.child("patientName").value.toString()
                                val managerName = snapshot.child("managerName").value.toString()
                                val latitude = snapshot.child("lat").value.toString()
                                val longitude = snapshot.child("long").value.toString()
                                val bloodGroup = snapshot.child("bloodGroup").value.toString()
                                val description = snapshot.child("description").value.toString()


                                val bloodDonor = BloodDonor(
                                    managerName = managerName,
                                    email = email,
                                    description = description,
                                    latitude = latitude,
                                    longitude = longitude,
                                    patientName = patientName,
                                    hospitalName = hospitalName,
                                    bloodGroup = bloodGroup

                                )

                                list.add(bloodDonor)

                                val adapter = BloodDonateAdapter(list)
                                binding.rvMain.adapter = adapter

                            }

                            override fun onCancelled(error: DatabaseError) {
                            }

                        })
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