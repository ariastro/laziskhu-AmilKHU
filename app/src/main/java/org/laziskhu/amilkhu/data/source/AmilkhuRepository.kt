package org.laziskhu.amilkhu.data.source

import com.skydoves.sandwich.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import org.laziskhu.amilkhu.data.source.remote.RemoteDataSource
import org.laziskhu.amilkhu.data.source.remote.response.*
import org.laziskhu.amilkhu.utils.Constants.DEFAULT_ERROR_MESSAGE
import org.laziskhu.amilkhu.utils.ErrorResponseMapper
import org.laziskhu.amilkhu.vo.Resource
import java.io.File
import javax.inject.Inject

class AmilkhuRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : AmilkhuDataSource {

    override fun login(username: String, password: String): Flow<Resource<LoginResponse>> = flow {
        remoteDataSource.login(username, password).let {
            it.suspendOnSuccess {
                emit(Resource.success(data))
            }.suspendOnError {
                emit(Resource.error(map(ErrorResponseMapper)?.message ?: DEFAULT_ERROR_MESSAGE, null))
            }.suspendOnException {
                emit(Resource.error(message(), null))
            }
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

    override fun getProfile(id: String): Flow<Resource<GetProfileResponse>> = flow {
        remoteDataSource.getProfile(id).let {
            it.suspendOnSuccess {
                emit(Resource.success(data))
            }.suspendOnError {
                emit(Resource.error(map(ErrorResponseMapper)?.message ?: DEFAULT_ERROR_MESSAGE, null))
            }.suspendOnException {
                emit(Resource.error(message(), null))
            }
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

    override fun getHistoryAttendance(id: String): Flow<Resource<GetHistoryAttendanceResponse>> = flow {
        remoteDataSource.getHistoryAttendance(id).let {
            it.suspendOnSuccess {
                emit(Resource.success(data))
            }.suspendOnError {
                emit(Resource.error(map(ErrorResponseMapper)?.message ?: DEFAULT_ERROR_MESSAGE, null))
            }.suspendOnException {
                emit(Resource.error(message(), null))
            }
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

    override fun checkIn(userId: String, checkInTime: String, latitude: String, longitude: String,
                         isInOffice: Boolean, photo: File, notes: String?, date: String
    ): Flow<Resource<BaseResponse>> = flow {
        remoteDataSource.checkIn(userId, checkInTime, latitude, longitude, isInOffice, photo, notes, date).let {
            it.suspendOnSuccess {
                emit(Resource.success(data))
            }.suspendOnError {
                emit(Resource.error(map(ErrorResponseMapper)?.message ?: DEFAULT_ERROR_MESSAGE, null))
            }.suspendOnException {
                emit(Resource.error(message(), null))
            }
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

    override fun checkOut(userId: String, checkOutTime: String, latitude: String, longitude: String, date: String): Flow<Resource<BaseResponse>> = flow {
        remoteDataSource.checkOut(userId, checkOutTime, latitude, longitude, date).let {
            it.suspendOnSuccess {
                emit(Resource.success(data))
            }.suspendOnError {
                emit(Resource.error(map(ErrorResponseMapper)?.message ?: DEFAULT_ERROR_MESSAGE, null))
            }.suspendOnException {
                emit(Resource.error(message(), null))
            }
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

    override fun getWaitingAttendance(date: String): Flow<Resource<GetWaitingAttendanceResponse>> = flow {
        remoteDataSource.getWaitingAttendance(date).let {
            it.suspendOnSuccess {
                emit(Resource.success(data))
            }.suspendOnError {
                emit(Resource.error(map(ErrorResponseMapper)?.message ?: DEFAULT_ERROR_MESSAGE, null))
            }.suspendOnException {
                emit(Resource.error(message(), null))
            }
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

    override fun updateAttendanceStatus(id: String, status: String): Flow<Resource<BaseResponse>> = flow {
        remoteDataSource.updateAttendanceStatus(id, status).let {
            it.suspendOnSuccess {
                emit(Resource.success(data))
            }.suspendOnError {
                emit(Resource.error(map(ErrorResponseMapper)?.message ?: DEFAULT_ERROR_MESSAGE, null))
            }.suspendOnException {
                emit(Resource.error(message(), null))
            }
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

    override fun getPaymentGateway(isQRIS: Int): Flow<Resource<GetPaymentGatewayResponse>> = flow {
        remoteDataSource.getPaymentGateway(isQRIS).let {
            it.suspendOnSuccess {
                emit(Resource.success(data))
            }.suspendOnError {
                emit(Resource.error(map(ErrorResponseMapper)?.message ?: DEFAULT_ERROR_MESSAGE, null))
            }.suspendOnException {
                emit(Resource.error(message(), null))
            }
        }
    }.onStart { emit(Resource.loading(null)) }.flowOn(ioDispatcher)

}
