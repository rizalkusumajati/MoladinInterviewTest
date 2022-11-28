package com.test.moladininterviewtest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.test.moladininterviewtest.data.paging.NETWORK_PAGE_SIZE
import com.test.moladininterviewtest.data.paging.UserPagingSource
import com.test.moladininterviewtest.data.remote.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class UserRepositoryTest {

    @Mock
    private lateinit var api: Api

    private val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
    }

    @Before
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `check RepositoryImpl`(){
        runTest {
            //Given
            val userRepositoryImpl = UserRepositoryImpl(
                api,
                StandardTestDispatcher()
            )
            //When
            val pagingData = userRepositoryImpl.getUser().first()

            //Then
            assertEquals(
                expected = Pager(
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
                ).flow.first()::class,
                actual = pagingData::class
            )
        }
    }
}