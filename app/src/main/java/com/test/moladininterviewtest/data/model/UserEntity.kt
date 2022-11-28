package com.test.moladininterviewtest.data.model

import com.test.moladininterviewtest.domain.model.User

data class UserEntity(
    val data: List<UserResponse>
) {

    data class UserResponse(
        val id: Int,
        val email: String,
        val first_name: String,
        val last_name: String,
        val avatar: String
    ){
        companion object{
            fun UserResponse.toDomain(): User{
                return User(
                    id = id,
                    email = email,
                    first_name = first_name,
                    last_name = last_name,
                    avatar = avatar
                )
            }
        }
    }

}
