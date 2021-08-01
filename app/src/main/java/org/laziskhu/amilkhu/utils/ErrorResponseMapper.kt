package org.laziskhu.amilkhu.utils

import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.squareup.moshi.Moshi
import org.laziskhu.amilkhu.data.source.remote.response.ErrorResponse

object ErrorResponseMapper : ApiErrorModelMapper<ErrorResponse> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ErrorResponse {
        val errorBody = apiErrorResponse.errorBody?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
        return ErrorResponse(false, errorBody?.message)
    }
}
