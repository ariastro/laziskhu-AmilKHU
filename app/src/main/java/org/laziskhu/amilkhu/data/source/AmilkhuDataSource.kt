package org.laziskhu.amilkhu.data.source

import kotlinx.coroutines.flow.Flow
import org.laziskhu.amilkhu.data.source.remote.response.LoginResponse
import org.laziskhu.amilkhu.vo.Resource

interface AmilkhuDataSource {

    fun login(username: String, password: String): Flow<Resource<LoginResponse>>

}