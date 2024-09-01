package br.com.leandro.podcast.model

import com.google.gson.annotations.SerializedName


data class ResponseClass(
    @SerializedName("status") val status : String,
    @SerializedName("feed") val feed : Feed,
    @SerializedName("items") val podcastList : List<Podcast>,
)