package com.bytee.communityservice.ui.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.R
import com.bytee.communityservice.utils.Prefs

class Form : Fragment() {


    lateinit var prefs: Prefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = Prefs(requireContext())

        if(prefs.type.equals("Blood")){
            findNavController().navigate(R.id.action_form_to_bloodForm)
        }else if (prefs.type.equals("Handicap")){
            findNavController().navigate(R.id.action_form_to_handicapForm)
        }else if(prefs.type.equals("Orphans")){
            findNavController().navigate(R.id.action_form_to_orphangeForm)
        }else if (prefs.type.equals("Share a meal")){
            findNavController().navigate(R.id.action_form_to_shareMealForm)
        }
    }

}