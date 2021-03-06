package com.bytee.communityservice.ui.screens

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.databinding.FragmentSingupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.regex.Pattern


class SignupFragment : Fragment() {

    private lateinit var _binding: FragmentSingupBinding
    private val binding get() = _binding
    lateinit var email: String
    lateinit var password: String
    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var fullName: String
    lateinit var phoneNumber: String
    lateinit var confirmPassword: String
    var validationPass = false

    private var acountType =
        arrayOf("Normal", "Handicap", "Orphans", "Blood", "Environmental", "Share a meal")


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


        val adapter = ArrayAdapter(requireContext(), com.bytee.communityservice.R.layout.spinner_item, acountType)

        adapter.setDropDownViewResource(com.bytee.communityservice.R.layout.spinner_item)

        binding.spinnerType.adapter = adapter

        binding.singupTextView.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.buttonLogin.setOnClickListener {
            email = binding.emailEditText.text.toString()
            password = binding.passWordEditText.text.toString()
            phoneNumber = binding.phoneNumberEditText.text.toString()
            fullName = binding.nameEditText.text.toString()
            confirmPassword = binding.confirmPassWordEditText.text.toString()

            if (checkValidation()) {
                firebaseLogin()
            }


        }
    }

    private fun isLetters(string: String): Boolean {
        return string.filter { it in 'A'..'Z' || it in 'a'..'z'|| it in " " }.length == string.length
    }

    private fun checkValidation(): Boolean {

        var boolean = true

        if (fullName.isEmpty()) {
            binding.nameTextInputLayout.helperText = "this is a required number"
            boolean = false
        } else {
            binding.nameTextInputLayout.helperText = ""
        }

        if (email.isEmpty()) {
            binding.emailTextInputLayout.helperText = "this is a required number"
            boolean = false
        } else {
            binding.emailTextInputLayout.helperText = ""
        }

        if (phoneNumber.isEmpty()) {
            binding.phoneNumberTextInputLayout.helperText = "this is a required filed"
            boolean = false
        } else {
            binding.phoneNumberTextInputLayout.helperText = ""
        }

        if(fullName.isNotEmpty()){
            if(!isLetters(fullName)){
                binding.nameTextInputLayout.helperText = "name should contain alphabets only"
                boolean = false
            }else{
                binding.nameTextInputLayout.helperText = ""
            }
        }


        if(!checkEmail(email)){
            binding.emailTextInputLayout.helperText = "Invalid email"
            boolean = false
        }else{
            binding.emailTextInputLayout.helperText = ""
        }

        if (phoneNumber.length < 10) {
            binding.phoneNumberTextInputLayout.helperText = "invalid phone number"
            boolean = false
        } else {
            binding.phoneNumberTextInputLayout.helperText = ""
        }

        if (password.isEmpty()) {
            binding.passwordTextInputLayout.helperText = "this is a required filed"
            boolean = false
        } else {
            binding.passwordTextInputLayout.helperText = ""
        }

        if (password.length < 8) {
            binding.passwordTextInputLayout.helperText =
                "password length should be greater than 8 characters"
            boolean = false
        } else {
            binding.passwordTextInputLayout.helperText = ""
        }

        if(password.isNotEmpty()){
            if(!isValidPassword(password)){
                binding.passwordTextInputLayout.helperText =
                    "password should contain special character number and alphabets"
                boolean = false
            }else{
                binding.passwordTextInputLayout.helperText = ""
            }
        }


        if(confirmPassword.isNotEmpty()){
            if(!isValidPassword(confirmPassword)){
                binding.confirmPasswordTextInputLayout.helperText =
                    "password should contain special character number and alphabets"
                boolean = false
            }else{
                binding.confirmPasswordTextInputLayout.helperText = ""
            }
        }

        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordTextInputLayout.helperText = "this is a required filed"
            boolean = false
        } else {
            binding.confirmPasswordTextInputLayout.helperText = ""
        }

        if (confirmPassword.length < 8) {
            binding.confirmPasswordTextInputLayout.helperText =
                "password length should be greater than 8 characters"
            boolean = false
        } else {
            binding.confirmPasswordTextInputLayout.helperText = ""
        }

        if (!password.equals(confirmPassword)) {
            binding.confirmPasswordTextInputLayout.helperText =
                "password not match please try again"
            boolean = false
        } else {
            binding.confirmPasswordTextInputLayout.helperText = ""
        }

        return boolean
    }


    private fun checkEmail(email: String): Boolean {
        return emailAddressPattern.matcher(email).matches()
    }

    private val emailAddressPattern: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private fun isValidPassword(password: String?) : Boolean {
        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
            val passwordMatcher = Regex(passwordPattern)

            return passwordMatcher.find(password) != null
        } ?: return false
    }

    private fun firebaseLogin() {
        binding.progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                requireActivity()
            ) { task ->
                if (!task.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(), task.exception.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    auth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("Register successfully , verification link sent to your given email $email")
                        builder.setPositiveButton(
                            "OK"
                        ) { dialog, which ->
                            findNavController().popBackStack()
                            dialog.cancel()
                        }
                        val dialog: Dialog = builder.create()
                        dialog.show()
                    }

                    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val hashMap: HashMap<String, String> = HashMap()

                            hashMap["name"] = binding.nameEditText.text.toString()
                            hashMap["email"] = email
                            hashMap["phoneNumber"] = binding.phoneNumberEditText.text.toString()
                            hashMap["id"] = auth.currentUser!!.uid
                            hashMap["accountType"] = binding.spinnerType.selectedItem.toString()

                            databaseReference.child(auth.currentUser!!.uid).setValue(hashMap)

                        }

                        override fun onCancelled(error: DatabaseError) {
                            Toast.makeText(
                                requireContext(),
                                "$error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

                    binding.progressBar.visibility = View.GONE

                }
            }

    }


}