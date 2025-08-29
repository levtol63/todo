package com.example.todo.data
class TaskRepository(private val dao: TaskDao) {
    suspend fun create(title: String): Task {
        val id = dao.insert(Task(0, title))
        return Task(id, title)
    }
    suspend fun read(id: Long) = dao.get(id)
    suspend fun readAll() = dao.getAll()
    suspend fun update(task: Task) = dao.update(task)
    suspend fun delete(id: Long) = dao.delete(id)
}
