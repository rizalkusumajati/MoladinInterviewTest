package com.test.moladininterviewtest.domain.repository

import androidx.paging.PagingData
import com.test.moladininterviewtest.data.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser() : Flow<PagingData<UserEntity.UserResponse>>
}