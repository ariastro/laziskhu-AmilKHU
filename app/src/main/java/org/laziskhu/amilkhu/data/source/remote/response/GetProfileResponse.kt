package org.laziskhu.amilkhu.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import org.laziskhu.amilkhu.BuildConfig

@Parcelize
@JsonClass(generateAdapter = true)
data class GetProfileResponse(
    @Json(name = "data")
    val data: Profile? = null,
    @Json(name = "status")
    val status: Boolean? = null
) : Parcelable {
    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Profile(
        @Json(name = "address")
        val address: String? = null,
        @Json(name = "birthday")
        val birthday: String? = null,
        @Json(name = "created_at")
        val createdAt: String? = null,
        @Json(name = "email")
        val email: String? = null,
        @Json(name = "gender")
        val gender: String? = null,
        @Json(name = "name")
        val name: String? = null,
        @Json(name = "nik")
        val nik: String? = null,
        @Json(name = "phone")
        val phone: String? = null,
        @Json(name = "photo")
        val photo: String? = null,
        @Json(name = "position")
        val position: String? = null,
        @Json(name = "role")
        val role: String? = null,
        @Json(name = "updated_at")
        val updatedAt: String? = null,
        @Json(name = "user_id")
        val userId: String? = null,
        @Json(name = "username")
        val username: String? = null
    ) : Parcelable {
        fun getProfilePicture() = BuildConfig.IMAGE_URL + photo
    }
}