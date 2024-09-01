package br.com.leandro.podcast.model

import com.google.gson.annotations.SerializedName

data class Rating (

    @SerializedName("scheme") val scheme : String,
    @SerializedName("value") val value : String
)