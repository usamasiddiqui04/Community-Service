package com.bytee.communityservice.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytee.communityservice.adapter.HandicapAdapter
import com.bytee.communityservice.databinding.FragmentAvailableWheelChairListBinding
import com.bytee.communityservice.model.Handicap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AvailableWheelChairListFragment : Fragment() {


    lateinit var _binding: FragmentAvailableWheelChairListBinding
    private val binding get() = _binding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    val list = arrayListOf<Handicap>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAvailableWheelChairListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        //adding a layoutmanager
        binding.rvMain.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.reference.child("Handicap Drive")

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
                                val address = snapshot.child("address").value.toString()
                                val email = snapshot.child("email").value.toString()
                                val ngoName = snapshot.child("ngoName").value.toString()
                                val managerName = snapshot.child("managerName").value.toString()
                                val latitude = snapshot.child("lat").value.toString()
                                val longitude = snapshot.child("long").value.toString()
                                val time = snapshot.child("time").value.toString()
                                val description = snapshot.child("description").value.toString()


                                val handicap = Handicap(
                                    managerName = managerName,
                                    email = email,
                                    description = description,
                                    ngoName = ngoName,
                                    address = address,
                                    lat = latitude,
                                    long = longitude,
                                    time = time
                                )

                                list.add(handicap)

                                val adapter = HandicapAdapter(list)
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