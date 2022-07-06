package com.dspot.dspotandroid.data.network

import com.dspot.dspotandroid.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("?")
    suspend fun getAllUsersPaging(
        @Query("page") page: Int,
        @Query("results") results: Int,
    ): Response<ApiResponse>


    @GET("?")
    suspend fun getFiftyUsers(
        @Query("results") page: Int,
    ): Response<ApiResponse>
}