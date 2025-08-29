package com.example.todo.data
interface TaskDao {
    suspend fun insert(task: Task): Long
    suspend fun get(id: Long): Task?
    suspend fun getAll(): List<Task>
    suspend fun update(task: Task): Boolean
    suspend fun delete(id: Long): Boolean
}
