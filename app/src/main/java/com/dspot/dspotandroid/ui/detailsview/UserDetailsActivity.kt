package com.dspot.dspotandroid.ui.detailsview

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dspot.dspotandroid.data.model.Result
import com.dspot.dspotandroid.databinding.ActivityUserDetailsBinding
import com.dspot.dspotandroid.util.Functions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSystemBar()

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user: Result? = intent.getParcelableExtra("USER")
        listeners()
        setupViews(user)
    }

    private fun listeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.tvMap.setOnClickListener {
            //TODO go to map fragment
        }
    }

    private fun setupViews(user: Result?) {
        //profile image
        Glide.with(binding.root.context)
            .load(user?.picture?.large)
            .transform(CircleCrop())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.profileImage)

        //textviews
        binding.tvFullName.text = "${user!!.name.title} ${user!!.name.first} ${user!!.name.last}"
        binding.tvCell.text = user.cell
        binding.tvEmail.text = user.email
        binding.tvAddress.text =
            "${user.location.street.name}, ${user.location.street.number}, ${user.location.city}. ${user.location.state}. ${user.location.postcode}"

        binding.tvNationality.text = user.nat
        binding.tvPhone.text = user.phone
        binding.tvDob.text = user.dob.date
        binding.tvUsername.text = user.login.username
    }

    private fun handleSystemBar() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        Functions.setWindowFlag(
            this,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            false
        )

        window.statusBarColor = Color.TRANSPARENT
        Functions.setSystemBarLight(this)
    }
}