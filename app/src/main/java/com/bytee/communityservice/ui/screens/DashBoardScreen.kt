package com.bytee.communityservice.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.Gravity.LEFT
import android.view.Gravity.RIGHT
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.DashBoardScreenBinding
import com.bytee.communityservice.model.Handicap
import com.bytee.communityservice.module.RegistrationActivity
import com.bytee.communityservice.utils.Prefs
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class DashBoardScreen : Fragment() {


    lateinit var _binding: DashBoardScreenBinding
    private val binding get() = _binding
    private lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    val list = arrayListOf<Handicap>()
    lateinit var prefs: Prefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DashBoardScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        prefs = Prefs(requireContext())
        // below line is used to get reference for our database.
//        databaseReference = firebaseDatabase.reference.child("Blood")

//        getList()

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)

        binding.logout.setOnClickListener {
            binding.drawerLayout.openDrawer(LEFT)
//            auth.signOut()
//            startActivity(Intent(requireContext(), RegistrationActivity::class.java))
//            activity?.finish()
        }

        Log.d("TAG", "onViewCreated: ${list.size}")

        binding.userName.text = prefs.name
        binding.userEmail.text = prefs.email


        val listCarousel = mutableListOf<CarouselItem>()
        // Image drawable with caption
        listCarousel.add(
            CarouselItem(
                imageDrawable = R.drawable.blood_donation,
                caption = "Join Blood Donation Drives"
            )
        )
        listCarousel.add(
            CarouselItem(
                imageDrawable = R.drawable.doante_food,
                caption = "Join Food Donation Drives"
            )
        )
        listCarousel.add(
            CarouselItem(
                imageDrawable = R.drawable.handicapped,
                caption = "Join Handicapped Drives"
            )
        )
        listCarousel.add(
            CarouselItem(
                imageDrawable = R.drawable.orphans_1,
                caption = "Join Orphanage Drives"
            )
        )
        listCarousel.add(
            CarouselItem(
                imageDrawable = R.drawable.plantation_1,
                caption = "Join Plantation Drives"
            )
        )

        binding.carouselSlider.setData(listCarousel)

        // Carousel listener
        binding.carouselSlider.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                when (position) {
                    0 -> findNavController().navigate(R.id.donateBloodListFragment)
                    1 -> findNavController().navigate(R.id.joinFoodDriveListFragment)
                    2 -> findNavController().navigate(R.id.joinHandyCappedEventListFragment)
                    3 -> findNavController().navigate(R.id.assistsOrphanListFragment)
                    4 -> findNavController().navigate(R.id.plantationDriveListFragment)
                    else -> {
                        findNavController().navigate(R.id.donateBloodListFragment)
                    }
                }
            }
        }
        binding.nvView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_faqs -> {
                    binding.drawerLayout.closeDrawer(LEFT)
                    val url = "https://api.whatsapp.com/send?phone=${+923155254086}"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
//                    return true
                }

                R.id.item_discover -> {
                    binding.drawerLayout.closeDrawer(LEFT)
                    val url = "https://api.whatsapp.com/send?phone=${+923155254086}"
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
//                    return true
                }
                R.id.item_logout -> {
                    binding.drawerLayout.closeDrawer(LEFT)
                    auth.signOut()
                    startActivity(Intent(requireContext(), RegistrationActivity::class.java))
                    activity?.finish()
//                    return true
                }
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        binding.cvBlood.setOnClickListener {
            findNavController().navigate(R.id.bloodDonationDashboardFragment)
        }

        binding.cvSmeal.setOnClickListener {
            findNavController().navigate(R.id.shareAMealFragment)
        }

        binding.cvAcap.setOnClickListener {
            findNavController().navigate(R.id.handiCappedDashboardFragment)
        }

        binding.cvOrphans.setOnClickListener {
            findNavController().navigate(R.id.assistsOrphanListFragment)
        }

        binding.cvEdrive.setOnClickListener {
            findNavController().navigate(R.id.environmentalDriveDashboardFragment)
        }
        binding.tvDashboard.setOnClickListener {
//            findNavController().navigate(R.id.environmentalDriveDashboardFragment)
        }
        binding.tvWhatsapp.setOnClickListener {
            val url = "https://api.whatsapp.com/send?phone=${+923155254086}"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.itemId) {
            R.id.item_faqs -> {
                binding.drawerLayout.openDrawer(RIGHT)
                val url = "https://api.whatsapp.com/send?phone=${+923155254086}"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
                return true
            }

            R.id.item_discover -> {
                binding.drawerLayout.openDrawer(RIGHT)
                val url = "https://api.whatsapp.com/send?phone=${+923155254086}"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
                return true
            }
            R.id.item_logout -> {
                binding.drawerLayout.openDrawer(RIGHT)
                auth.signOut()
                startActivity(Intent(requireContext(), RegistrationActivity::class.java))
                activity?.finish()
                return true
            }
        }
        return true//super.onOptionsItemSelected(item)
    }

}