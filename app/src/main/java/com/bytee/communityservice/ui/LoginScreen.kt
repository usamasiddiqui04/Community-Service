package com.bytee.communityservice.ui

import android.app.AlertDialog
import android.app.Dialog
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LoginScreen : Fragment() {


    private lateinit var _binding: FragmentLoginScreenBinding
    private val binding get() = _binding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var email: String
    lateinit var password: String

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


        binding.showPassword.setOnClickListener {
            binding.passWordEditText.transformationMethod = null
            binding.hidePassword.visibility = View.VISIBLE
            binding.showPassword.visibility = View.GONE
        }

        binding.hidePassword.setOnClickListener {
            binding.passWordEditText.transformationMethod = PasswordTransformationMethod()
            binding.hidePassword.visibility = View.GONE
            binding.showPassword.visibility = View.VISIBLE
        }


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
                    if (auth.currentUser!!.isEmailVerified) {

                        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                    } else {
                        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("Please verify your email")
                        builder.setPositiveButton(
                            "OK"
                        ) { dialog, which -> dialog.cancel() }
                        val dialog: Dialog = builder.create()
                        dialog.show()
                    }
                } else {

                    Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}