package com.test.moladininterviewtest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.test.moladininterviewtest.BuildConfig
import com.test.moladininterviewtest.data.model.UserEntity
import com.test.moladininterviewtest.data.paging.NETWORK_PAGE_SIZE
import com.test.moladininterviewtest.data.paging.UserPagingSource
import com.test.moladininterviewtest.data.remote.Api
import com.test.moladininterviewtest.domain.model.User
import com.test.moladininterviewtest.domain.repository.UserRepository
import com.test.moladininterviewtest.presentation.adapter.UserAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FakeRepository(): UserRepository{
    override fun getUser(): Flow<PagingData<UserEntity.UserResponse>> {
        return flow {
            val value = PagingData.from(
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
            emit(value)
        }
    }

}

class NoopListCallback : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
}
@RunWith(AndroidJUnit4::class)
class UserViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: UserViewModel
    val dispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
        viewModel = UserViewModel(
            FakeRepository(),
            Dispatchers.Main
        )
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }



    @Test
    fun `check viewmodel getuser return PagingData User`(){
        //Given
        val viewModelLocal = viewModel
        runTest {
            //When
            val differ = AsyncPagingDataDiffer(
                diffCallback = UserAdapter.UserDiffCallback(),
                updateCallback = NoopListCallback(),
                workerDispatcher = Dispatchers.Main
            )

            val result = viewModelLocal.getUser()
            differ.submitData(result.first())

            // Wait for transforms and the differ to process all updates.
            advanceUntilIdle()


            //Then
            assertNotNull(result)
            assertEquals(
                expected = User::class,
                actual = differ.peek(0)!!::class
            )

        }

    }

}