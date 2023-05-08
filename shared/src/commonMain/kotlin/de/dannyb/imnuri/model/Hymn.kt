package de.dannyb.imnuri.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hymn(
    @SerialName("numar") val number: Int,
    @SerialName("titlu") val title: String,
    @SerialName("categorie") val category: String,
    @SerialName("gama") val key: String?,
    @SerialName("strofe") val stanzas: List<String>,
    @SerialName("mp3") val hasMp3: Boolean,
    @SerialName("sheet") val hasSheet: Boolean,
)

@Serializable
data class Hymns(
    @SerialName("imnuri") val hymns: List<Hymn>
)