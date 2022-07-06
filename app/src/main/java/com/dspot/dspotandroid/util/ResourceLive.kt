package com.dspot.dspotandroid.util

data class ResourceLive(val status: String, val message: String?) {

    enum class Status {
        LOADING, LOADING_MORE, ERROR, SUCCESS
    }
}


