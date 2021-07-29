package org.laziskhu.amilkhu.data.source.remote.api

import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.laziskhu.amilkhu.data.source.remote.response.BaseResponse
import org.laziskhu.amilkhu.data.source.remote.response.GetHistoryAttendanceResponse
import org.laziskhu.amilkhu.data.source.remote.response.GetProfileResponse
import org.laziskhu.amilkhu.data.source.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiResponse<LoginResponse>

    @GET("users")
    suspend fun getProfile(@Query("id") id: String): ApiResponse<GetProfileResponse>

    @GET("attendences")
    suspend fun getHistoryAttendance(@Query("id") id: String): ApiResponse<GetHistoryAttendanceResponse>

    @Multipart
    @POST("attendences/checkin")
    suspend fun checkIn(
        @Part("user_id") userId: String,
        @Part("coming_time") checkInTime: String,
        @Part("coming_latitude") latitude: String,
        @Part("coming_longitude") longitude: String,
        @Part("isInOffice") isInOffice: Boolean,
        @Part("notes") notes: String?,
        @Part("date") date: String,
        @Part photo: MultipartBody.Part
    ): ApiResponse<BaseResponse>

    @FormUrlEncoded
    @POST("attendences/checkout")
    suspend fun checkOut(
        @Field("user_id") userId: String,
        @Field("home_time") checkOutTime: String,
        @Field("home_latitude") latitude: String,
        @Field("home_longitude") longitude: String,
        @Field("date") date: String
    ): ApiResponse<BaseResponse>


}