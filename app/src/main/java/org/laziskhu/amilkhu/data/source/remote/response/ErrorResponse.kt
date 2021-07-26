package org.laziskhu.amilkhu.data.source.remote.response

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ErrorResponse(
  @Json(name = "status")
  val status: Boolean,
  @Json(name = "message")
  val message: String?
)