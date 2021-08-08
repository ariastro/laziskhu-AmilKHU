package org.laziskhu.amilkhu.data.source

import kotlinx.coroutines.flow.Flow
import org.laziskhu.amilkhu.data.source.remote.response.*
import org.laziskhu.amilkhu.vo.Resource
import java.io.File

interface AmilkhuDataSource {

    fun login(username: String, password: String): Flow<Resource<LoginResponse>>

    fun getProfile(id: String): Flow<Resource<GetProfileResponse>>

    fun getHistoryAttendance(id: String): Flow<Resource<GetHistoryAttendanceResponse>>

    fun checkIn(userId: String, checkInTime: String, latitude: String, longitude: String,
                isInOffice: Boolean, photo: File, notes: String?, date: String
    ): Flow<Resource<BaseResponse>>

    fun checkOut(userId: String, checkOutTime: String, latitude: String, longitude: String, date: String): Flow<Resource<BaseResponse>>

    fun getWaitingAttendance(date: String): Flow<Resource<GetWaitingAttendanceResponse>>

    fun updateAttendanceStatus(id: String, status: String): Flow<Resource<BaseResponse>>

}