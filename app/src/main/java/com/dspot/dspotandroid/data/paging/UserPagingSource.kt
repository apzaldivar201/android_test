package com.dspot.dspotandroid.data.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dspot.dspotandroid.data.model.Result
import com.dspot.dspotandroid.data.network.ApiInterface
import com.dspot.dspotandroid.util.Constants.PAGE_CANT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserPagingSource(
    private val apiService: ApiInterface,
    private var hasError: MutableLiveData<Boolean>
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, Result> {

        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getAllUsersPaging(currentPage, PAGE_CANT)
            val responseData = mutableListOf<Result>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)
            hasError.postValue(false)
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            Log.wtf("Error", e.message)
            hasError.postValue(true)
            LoadResult.Error(e)
        }
    }
}