package org.laziskhu.amilkhu.data.source.remote.api

import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.laziskhu.amilkhu.data.source.remote.response.*
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
        @Part("user_id") userId: RequestBody,
        @Part("coming_time") checkInTime: RequestBody,
        @Part("coming_latitude") latitude: RequestBody,
        @Part("coming_longitude") longitude: RequestBody,
        @Part("isInOffice") isInOffice: RequestBody,
        @Part("notes") notes: RequestBody?,
        @Part("date") date: RequestBody,
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

    @GET("attendences")
    suspend fun getWaitingAttendance(
        @Query("date") date: String,
        @Query("status") status: String
    ): ApiResponse<GetWaitingAttendanceResponse>

}