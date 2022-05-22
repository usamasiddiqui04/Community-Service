package com.bytee.communityservice.ui.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.DashBoardScreenBinding
import com.bytee.communityservice.model.BloodDonor
import com.bytee.communityservice.model.Handicap
import com.bytee.communityservice.module.RegistrationActivity
import com.bytee.communityservice.utils.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class DashBoardScreen : Fragment() {


    lateinit var _binding: DashBoardScreenBinding
    private val binding get() = _binding
    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    val list = arrayListOf<Handicap>()
    lateinit var prefs: Prefs

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
        prefs = Prefs(requireContext())
        // below line is used to get reference for our database.
//        databaseReference = firebaseDatabase.reference.child("Blood")

//        getList()

        binding.logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), RegistrationActivity::class.java))
            activity?.finish()
        }

        Log.d("TAG", "onViewCreated: ${list.size}")



        binding.userName.text = prefs.name
        binding.userEmail.text = prefs.email

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
}