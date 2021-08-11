package org.laziskhu.amilkhu.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import org.laziskhu.amilkhu.BuildConfig

@Parcelize
@JsonClass(generateAdapter = true)
data class GetWaitingAttendanceResponse(
    @Json(name = "status")
    val status: Boolean = false,
    @Json(name = "data")
    val data: List<WaitingAttendance>? = emptyList()
) : Parcelable {
    @Parcelize
    @JsonClass(generateAdapter = true)
    data class WaitingAttendance(
        @Json(name = "id_attendence")
        val idAttendence: String? = null,
        @Json(name = "user_id")
        val userId: String? = null,
        @Json(name = "coming_time")
        val comingTime: String? = null,
        @Json(name = "home_time")
        val homeTime: String? = null,
        @Json(name = "coming_latitude")
        val comingLatitude: String? = null,
        @Json(name = "coming_longitude")
        val comingLongitude: String? = null,
        @Json(name = "home_latitude")
        val homeLatitude: String? = null,
        @Json(name = "home_longitude")
        val homeLongitude: String? = null,
        @Json(name = "isInOffice")
        val isInOffice: String? = null,
        @Json(name = "photo")
        val photo: String? = null,
        @Json(name = "status")
        val status: String? = null,
        @Json(name = "notes")
        val notes: String? = null,
        @Json(name = "date")
        val date: String? = null,
        @Json(name = "created_at")
        val createdAt: String? = null,
        @Json(name = "updated_at")
        val updatedAt: String? = null,
        @Json(name = "username")
        val username: String? = null,
        @Json(name = "name")
        val name: String? = null,
        @Json(name = "role")
        val role: String? = null,
        @Json(name = "avatar")
        val avatar: String? = null,
        @Json(name = "gender")
        val gender: String? = null,
        @Json(name = "position")
        val position: String? = null
    ) : Parcelable {

        fun getAttendanceImage() = BuildConfig.IMAGE_URL + photo

    }
}