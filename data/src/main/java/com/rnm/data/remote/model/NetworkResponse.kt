package com.rnm.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorResponse {
    @SerializedName("Message")
    @Expose
    val message: String? = null
}