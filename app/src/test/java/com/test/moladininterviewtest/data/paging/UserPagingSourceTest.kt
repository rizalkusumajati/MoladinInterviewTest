package com.test.moladininterviewtest.data.paging

import androidx.paging.PagingSource
import com.test.moladininterviewtest.data.model.UserEntity
import com.test.moladininterviewtest.data.remote.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UserPagingSourceTest {

    @Mock
    private lateinit var api: Api

    private lateinit var userEntity: UserEntity

    val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(dispatcher)
        userEntity = UserEntity(
            listOf(
                UserEntity.UserResponse(
                    id = 1,
                    email = "george.bluth@reqres.in",
                    first_name = "George",
                    last_name = "Bluth",
                    avatar = "https://reqres.in/img/faces/1-image.jpg"
                ),
                UserEntity.UserResponse(
                    id = 2,
                    email = "janet.weaver@reqres.in",
                    first_name = "Janet",
                    last_name = "Weaver",
                    avatar = "https://reqres.in/img/faces/1-image.jpg"
                )
            )
        )
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `check Paging source`() {
        val pagingSource = UserPagingSource(
            api,
            dispatcher
        )
            runTest(dispatcher) {
                `when`( api.getUserList(1,2) ).then { userEntity }
                assertEquals(
                    expected = PagingSource.LoadResult.Page(
                        data = userEntity.data,
                        prevKey = null,
                        nextKey = 2
                    ),
                    actual = pagingSource.load(
                        PagingSource.LoadParams.Refresh(
                            key = null,
                            loadSize = 2,
                            placeholdersEnabled = false
                        )
                    )
                )
            }



    }
}