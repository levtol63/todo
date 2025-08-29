package com.example.todo.data.api
import com.example.todo.data.Task
import retrofit2.http.*

interface ApiService {
    @GET("/tasks") suspend fun getTasks(): List<Task>
    @POST("/tasks") suspend fun create(@Body body: Map<String, String>): Task
    @DELETE("/tasks/{id}") suspend fun delete(@Path("id") id: Long)
}
