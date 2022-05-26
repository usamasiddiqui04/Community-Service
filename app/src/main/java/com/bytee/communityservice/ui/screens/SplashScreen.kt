package com.bytee.communityservice.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.FragmentSplahScreenBinding
import com.bytee.communityservice.module.DashBoardActivity
import com.bytee.communityservice.module.FormActivity
import com.bytee.communityservice.utils.Prefs
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {

    lateinit var _binding: FragmentSplahScreenBinding
    private val binding get() = _binding

    lateinit var auth: FirebaseAuth
    lateinit var prefs: Prefs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplahScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        prefs = Prefs(requireContext())

        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep((2 * 1000).toLong())

                    if (auth.currentUser?.uid == null) {
                        findNavController().navigate(R.id.action_splahScreen_to_loginScreen)
                    } else {

                        if (prefs.type.equals("Normal"))
                            startActivity(Intent(requireContext(), DashBoardActivity::class.java))
                        else
                            startActivity(Intent(requireContext(), DashBoardActivity::class.java))


                    }


                } catch (e: Exception) {

                }
            }
        }
        background.start()


    }


}