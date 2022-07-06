package com.dspot.dspotandroid.ui.paginatedlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dspot.dspotandroid.data.model.Result
import com.dspot.dspotandroid.databinding.ActivityPaginatedListBinding
import com.dspot.dspotandroid.ui.detailsview.UserDetailsActivity
import com.dspot.dspotandroid.util.Functions.Companion.handleSystemBar
import com.dspot.dspotandroid.util.ResourcePaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaginatedListActivity : AppCompatActivity(), UserAdapter.OnItemClickListener {

    private lateinit var binding: ActivityPaginatedListBinding
    private lateinit var userAdapter: UserAdapter
    private val viewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSystemBar(window, this)

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
        userAdapter = UserAdapter(this)

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
            val statusA: ResourcePaging = it
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

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmer()
    }

    override fun onPause() {
        binding.shimmerViewContainer.stopShimmer()
        super.onPause()
    }

    override fun onItemClick(view: View?, item: Result?, position: Int) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("USER", item)
        startActivity(intent)
    }
}