package com.dspot.dspotandroid.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApiResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: ArrayList<Result>
) : Parcelable