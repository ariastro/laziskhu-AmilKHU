package org.laziskhu.amilkhu.data.source.remote

import com.skydoves.sandwich.ApiResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.laziskhu.amilkhu.data.source.remote.api.ApiService
import org.laziskhu.amilkhu.data.source.remote.response.BaseResponse
import org.laziskhu.amilkhu.data.source.remote.response.GetHistoryAttendanceResponse
import org.laziskhu.amilkhu.data.source.remote.response.GetProfileResponse
import org.laziskhu.amilkhu.data.source.remote.response.LoginResponse
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
        val photoBody = MultipartBody.Part.createFormData("photo", photo.name, requestBody)
        return apiService.checkIn(userId, checkInTime, latitude, longitude, isInOffice, notes, date, photoBody)
    }

    suspend fun checkOut(userId: String, checkOutTime: String, latitude: String, longitude: String, date: String): ApiResponse<BaseResponse> =
        apiService.checkOut(userId, checkOutTime, latitude, longitude, date)

}
