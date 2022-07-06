package com.dspot.dspotandroid.ui.paginatedlist

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dspot.dspotandroid.databinding.ActivityPaginatedListBinding
import com.dspot.dspotandroid.util.Functions
import com.dspot.dspotandroid.util.ResourceLive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaginatedListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaginatedListBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSystemBar()

        binding = ActivityPaginatedListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadData()
        listeners()
    }

    private fun listeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()

        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(
                binding.root.context
            )
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            setHasFixedSize(true)
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.listData.collect {
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.visibility = View.GONE
                userAdapter.submitData(it)
            }
        }

        viewModel.status.observe(this) {
            val statusA: ResourceLive = it
            when (statusA.status) {
                "LOADING" -> {
                    binding.shimmerViewContainer.startShimmer()
                    binding.shimmerViewContainer.visibility = View.VISIBLE
                    binding.progressHorizontal.visibility = View.GONE
                    Log.wtf("LOADING", "LOADING")
                }
                "LOADING_MORE" -> {
                    binding.progressHorizontal.visibility = View.VISIBLE
                    Log.wtf("LOADING_MORE", "LOADING_MORE")
                }
                "SUCCESS" -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.progressHorizontal.visibility = View.GONE
                    binding.layoutError.visibility = View.GONE
                    Log.wtf("SUCCESS", "SUCCESS")
                }
                "ERROR" -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.layoutError.visibility = View.VISIBLE
                    binding.progressHorizontal.visibility = View.GONE
                    binding.errorMsg.text = statusA.message
                    Log.wtf("ERROR", statusA.message)
                }
            }
        }
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


    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmer()
    }

    override fun onPause() {
        binding.shimmerViewContainer.stopShimmer()
        super.onPause()
    }
}