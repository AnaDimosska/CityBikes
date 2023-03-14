package com.example.citybikes.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.citybikes.R
import com.example.citybikes.databinding.FragmentCityBikesDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class CityBikesDetailsFragment : Fragment() {

    private val args: CityBikesDetailsFragmentArgs by navArgs()

    private var _binding: FragmentCityBikesDetailsBinding? = null
    private val binding by lazy { _binding!! }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityBikesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val bouncedBuilder = LatLngBounds.builder()
        val callback = OnMapReadyCallback { googleMap ->
            val city = LatLng(args.lat.toDouble(), args.lon.toDouble())
            bouncedBuilder.include(city)
            val marker =
                googleMap.addMarker(MarkerOptions().position(city).title("Marker in Sydney"))
            googleMap.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bouncedBuilder.build(),
                    1000,
                    1000,
                    0
                )
            )
            googleMap.setOnMarkerClickListener { clickedMarker ->
                if (clickedMarker == marker) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("${args.companyName} Marker Clicked")
                    builder.setMessage("You clicked on the marker in ${args.companyCity} ${args.companyCountryCode}")
                    builder.setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()
                }
                true

            }
        }
        mapFragment?.getMapAsync(callback)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val direction = CityBikesDetailsFragmentDirections.actionCityBikesDetailsFragmentToCityBikesFragment()
                findNavController().navigate(direction)
            }

        })
    }
}