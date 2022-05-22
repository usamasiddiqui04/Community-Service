package com.bytee.communityservice.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bytee.communityservice.adapter.CustomAdapter
import com.bytee.communityservice.databinding.FragmentJoinHandyCappedEventListBinding

class JoinHandyCappedEventListFragment : Fragment() {
    lateinit var _binding: FragmentJoinHandyCappedEventListBinding
    private val binding get() = _binding

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


        //crating an arraylist to store users using the data class user
        val titleList = ArrayList<String>()

        //adding some dummy data to the list
        titleList.add("Belal Khan")
        titleList.add("Ramiz Khan")
        titleList.add("Faiz Khan")
        titleList.add("Yashar Khan")

        //creating our adapter
        val adapter = CustomAdapter(titleList)

        //now adding the adapter to recyclerview
        binding.rvMain.adapter = adapter

        binding.ivBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}