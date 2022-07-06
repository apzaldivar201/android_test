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
}