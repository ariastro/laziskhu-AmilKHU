package org.laziskhu.amilkhu.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "data")
    val data: LoginData? = null,
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "status")
    val status: Boolean = false
) : Parcelable {
    @Parcelize
    @JsonClass(generateAdapter = true)
    data class LoginData(
        @Json(name = "email")
        val email: String = "",
        @Json(name = "name")
        val name: String = "",
        @Json(name = "role")
        val role: String = "",
        @Json(name = "token")
        val token: String = "",
        @Json(name = "user_id")
        val userId: String = "",
        @Json(name = "username")
        val username: String = ""
    ) : Parcelable
}