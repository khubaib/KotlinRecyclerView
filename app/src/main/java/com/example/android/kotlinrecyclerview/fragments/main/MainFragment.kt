package com.example.android.kotlinrecyclerview.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.kotlinrecyclerview.databinding.FragmentMainBinding

class MainFragment : Fragment() {


    lateinit var viewModel : MainFragmentViewModel

    lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Use databinding to inflate the layout
        binding = FragmentMainBinding.inflate(inflater)

       /* val application = requireNotNull(this.activity).application

        val viewModelFactory = MainFragmentViewModelFactory(application)

        // Get a reference to the ViewModel associated with this fragment.
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainFragmentViewModel::class.java)*/

        val application = requireNotNull(this.activity).application

        val viewModelFactory = MainFragmentViewModelFactory(application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainFragmentViewModel::class.java)


        binding.viewmodel = viewModel

        val adapter = MainFragmentAdapter(SectionCLickListener { sectionID ->
            viewModel.onSectionClicked(sectionID)
            //Toast.makeText(context,"${sectionID}",Toast.LENGTH_LONG).show()
        })


        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })





        // Add an Observer on the state variable for Navigating when and item is clicked.
        viewModel.navigateToSectionDetails.observe(viewLifecycleOwner, Observer { section ->
            section?.let {

                this.findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(section)
                )

                viewModel.onSectionDetailNavigated();
            }
        })

        binding.recyclerView.adapter = adapter


        viewModel.sections.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.setLifecycleOwner(this)


        //easily switch between LinearLayoutManager and a GridLayoutManager
        //binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val manager = GridLayoutManager(activity, 3)
        binding.recyclerView.layoutManager = manager
        return binding.root
    }

    /**
     * Method for displaying a Toast error message for network errors.
     */
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            binding.errorText.visibility = View.VISIBLE
            viewModel.onNetworkErrorShown()
        }
    }

}