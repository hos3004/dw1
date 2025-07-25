package com.example.daawahtv.network

/** Response structure for single episode details */
data class EpisodeDetailsResponse(
    val status: Boolean,
    val data: EpisodeData
)

data class EpisodeData(
    val url_link: String
)
