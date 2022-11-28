package com.test.moladininterviewtest.data.remote

import com.test.moladininterviewtest.BuildConfig
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class ApiTest {
    private lateinit var mockWebServer: MockWebServer
    private val mockResponseUser = """
        {
            "page": 1,
            "per_page": 2,
            "total": 12,
            "total_pages": 6,
            "data": [
                {
                    "id": 1,
                    "email": "george.bluth@reqres.in",
                    "first_name": "George",
                    "last_name": "Bluth",
                    "avatar": "https://reqres.in/img/faces/1-image.jpg"
                },
                {
                    "id": 2,
                    "email": "janet.weaver@reqres.in",
                    "first_name": "Janet",
                    "last_name": "Weaver",
                    "avatar": "https://reqres.in/img/faces/2-image.jpg"
                }
            ],
            "support": {
                "url": "https://reqres.in/#support-heading",
                "text": "To keep ReqRes free, contributions towards server costs are appreciated!"
            }
        }
    """.trimIndent()

    private lateinit var apiService: Api

    @Before
    fun setup(){
        mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.enqueue(MockResponse().setBody(mockResponseUser))
        val baseUrl = mockWebServer.url(BuildConfig.BASE_URL)
        apiService =  Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @After
    fun tearDown(){
        mockWebServer.shutdown()
    }

    @Test
    fun `check getUser can be parsed`(){
        //Given
        val localApiService = apiService

        //When
        runTest {
            val result = localApiService.getUserList()

            Assert.assertTrue(result.data.isNotEmpty())
            Assert.assertEquals(2, result.data.size)
        }
    }
}