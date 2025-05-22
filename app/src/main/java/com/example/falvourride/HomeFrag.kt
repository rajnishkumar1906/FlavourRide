package com.example.falvourride

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*  // For UUID and Locale

class HomeFrag : Fragment() {

    private val orderViewModel: OrderViewModel by activityViewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Permission launcher
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            fetchLocation()
        } else {
            view?.findViewById<TextView>(R.id.location)?.text = "Permission denied"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Request permission
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            fetchLocation()
        } else {
            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // RecyclerView setup
        val recyclerView = view.findViewById<RecyclerView>(R.id.foods)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val foodList = listOf(
            FoodItem("Pizza", R.drawable.pizza, 199.0),
            FoodItem("Burger", R.drawable.burger, 149.0),
            FoodItem("Cold Drink", R.drawable.cold_drink, 59.0),
            FoodItem("Sandwich", R.drawable.sandwich, 129.0)
        )


        val adapter = FoodAdapter(foodList) { foodItem ->
            val newOrder = Order(
                orderId = UUID.randomUUID().toString().substring(0, 8),
                itemName = foodItem.name,
                status = "Pending",
                price = foodItem.price,
                imageResId = foodItem.imageResId
            )
            orderViewModel.addOrder(newOrder)
        }


        recyclerView.adapter = adapter
    }

    // Fetch current location and update UI
    private fun fetchLocation() {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    val locationTextView = view?.findViewById<TextView>(R.id.location)
                    if (location != null) {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        try {
                            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                            if (addresses != null && addresses.isNotEmpty()) {
                                val address = addresses[0]
                                val district = address.subAdminArea ?: "Unknown District"
                                val state = address.adminArea ?: "Unknown State"
                                locationTextView?.text = "$district, $state"
                            } else {
                                locationTextView?.text = "Location unavailable"
                            }
                        } catch (e: Exception) {
                            locationTextView?.text = "Geocoder error"
                        }
                    } else {
                        locationTextView?.text = "Location unavailable"
                    }
                }
        } catch (e: SecurityException) {
            view?.findViewById<TextView>(R.id.location)?.text = "Location error"
        }
    }
}
