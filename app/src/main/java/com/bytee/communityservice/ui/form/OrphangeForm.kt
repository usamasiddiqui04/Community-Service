package com.bytee.communityservice.ui.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.FragmentHandicapFormBinding
import com.bytee.communityservice.databinding.FragmentOrphangeFormBinding


class OrphangeForm : Fragment() {

    lateinit var _binding: FragmentOrphangeFormBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orphange_form, container, false)
    }
}