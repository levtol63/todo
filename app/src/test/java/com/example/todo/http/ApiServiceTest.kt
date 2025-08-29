package com.example.todo.http
import com.example.todo.data.Task
import com.example.todo.data.api.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.qameta.allure.*

@Epic("API MockWebServer")
@Feature("GET/POST/DELETE")
class ApiServiceTest {
    private val gson = Gson()
    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @BeforeEach fun setup() {
        server = MockWebServer().apply { start() }
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }
    @AfterEach fun tearDown() { server.shutdown() }

    @Test @Story("GET /tasks")
    fun get_tasks() = runTest {
        val body = gson.toJson(listOf(Task(1,"A"), Task(2,"B")))
        server.enqueue(MockResponse().setResponseCode(200).setBody(body))
        val result = api.getTasks()
        Assertions.assertEquals(2, result.size)
    }

    @Test @Story("POST /tasks")
    fun post_task() = runTest {
        val created = gson.toJson(Task(3,"C"))
        server.enqueue(MockResponse().setResponseCode(201).setBody(created))
        val result = api.create(mapOf("title" to "C"))
        Assertions.assertEquals(3L, result.id)
    }

    @Test @Story("DELETE /tasks/{id}")
    fun delete_task() = runTest {
        server.enqueue(MockResponse().setResponseCode(204))
        api.delete(1)
        val req = server.takeRequest()
        Assertions.assertEquals("DELETE", req.method)
        Assertions.assertEquals("/tasks/1", req.path)
    }
}
