package com.dspot.dspotandroid.data.repository

import com.dspot.dspotandroid.data.network.UserRemoteDataSource
import com.dspot.dspotandroid.util.performGetOperation
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
) {
    fun getUsers() = performGetOperation(
        networkCall = { remoteDataSource.getUsers() },
    )

    fun getUsersPaginated(page: Int, results: Int) = performGetOperation(
        networkCall = { remoteDataSource.getUsersPaging(page, results) },
    )
}