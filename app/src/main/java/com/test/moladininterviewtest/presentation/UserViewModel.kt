package com.test.moladininterviewtest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.test.moladininterviewtest.data.model.UserEntity.UserResponse.Companion.toDomain
import com.test.moladininterviewtest.di.ApplicationModule
import com.test.moladininterviewtest.domain.model.User
import com.test.moladininterviewtest.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationModule.DispatcherIO val dispatcher: CoroutineDispatcher
): ViewModel() {

    fun getUser(): Flow<PagingData<User>> {
        return userRepository.getUser().map {
            value ->
                value.map {
                    it.toDomain()
                }
        }.cachedIn(viewModelScope)
    }
}