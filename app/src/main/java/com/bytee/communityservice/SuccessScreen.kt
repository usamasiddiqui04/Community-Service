package com.bytee.communityservice

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.module.DashBoardActivity
import com.bytee.communityservice.module.FormActivity

class SuccessScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep((2 * 3000).toLong())
                    findNavController().popBackStack()



                } catch (e: Exception) {

                }
            }
        }
        background.start()
    }


}