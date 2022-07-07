package com.dspot.dspotandroid.data.network

import com.dspot.dspotandroid.util.Constants.BASE_URL
import com.google.gson.Gson
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(JUnit4::class)
internal class RetrofitApiInterfaceTest {


    lateinit var mockWebServer: MockWebServer
    lateinit var apiService: ApiInterface
    lateinit var gson: Gson


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        gson = Gson()
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BASE_URL))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiInterface::class.java)
    }


    @Test
    fun `get 50 users api test`() {
        runBlocking {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody("{}"))
            val response = apiService.getFiftyUsers(50)
            val request = mockWebServer.takeRequest()
            assertEquals("/?&results=50", request.path)
            response.body()?.results?.let { assertEquals(true, it.isEmpty()) }
        }
    }

    @Test
    fun `get paginated users api test`() {
        runBlocking {
            val mockResponse = MockResponse()
            mockWebServer.enqueue(mockResponse.setBody("{}"))
            val response = apiService.getAllUsersPaging(1, 50)
            val request = mockWebServer.takeRequest()
            assertEquals("/?&page=1&results=50", request.path)
            response.body()?.results?.let { assertEquals(true, it.isEmpty()) }
        }
    }


    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}