package com.example.citybikes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.citybikes.R
import com.example.citybikes.adapter.MyAdapter
import com.example.citybikes.databinding.FragmentCityBikesBinding
import com.example.citybikes.model.Network
import com.example.citybikes.util.MyDiffCallback
import com.example.citybikes.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityBikesFragment: Fragment() {
    private var _binding: FragmentCityBikesBinding? = null
    private val binding by lazy { _binding!! }
    private val viewModel by viewModels<CityBikesViewModel>()
    lateinit var bikesAdapter: MyAdapter
    private lateinit var differ: AsyncListDiffer<Network>
    var responseBike = mutableListOf<Network>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityBikesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bikesList.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    binding.paginationProgressBar.visibility = View.INVISIBLE
                    response.data?.let { bikesResponse ->
                        responseBike.addAll(bikesResponse)
                        bikesAdapter.differ.submitList(bikesResponse)
                    }
                }
                is Resource.Error -> {
                    binding.paginationProgressBar.visibility = View.INVISIBLE
                    response.message.let { message ->
                        Log.e("MainActivity", "there is error: $message")
                    }
                }

                is Resource.Loading -> {
                    binding.paginationProgressBar.visibility = View.VISIBLE
                }
            }
        })

        bikesAdapter = MyAdapter(object: MyAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                val companyName = responseBike[position].name
                val companyLocation = responseBike[position].location
                val direction = CityBikesFragmentDirections.actionCityBikesFragmentToCityBikesDetailsFragment(companyLocation.latitude.toFloat(),companyLocation.longitude.toFloat(),companyName,companyLocation.city,companyLocation.country)
                findNavController().navigate(direction)
            }

        })
        differ = AsyncListDiffer(bikesAdapter, MyDiffCallback())
        val linearLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.bikesRv.apply {
            adapter = bikesAdapter
            layoutManager = linearLayoutManager
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                bikesAdapter.getFilter().filter(newText)
                return true
            }
        })
    }

}