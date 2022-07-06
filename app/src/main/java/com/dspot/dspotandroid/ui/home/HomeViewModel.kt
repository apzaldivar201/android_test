package com.dspot.dspotandroid.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dspot.dspotandroid.data.dummy.DashboardItemsDataSource.Companion.getAllDashboardItems
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {
    val homeItems = liveData(Dispatchers.IO) {
        emit(
            getAllDashboardItems()
        )
    }
}
