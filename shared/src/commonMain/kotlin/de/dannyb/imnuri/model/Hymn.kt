package de.dannyb.imnuri.model

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Hymn(
    @SerialName("numar") val number: Int,
    @SerialName("titlu") val title: String,
    @SerialName("categorie") val category: String,
    @SerialName("gama") val key: String?,
    @SerialName("strofe") val stanzas: List<String>,
    @SerialName("mp3") val hasMp3: Boolean,
    @SerialName("sheet") val hasSheet: Boolean,
) : Parcelable

@Serializable
@Parcelize
data class Hymns(
    @SerialName("imnuri") val hymns: List<Hymn>
) : Parcelable