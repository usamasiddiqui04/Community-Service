package com.bytee.communityservice.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytee.communityservice.adapter.HandicapAdapter
import com.bytee.communityservice.databinding.FragmentJoinHandyCappedEventListBinding
import com.bytee.communityservice.model.Handicap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class JoinHandyCappedEventListFragment : Fragment() {
    lateinit var _binding: FragmentJoinHandyCappedEventListBinding
    private val binding get() = _binding

    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    val list = arrayListOf<Handicap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJoinHandyCappedEventListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getting recyclerview from xml

        //adding a layoutmanager
        binding.rvMain.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.reference.child("Handicap Drive")

        getList()


        //crating an arraylist to store users using the data class user
//        val titleList = ArrayList<String>()
//
//        //adding some dummy data to the list
//        titleList.add("Belal Khan")
//        titleList.add("Ramiz Khan")
//        titleList.add("Faiz Khan")
//        titleList.add("Yashar Khan")

        //creating our adapter



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