package org.laziskhu.amilkhu.data.source

import com.skydoves.sandwich.*
import org.laziskhu.amilkhu.vo.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import org.laziskhu.amilkhu.data.source.remote.RemoteDataSource
import org.laziskhu.amilkhu.data.source.remote.response.BaseResponse
import org.laziskhu.amilkhu.data.source.remote.response.GetHistoryAttendanceResponse
import org.laziskhu.amilkhu.data.source.remote.response.GetProfileResponse
import org.laziskhu.amilkhu.data.source.remote.response.LoginResponse
import org.laziskhu.amilkhu.utils.ErrorResponseMapper
import org.laziskhu.amilkhu.utils.logDebug
import java.io.File
import javax.inject.Inject

class AmilkhuRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : AmilkhuDataSource {

    override fun login(username: String, password: String): Flow<Resource<LoginResponse>> = flow {
        val response = remoteDataSource.login(username, password)
        response.suspendOnSuccess {
            emit(Resource.success(data))
        }.suspendOnError {
            emit(Resource.error(map(ErrorResponseMapper).message, LoginResponse()))
        }.suspendOnException {
            emit(Resource.error(message, LoginResponse()))
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

    override fun getProfile(id: String): Flow<Resource<GetProfileResponse>> = flow {
        val response = remoteDataSource.getProfile(id)
        response.suspendOnSuccess {
            emit(Resource.success(data))
        }.suspendOnError {
            emit(Resource.error(map(ErrorResponseMapper).message, GetProfileResponse()))
        }.suspendOnException {
            emit(Resource.error(message, GetProfileResponse()))
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

    override fun getHistoryAttendance(id: String): Flow<Resource<GetHistoryAttendanceResponse>> = flow {
        remoteDataSource.getHistoryAttendance(id).let {
            it.suspendOnSuccess {
                emit(Resource.success(data))
            }.suspendOnError {
                emit(Resource.error(map(ErrorResponseMapper).message, GetHistoryAttendanceResponse()))
            }.suspendOnException {
                emit(Resource.error(message, GetHistoryAttendanceResponse()))
            }
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

    override fun checkIn(
        userId: String,
        checkInTime: String,
        latitude: String,
        longitude: String,
        isInOffice: Boolean,
        photo: File,
        notes: String?,
        date: String
    ): Flow<Resource<BaseResponse>> = flow {
        remoteDataSource.checkIn(userId, checkInTime, latitude, longitude, isInOffice, photo, notes, date).let {
            it.suspendOnSuccess {
                emit(Resource.success(data))
            }.suspendOnError {
                emit(Resource.error(map(ErrorResponseMapper).message, BaseResponse()))
            }.suspendOnException {
                emit(Resource.error(message, BaseResponse()))
            }
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

}
