package org.laziskhu.amilkhu.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class BaseResponse(
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "status")
    val status: Boolean = false
) : Parcelable