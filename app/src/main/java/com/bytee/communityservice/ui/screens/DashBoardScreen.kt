package com.bytee.communityservice.ui.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.DashBoardScreenBinding
import com.bytee.communityservice.model.BloodDonor
import com.bytee.communityservice.model.Handicap
import com.bytee.communityservice.module.RegistrationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class DashBoardScreen : Fragment() {


    lateinit var _binding: DashBoardScreenBinding
    private val binding get() = _binding
    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    val list = arrayListOf<Handicap>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DashBoardScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        // below line is used to get reference for our database.
//        databaseReference = firebaseDatabase.reference.child("Blood")

        getList()

        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), RegistrationActivity::class.java))
            activity?.finish()
        }

        Log.d("TAG", "onViewCreated: ${list.size}")



        binding.cvAssistHandycapped.setOnClickListener {
            findNavController().navigate(R.id.handiCappedDashboardFragment)
        }
        binding.cvAssistOrphans.setOnClickListener {
            findNavController().navigate(R.id.handiCappedDashboardFragment)
        }
        binding.cvBloodDonation.setOnClickListener {
            findNavController().navigate(R.id.bloodDonationDashboardFragment)
        }
        binding.cvEnvDrive.setOnClickListener {
            findNavController().navigate(R.id.environmentalDriveDashboardFragment)
        }
        binding.cvShareAMeal.setOnClickListener {
            findNavController().navigate(R.id.shareAMealFragment)
        }

    }

    fun getList() {
//        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                for (child in snapshot.children) {
//
//                    databaseReference.child(child.key!!)
//                        .addListenerForSingleValueEvent(object : ValueEventListener {
//                            override fun onDataChange(snapshot: DataSnapshot) {
//                                val bloodGroup = snapshot.child("bloodGroup").value.toString()
//                                val email = snapshot.child("email").value.toString()
//                                val hospitalName = snapshot.child("hospitalName").value.toString()
//                                val managerName = snapshot.child("managerName").value.toString()
//                                val latitude = snapshot.child("latitude").value.toString()
//                                val longitude = snapshot.child("longitude").value.toString()
//                                val patientName = snapshot.child("patientName").value.toString()
//
//
//                                val bloodDonor = BloodDonor(
//                                    managerName = managerName,
//                                    email = email,
//                                    hospitalName = hospitalName,
//                                    bloodGroup = bloodGroup,
//                                    patientName = patientName,
//                                    latitude = latitude,
//                                    longitude = longitude,
//                                    description = "sads"
//                                )
//                                list.add(bloodDonor)
//
//                            }
//
//                            override fun onCancelled(error: DatabaseError) {
//                            }
//
//                        })
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(
//                    requireContext(),
//                    "$error",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })

        val ref = FirebaseDatabase.getInstance().reference.child("Handicap Drive")
        ref.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    //Get map of users in datasnapshot
//                    for (postSnapshot in dataSnapshot.children) {
//                        val user: Handicap? = postSnapshot.getValue(Handicap::class.java)
//                        list.add(user!!);
//                    }


                    Log.d("", "")
//                    collectPhoneNumbers(dataSnapshot.value as Map<String?, Any?>?)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //handle databaseError
                }
            })
    }

//    private fun collectBloodItems(bloodList: Map<String, Any>) {
//        val phoneNumbers: ArrayList<Long?> = ArrayList()
//
//        //iterate through each user, ignoring their UID
//        for ((_, value): Map.Entry<String, Any> in bloodList) {
//
//            //Get user map
//            val singleUser = value as Map<*, *>
//            //Get phone field and append to list
//            phoneNumbers.add(singleUser["phone"] as Long?)
//        }
//        System.out.println(phoneNumbers.toString())
//    }

}