package com.dspot.dspotandroid.ui.paginatedlist

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                userAdapter.submitData(it)
            }
        }

        viewModel.hasError.observe(this) {
            Toast.makeText(binding.root.context, "Error loading data!!", LENGTH_SHORT)
                .show()
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
}