package com.test.moladininterviewtest.data.model

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.moladininterviewtest.data.model.UserEntity.UserResponse.Companion.toDomain
import com.test.moladininterviewtest.domain.model.User
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

class UserEntityTest {

    private lateinit var userEntity: UserEntity.UserResponse
    @Before
    fun setup(){
        userEntity = UserEntity.UserResponse(
            id = 1,
            first_name = "George",
            last_name = "Bluth",
            email = "george.bluth@reqres.in",
            avatar = "https://reqres.in/img/faces/1-image.jpg"
        )
    }

    @Test
    fun `check fun toDomain return User object`() {
        //Given
        val userEntityLocal = userEntity
        //When
        val user = userEntityLocal.toDomain()
        //Then
        Assert.assertEquals(user::class, User::class)
    }

    @Test
    fun `check value domain User is same with UserResponse`(){
        //Given
        val userEntityLocal = userEntity
        //When
        val user = userEntityLocal.toDomain()
        //Then
        Assert.assertEquals(userEntityLocal.id, user.id)
        Assert.assertEquals(userEntityLocal.first_name, user.first_name)
        Assert.assertEquals(userEntityLocal.last_name, user.last_name)
        Assert.assertEquals(userEntityLocal.avatar, user.avatar)
        Assert.assertEquals(userEntityLocal.email, user.email)
    }
}