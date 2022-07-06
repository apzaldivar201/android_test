package com.dspot.dspotandroid.ui.finitelist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dspot.dspotandroid.data.model.Result
import com.dspot.dspotandroid.databinding.ActivityFiniteUsersBinding
import com.dspot.dspotandroid.ui.detailsview.UserDetailsActivity
import com.dspot.dspotandroid.util.Functions.Companion.handleSystemBar
import com.dspot.dspotandroid.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FiniteUsersActivity : AppCompatActivity(), FiniteListAdapter.UserItemListener {

    private lateinit var binding: ActivityFiniteUsersBinding
    private lateinit var userAdapter: FiniteListAdapter
    private val viewModel: FiniteUsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSystemBar(window, this)

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

    override fun onClickedUser(item: Result) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("USER", item)
        startActivity(intent)
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