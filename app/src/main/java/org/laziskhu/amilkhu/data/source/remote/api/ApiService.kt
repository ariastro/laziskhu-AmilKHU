package org.laziskhu.amilkhu.data.source.remote.api

import com.skydoves.sandwich.ApiResponse
import org.laziskhu.amilkhu.data.source.remote.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(@Field("username") username: String,
                      @Field("password") password: String): ApiResponse<LoginResponse>

}