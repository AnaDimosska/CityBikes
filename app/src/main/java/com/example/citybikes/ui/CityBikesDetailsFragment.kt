package com.example.citybikes.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.citybikes.R
import com.example.citybikes.databinding.FragmentCityBikesDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class CityBikesDetailsFragment : Fragment() {

   // private val args: CityBikesDetailsFragmentArgs by navArgs()

    private var _binding: FragmentCityBikesDetailsBinding? = null
    private val binding by lazy { _binding!! }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityBikesDetailsBinding.inflate(inflater, container, false)
        val bundle = arguments
        //Here I am getting the lat and lon values from the bundle in the first fragment and they would be casted to double
        val lat = bundle?.getString("lat")
        val lon = bundle?.getString("lon")
        //val lat = bundle?.getString("company_name")
        //val lon = bundle?.getString("company_city")
        //val lon = bundle?.getString("company_country")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        val bouncedBuilder = LatLngBounds.builder()
        val callback = OnMapReadyCallback { googleMap ->
            val city = LatLng(33.8688,151.2093)
            bouncedBuilder.include(city)
            val marker = googleMap.addMarker(MarkerOptions().position(city).title("Marker in Sydney"))
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
                    // Create and display the AlertDialog
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Marker Clicked")
                    //Here instead of this message we would provide company name,country,city
                    builder.setMessage("You clicked on the marker in $city")
                    builder.setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                    builder.show()
                }
                true

            }
        }
        mapFragment?.getMapAsync(callback)
    }
}