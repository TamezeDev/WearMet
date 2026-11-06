package com.zekiee.wearmet.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val countryCode: String,
    val region: String?
) {
    val flag: String? get() = countryCode.toFlagEmoji()
}

private fun String.toFlagEmoji(): String? {
    if (length != 2 || !all { it.isLetter() }) return null
    return uppercase().map { String(Character.toChars(0x1F1E6 + (it - 'A'))) }.joinToString("")
}


