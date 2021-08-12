package org.laziskhu.amilkhu.data.source.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class GetPaymentGatewayResponse(
    @Json(name = "status")
    val status: Boolean? = null,
    @Json(name = "data")
    val data: List<Rekening?> = emptyList()
) : Parcelable {
    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Rekening(
        @Json(name = "id_rekening")
        val idRekening: String? = null,
        @Json(name = "bank_name")
        val bankName: String? = null,
        @Json(name = "bank_code")
        val bankCode: String? = null,
        @Json(name = "rekening_name")
        val rekeningName: String? = null,
        @Json(name = "rekening_number")
        val rekeningNumber: String? = null,
        @Json(name = "logo")
        val logo: String? = null,
        @Json(name = "isQRIS")
        val isQRIS: String? = null,
        @Json(name = "created_at")
        val createdAt: String? = null,
        @Json(name = "updated_at")
        val updatedAt: String? = null
    ) : Parcelable
}