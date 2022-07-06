package com.dspot.dspotandroid.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Street(
    @SerializedName("name")
    val name: String,
    @SerializedName("number")
    val number: Int
) : Parcelable