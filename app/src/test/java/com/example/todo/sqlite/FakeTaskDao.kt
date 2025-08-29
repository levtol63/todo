package com.example.todo.sqlite
import com.example.todo.data.*

class FakeTaskDao : TaskDao {
    private val data = LinkedHashMap<Long, Task>()
    private var nextId = 1L
    override suspend fun insert(task: Task): Long { val id = nextId++; data[id] = task.copy(id=id); return id }
    override suspend fun get(id: Long) = data[id]
    override suspend fun getAll() = data.values.toList()
    override suspend fun update(task: Task) = data.replace(task.id, task) != null
    override suspend fun delete(id: Long) = data.remove(id) != null
}
