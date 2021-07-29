package org.laziskhu.amilkhu.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class GetHistoryAttendanceResponse(
    @Json(name = "data")
    val data: List<HistoryAttendance>? = null,
    @Json(name = "status")
    val status: Boolean? = null
) : Parcelable