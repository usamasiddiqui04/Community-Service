package com.bytee.communityservice.ui.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bytee.communityservice.R
import com.bytee.communityservice.databinding.DashBoardScreenBinding
import com.bytee.communityservice.model.Handicap
import com.bytee.communityservice.module.RegistrationActivity
import com.bytee.communityservice.utils.Prefs
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
            auth.signOut()
            startActivity(Intent(requireContext(), RegistrationActivity::class.java))
            activity?.finish()
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

        val listCarouselDashboard = mutableListOf<CarouselItem>()
        // Image drawable with caption
        listCarouselDashboard.add(
            CarouselItem(
                imageDrawable = R.drawable.scap,
                caption = "Assist Handicapped"
            )
        )
        listCarouselDashboard.add(
            CarouselItem(
                imageDrawable = R.drawable.edrive,
                caption = "Environmental Drives"
            )
        )
        listCarouselDashboard.add(
            CarouselItem(
                imageDrawable = R.drawable.donateblood,
                caption = "Blood Donation"
            )
        )
        listCarouselDashboard.add(
            CarouselItem(
                imageDrawable = R.drawable.orphans,
                caption = "Assist Orphans"
            )
        )
        listCarouselDashboard.add(
            CarouselItem(
                imageDrawable = R.drawable.smeal,
                caption = "Share A Meal"
            )
        )

        binding.carouselDashboardItems.setData(listCarouselDashboard)

        // Carousel listener
        binding.carouselDashboardItems.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                when (position) {
                    0 -> findNavController().navigate(R.id.handiCappedDashboardFragment)
                    1 -> findNavController().navigate(R.id.assistsOrphanListFragment)
                    2 -> findNavController().navigate(R.id.bloodDonationDashboardFragment)
                    3 -> findNavController().navigate(R.id.environmentalDriveDashboardFragment)
                    4 -> findNavController().navigate(R.id.shareAMealFragment)
                    else -> {
                        findNavController().navigate(R.id.handiCappedDashboardFragment)
                    }
                }
            }
        }


//        binding.cvBloodDrive.setOnClickListener {
//            findNavController().navigate(R.id.donateBloodListFragment)
//        }
//
//        binding.cvFoodDrive.setOnClickListener {
//            findNavController().navigate(R.id.joinFoodDriveListFragment)
//        }
//
//        binding.cvHandyCapped.setOnClickListener {
//            findNavController().navigate(R.id.joinHandyCappedEventListFragment)
//        }
//
//        binding.cvOrphansDrive.setOnClickListener {
//            findNavController().navigate(R.id.assistsOrphanListFragment)
//        }
//
//        binding.cvPlantationDrive.setOnClickListener {
//            findNavController().navigate(R.id.plantationDriveListFragment)
//        }

//
//        binding.llAssistHandyCapped.setOnClickListener {
//            findNavController().navigate(R.id.handiCappedDashboardFragment)
//        }
//        binding.llAssistOrphanage.setOnClickListener {
//            findNavController().navigate(R.id.assistsOrphanListFragment)
//        }
//        binding.llDonateBlood.setOnClickListener {
//            findNavController().navigate(R.id.bloodDonationDashboardFragment)
//        }
//        binding.llEnvironmental.setOnClickListener {
//            findNavController().navigate(R.id.environmentalDriveDashboardFragment)
//        }
//        binding.llShareAMeal.setOnClickListener {
//            findNavController().navigate(R.id.shareAMealFragment)
//        }

    }

}