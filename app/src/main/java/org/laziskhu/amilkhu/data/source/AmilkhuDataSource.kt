package org.laziskhu.amilkhu.data.source

import kotlinx.coroutines.flow.Flow
import org.laziskhu.amilkhu.data.source.remote.response.BaseResponse
import org.laziskhu.amilkhu.data.source.remote.response.GetHistoryAttendanceResponse
import org.laziskhu.amilkhu.data.source.remote.response.GetProfileResponse
import org.laziskhu.amilkhu.data.source.remote.response.LoginResponse
import org.laziskhu.amilkhu.vo.Resource
import java.io.File

interface AmilkhuDataSource {

    fun login(username: String, password: String): Flow<Resource<LoginResponse>>

    fun getProfile(id: String): Flow<Resource<GetProfileResponse>>

    fun getHistoryAttendance(id: String): Flow<Resource<GetHistoryAttendanceResponse>>

    fun checkIn(
        userId: String,
        checkInTime: String,
        latitude: String,
        longitude: String,
        isInOffice: Boolean,
        photo: File,
        notes: String?,
        date: String
    ): Flow<Resource<BaseResponse>>

}