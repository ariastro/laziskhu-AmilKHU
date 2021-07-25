package org.laziskhu.amilkhu.data.source.remote

import com.skydoves.sandwich.ApiResponse
import org.laziskhu.amilkhu.data.source.remote.api.ApiService
import org.laziskhu.amilkhu.data.source.remote.response.LoginResponse
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun login(username: String, password: String): ApiResponse<LoginResponse> =
        apiService.login(username, password)
}
