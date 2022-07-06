package com.dspot.dspotandroid.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Registered(
    @SerializedName("age")
    val age: Int,
    @SerializedName("date")
    val date: String
) : Parcelable