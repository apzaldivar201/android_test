package com.dspot.dspotandroid.ui.finitelist

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dspot.dspotandroid.data.model.Result
import com.dspot.dspotandroid.databinding.ActivityFiniteUsersBinding
import com.dspot.dspotandroid.util.Functions
import com.dspot.dspotandroid.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FiniteUsersActivity : AppCompatActivity(), FiniteListAdapter.UserItemListener {

    private lateinit var binding: ActivityFiniteUsersBinding
    private lateinit var userAdapter: FiniteListAdapter
    private val viewModel: FiniteUsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSystemBar()

        binding = ActivityFiniteUsersBinding.inflate(layoutInflater)
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
        userAdapter = FiniteListAdapter(this)

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
        viewModel.users.observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.layoutError.visibility = View.GONE
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    if (!it.data?.results.isNullOrEmpty()) userAdapter.setItems(it.data!!.results)
                }

                Resource.Status.ERROR -> {
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.visibility = View.GONE
                    binding.errorMsg.text = it.message
                    binding.layoutError.visibility = View.VISIBLE
                }

                Resource.Status.LOADING -> {
                    binding.layoutError.visibility = View.GONE
                    binding.shimmerViewContainer.visibility = View.VISIBLE
                    binding.shimmerViewContainer.startShimmer()
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

    override fun onClickedUser(item: Result) {
        Timber.d(item.name.first)
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