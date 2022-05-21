package com.bytee.communityservice.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.databinding.FragmentSingupBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class SignupFragment : Fragment() {

    lateinit var _binding: FragmentSingupBinding
    private val binding get() = _binding
    lateinit var email: String
    lateinit var password: String
    lateinit var firebaseDatabase: FirebaseDatabase

    // creating a variable for our Database
    // Reference for Firebase.
    lateinit var databaseReference: DatabaseReference


    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSingupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.reference.child("Users")

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)


        binding.singupTextView.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.buttonLogin.setOnClickListener {
            email = binding.emailEditText.text.toString()
            password = binding.passWordEditText.text.toString()
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        binding.progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                requireActivity()
            ) { task ->
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(), "Authentication failed." + task.exception,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    auth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                        Toast.makeText(
                            requireContext(),
                            "Registered , Check email to verified",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    databaseReference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            // inside the method of on Data change we are setting
                            // our object class to our database reference.
                            // data base reference will sends data to firebase.
                            val hashMap : HashMap<String,String> = HashMap()

                            hashMap["name"] = binding.nameEditText.text.toString()
                            hashMap["email"] = email
                            hashMap["id"] = auth.currentUser!!.uid

                            databaseReference.child(auth.currentUser!!.uid).setValue(hashMap)

                            // after adding this data we are showing toast message.
                            Toast.makeText(requireContext(), "data added", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // if the data is not added or it is cancelled then
                            // we are displaying a failure toast message.
                            Toast.makeText(
                                requireContext(),
                                "Fail to add data $error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

                    binding.progressBar.visibility = View.GONE

                }
            }

    }


}