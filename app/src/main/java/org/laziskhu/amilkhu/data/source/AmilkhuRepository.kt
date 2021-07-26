package org.laziskhu.amilkhu.data.source

import com.skydoves.sandwich.*
import org.laziskhu.amilkhu.vo.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import org.laziskhu.amilkhu.data.source.remote.RemoteDataSource
import org.laziskhu.amilkhu.data.source.remote.response.LoginResponse
import org.laziskhu.amilkhu.utils.ErrorResponseMapper
import org.laziskhu.amilkhu.utils.logDebug
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
    }.onStart { emit(Resource.loading(LoginResponse())) }.flowOn(ioDispatcher)

}
