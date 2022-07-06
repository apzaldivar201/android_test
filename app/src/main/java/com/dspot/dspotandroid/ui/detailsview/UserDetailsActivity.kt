package com.dspot.dspotandroid.ui.detailsview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dspot.dspotandroid.R
import com.dspot.dspotandroid.data.model.Result
import com.dspot.dspotandroid.databinding.ActivityUserDetailsBinding
import com.dspot.dspotandroid.util.Functions.Companion.formatDate
import com.dspot.dspotandroid.util.Functions.Companion.handleSystemBar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var user: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSystemBar(window, this)

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        user = intent.getParcelableExtra("USER")!!
        listeners()
        setupViews(user)
    }

    private fun listeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupViews(user: Result?) {
        //profile image
        Glide.with(binding.root.context)
            .load(user?.picture?.large)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.profileImage)

        //textviews
        binding.tvFullName.text = user?.name?.fullName() ?: ""
        binding.tvCell.text = user?.cell ?: ""
        binding.tvEmail.text = user?.email ?: ""
        binding.tvAddress.text = user?.location?.fullAddress() ?: ""

        binding.tvNationality.text = user?.nat ?: ""
        binding.tvPhone.text = user?.phone ?: ""
        binding.tvDob.text = formatDate(user?.dob?.date ?: "")
        binding.tvUsername.text = user?.login?.username ?: ""
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val coordinates = user.location.coordinates
        Log.wtf("MARKER", "${coordinates.latitude},${coordinates.longitude}")
        val marker = LatLng(coordinates.latitude.toDouble(), coordinates.longitude.toDouble())

        try {
            googleMap.addMarker(
                MarkerOptions()
                    .position(marker)
                    .title("${user.name.title} ${user.name.first}")
            )
        } catch (e: Exception) {
            Log.wtf("Marker error", e.message)
        }

        val cameraPosition = CameraPosition.builder()
            .target(marker)
            .zoom(11f)
            .bearing(90f)
            .tilt(30f)
            .build()

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}