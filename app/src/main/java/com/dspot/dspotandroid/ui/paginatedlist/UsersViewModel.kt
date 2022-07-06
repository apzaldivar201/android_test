package com.dspot.dspotandroid.ui.paginatedlist

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dspot.dspotandroid.data.network.ApiInterface
import com.dspot.dspotandroid.data.paging.UserPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel

@Inject
constructor(
    private val apiService: ApiInterface,
) : ViewModel() {

    val hasError = MutableLiveData(false)

    val listData = Pager(PagingConfig(pageSize = 1)) {
        UserPagingSource(apiService, hasError)
    }.flow.cachedIn(viewModelScope)



}
























