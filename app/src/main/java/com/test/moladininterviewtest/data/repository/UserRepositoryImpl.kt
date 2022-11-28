package com.test.moladininterviewtest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.moladininterviewtest.data.model.UserEntity
import com.test.moladininterviewtest.data.paging.NETWORK_PAGE_SIZE
import com.test.moladininterviewtest.data.paging.UserPagingSource
import com.test.moladininterviewtest.domain.repository.UserRepository
import com.test.moladininterviewtest.data.remote.Api
import com.test.moladininterviewtest.di.ApplicationModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: Api,
    @ApplicationModule.DispatcherIO private val dispatcher: CoroutineDispatcher
): UserRepository {

    override fun getUser(): Flow<PagingData<UserEntity.UserResponse>>  {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UserPagingSource(
                    api = api,
                    dispatcher = dispatcher
                )
            }
        ).flow
    }
}