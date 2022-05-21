package com.bytee.communityservice.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.FragmentSplahScreenBinding


@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {


    lateinit var _binding: FragmentSplahScreenBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplahScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep((2 * 1000).toLong())
                    findNavController().navigate(R.id.action_splahScreen_to_loginScreen)

                } catch (e: Exception) {

                }
            }
        }
        background.start()


    }

}