package com.test.moladininterviewtest.data.remote

import com.test.moladininterviewtest.data.model.UserEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("users")
    suspend fun getUserList(
        @Query("page") page: Int = 0,
        @Query("per_page") perPage: Int = 2
    ): UserEntity
}
