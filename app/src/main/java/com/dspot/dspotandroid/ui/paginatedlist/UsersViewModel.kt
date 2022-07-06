package com.dspot.dspotandroid.ui.paginatedlist

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dspot.dspotandroid.data.network.ApiInterface
import com.dspot.dspotandroid.data.paging.UserPagingSource
import com.dspot.dspotandroid.util.ResourceLive
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel

@Inject
constructor(
    private val apiService: ApiInterface,
) : ViewModel() {

    val status = MutableLiveData(ResourceLive("",""))

    val listData = Pager(PagingConfig(pageSize = 1)) {
        UserPagingSource(apiService, status)
    }.flow.cachedIn(viewModelScope)
}
























