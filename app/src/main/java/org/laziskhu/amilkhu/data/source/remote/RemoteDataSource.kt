package org.laziskhu.amilkhu.data.source.remote

import com.skydoves.sandwich.ApiResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.laziskhu.amilkhu.data.source.remote.api.ApiService
import org.laziskhu.amilkhu.data.source.remote.response.*
import org.laziskhu.amilkhu.utils.Constants.WAITING
import java.io.File
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun login(username: String, password: String): ApiResponse<LoginResponse> =
        apiService.login(username, password)

    suspend fun getProfile(id: String): ApiResponse<GetProfileResponse> =
        apiService.getProfile(id)

    suspend fun getHistoryAttendance(id: String): ApiResponse<GetHistoryAttendanceResponse> =
        apiService.getHistoryAttendance(id)

    suspend fun checkIn(
        userId: String,
        checkInTime: String,
        latitude: String,
        longitude: String,
        isInOffice: Boolean,
        photo: File,
        notes: String?,
        date: String
    ): ApiResponse<BaseResponse> {
        val requestBody = photo.asRequestBody("multipart/form-file".toMediaTypeOrNull())
        val convertedBoolean = if (isInOffice) "1" else "0"
        return apiService.checkIn(
            userId = userId.toRequestBody("text/plain".toMediaTypeOrNull()),
            checkInTime = checkInTime.toRequestBody("text/plain".toMediaTypeOrNull()),
            latitude = latitude.toRequestBody("text/plain".toMediaTypeOrNull()),
            longitude = longitude.toRequestBody("text/plain".toMediaTypeOrNull()),
            isInOffice = convertedBoolean.toRequestBody("text/plain".toMediaTypeOrNull()),
            photo = MultipartBody.Part.createFormData("photo", photo.name, requestBody),
            notes = notes?.toRequestBody("text/plain".toMediaTypeOrNull()),
            date = date.toRequestBody("text/plain".toMediaTypeOrNull()),
        )
    }

    suspend fun checkOut(userId: String, checkOutTime: String, latitude: String, longitude: String, date: String): ApiResponse<BaseResponse> =
        apiService.checkOut(userId, checkOutTime, latitude, longitude, date)

    suspend fun getWaitingAttendance(date: String): ApiResponse<GetWaitingAttendanceResponse> =
        apiService.getWaitingAttendance(date, WAITING)

    suspend fun updateAttendanceStatus(id: String, status: String): ApiResponse<BaseResponse> =
        apiService.updateAttendanceStatus(id, status)

    suspend fun getPaymentGateway(isQRIS: Int): ApiResponse<GetPaymentGatewayResponse> =
        apiService.getPaymentGateway(isQRIS)

}
