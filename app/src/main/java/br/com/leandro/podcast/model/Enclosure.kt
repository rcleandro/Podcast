package br.com.leandro.podcast.model

import android.media.Rating
import com.google.gson.annotations.SerializedName

data class Enclosure (
    @SerializedName("link") val link : String,
    @SerializedName("type") val type : String,
    @SerializedName("duration") val duration : Int,
    @SerializedName("rating") val rating : Rating
)