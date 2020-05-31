package com.example.android.kotlinrecyclerview.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.kotlinrecyclerview.databinding.FragmentDetailBinding
import com.example.android.kotlinrecyclerview.entity.SectionDetails
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDetailBinding.inflate(inflater)
        val arguments = DetailFragmentArgs.fromBundle(arguments!!)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = DetailFragmentViewModelFactory(arguments.section, application)
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailFragmentViewModel::class.java)



        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToMainFragment())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        binding.viewmodel = viewModel

        viewModel.sectionDetailFromDB.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.sectionDetail = it
            }
        })


        // Add an Observer to the state variable for Navigating when a back button is clicked.
        /*viewModel.navigateToMainSections.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                *//*this.findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToMainFragment())*//*
                // Reset state to make sure we only navigate once, even if the device
                // has a configuration change.
                viewModel.doneNavigating()
            }
        })*/

        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}