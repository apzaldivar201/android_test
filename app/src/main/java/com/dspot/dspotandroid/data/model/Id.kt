package com.dspot.dspotandroid.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Id(
    @SerializedName("name")
    val name: String?,
    @SerializedName("value")
    val value: String?
) : Parcelable