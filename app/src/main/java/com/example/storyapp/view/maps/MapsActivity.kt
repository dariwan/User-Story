package com.example.storyapp.view.maps

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.storyapp.R
import com.example.storyapp.data.AllStoryResponse
import com.example.storyapp.databinding.ActivityMapsBinding
import com.example.storyapp.utils.SesionManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var sharedPref: SesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        sharedPref = SesionManager(this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true


        getMyLocation()
        getStoriesWithMaps(googleMap)

    }


    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        isGranted: Boolean ->
        if (isGranted){
            getMyLocation()
        }
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mMap.isMyLocationEnabled = true
        } else{
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun addManyMarkerLocation( googleMap: GoogleMap, listStories: List<AllStoryResponse.ListStory>){
       listStories.forEach{
           listStoryMap ->
           val markerMap = MarkerOptions()
           markerMap.position(LatLng(listStoryMap.lat, listStoryMap.lon))
               .title(listStoryMap.name)

           val marker = googleMap.addMarker(markerMap)
           marker?.tag = listStoryMap
       }
    }


    private fun getStoriesWithMaps(googleMap: GoogleMap) {
        val  mapsViewModel: MapsViewModel by viewModels {
            MapsViewModel.ViewModelFactory(this)
        }

        mapsViewModel.getStoryWithLocation().observe(this){
            when(it){
                is com.example.storyapp.utils.Result.Success -> {
                    addManyMarkerLocation(googleMap, it.data)
                }

                is com.example.storyapp.utils.Result.Loading -> {}

                is com.example.storyapp.utils.Result.Error ->{}
            }
        }

    }

}