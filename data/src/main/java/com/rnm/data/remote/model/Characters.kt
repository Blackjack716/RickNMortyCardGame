package com.rnm.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


data class Characters(
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("status")
    @Expose
    val status: String? = null,

    @SerializedName("image")
    @Expose
    val image: String? = null
)

data class CharactersDto(
    @SerializedName("info")
    @Expose
    var info: CharactersInfo? = null,

    @SerializedName("results")
    @Expose
    val results: List<Characters> = emptyList(),
)

@Serializable
data class CharactersInfo(

    @SerializedName("count")
    @Expose
    val count: Int? = null,

    @SerializedName("pages")
    @Expose
    val pages: Int? = null,

    @SerializedName("next")
    @Expose
    val next: String? = null,

    @SerializedName("prev")
    @Expose
    val prev: String? = null
)
