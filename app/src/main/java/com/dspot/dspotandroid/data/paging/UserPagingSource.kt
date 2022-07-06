package com.dspot.dspotandroid.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dspot.dspotandroid.data.model.Result
import com.dspot.dspotandroid.data.network.ApiInterface
import com.dspot.dspotandroid.util.Constants.PAGE_CANT
import com.dspot.dspotandroid.util.ResourcePaging

class UserPagingSource(
    private val apiService: ApiInterface,
    private val status: MutableLiveData<ResourcePaging>
) : PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, Result> {

        return try {
            val currentPage = params.key ?: 1

            if (currentPage == 1) {
                status.postValue(ResourcePaging("LOADING", ""))
            } else {
                status.postValue(ResourcePaging("LOADING_MORE", ""))
            }

            val response = apiService.getAllUsersPaging(currentPage, PAGE_CANT)
            val responseData = mutableListOf<Result>()
            val data = response.body()?.results ?: emptyList()
            responseData.addAll(data)
            status.postValue(ResourcePaging("SUCCESS", ""))
            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            status.postValue(ResourcePaging("ERROR", e.message))
            LoadResult.Error(e)
        }
    }
}