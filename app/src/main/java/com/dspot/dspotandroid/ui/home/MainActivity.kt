package com.dspot.dspotandroid.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dspot.dspotandroid.data.model.DashboardItem
import com.dspot.dspotandroid.databinding.ActivityMainBinding
import com.dspot.dspotandroid.ui.finitelist.FiniteUsersActivity
import com.dspot.dspotandroid.ui.paginatedlist.PaginatedListActivity
import com.dspot.dspotandroid.util.Functions.Companion.setSystemBarLight
import com.dspot.dspotandroid.util.Functions.Companion.setWindowFlag
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), HomeAdapter.HomeItemListener {

    private lateinit var binding: ActivityMainBinding
    /* private lateinit var userAdapter: UserAdapter
     private val viewModel: UsersViewModel by viewModels()*/
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleSystemBar()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter(this)
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.homeItems.observe(this) {
            if (!it.isNullOrEmpty()) adapter.setItems(ArrayList(it))
        }
    }

    override fun onClickedItem(item: DashboardItem) {
        when (item.title) {
            "50 users list" -> {
                startActivity(Intent(binding.root.context, FiniteUsersActivity::class.java))
            }

            "Paginated list" -> {
                startActivity(Intent(binding.root.context, PaginatedListActivity::class.java))
            }
        }
    }


    private fun handleSystemBar() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        setWindowFlag(
            this,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            false
        )

        window.statusBarColor = Color.TRANSPARENT
        setSystemBarLight(this)
    }
}