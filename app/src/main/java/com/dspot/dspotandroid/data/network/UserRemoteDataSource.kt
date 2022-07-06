package com.dspot.dspotandroid.data.network

import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService: ApiInterface
) : BaseDataSource() {
    suspend fun getUsers() = getResult { apiService.getFiftyUsers(50) }

    suspend fun getUsersPaging(page: Int, results: Int) =
        getResult { apiService.getAllUsersPaging(page, results) }
}