package com.bytee.communityservice.ui.screens

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.FragmentLoginScreenBinding
import com.bytee.communityservice.module.DashBoardActivity
import com.bytee.communityservice.module.FormActivity
import com.bytee.communityservice.utils.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class LoginScreen : Fragment() {


    private lateinit var _binding: FragmentLoginScreenBinding
    private val binding get() = _binding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var email: String
    lateinit var password: String
    lateinit var prefs: Prefs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Users")
        prefs = Prefs(requireContext())


        binding.singupTextView.setOnClickListener {
            findNavController().navigate(R.id.action_loginScreen_to_singupFragment)
        }


        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        binding.buttonLogin.setOnClickListener {
            email = binding.emailEditText.text.toString()
            password = binding.passWordEditText.text.toString()

            if (email.isEmpty()) {
                binding.emailTextInputLayout.isErrorEnabled = true
                binding.emailTextInputLayout.helperText = "this is a required field"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordTextInputLayout.isErrorEnabled = true
                binding.passwordTextInputLayout.helperText = "this is a required filed"

                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.passwordTextInputLayout.isErrorEnabled = true
                binding.passwordTextInputLayout.helperText =
                    "password length should be greater than 8 characters"

                return@setOnClickListener
            }
            firebaseLogin()
        }

    }

    private fun firebaseLogin() {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    if (auth.currentUser!!.isEmailVerified) {

                        databaseReference.child(auth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val type = snapshot.child("accountType").value.toString()
                                val name = snapshot.child("name").value.toString()
                                val phoneNumber = snapshot.child("phoneNumber").value.toString()
                                val email = snapshot.child("email").value.toString()

                                prefs.name = name
                                prefs.email = email
                                prefs.phoneNumber = phoneNumber
                                prefs.type = type

                                if(type == "Normal"){
                                    startActivity(Intent(requireContext() , DashBoardActivity::class.java))
                                    activity?.finish()
                                }else {
                                    startActivity(Intent(requireContext() , FormActivity::class.java))
                                    activity?.finish()
                                }


                            }

                            override fun onCancelled(error: DatabaseError) {
                                // calling on cancelled method when we receive
                                // any error or we are not able to get the data.
                                Toast.makeText(
                                    requireContext(),
                                    "Fail to get data.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })


//                    } else {
//                        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
//                        builder.setTitle("Please verify your email")
//                        builder.setPositiveButton(
//                            "OK"
//                        ) { dialog, which -> dialog.cancel() }
//                        val dialog: Dialog = builder.create()
//                        dialog.show()
//                    }
                } else {

                    Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}