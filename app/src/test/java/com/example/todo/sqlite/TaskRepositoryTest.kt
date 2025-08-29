package com.example.todo.sqlite
import com.example.todo.data.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import io.qameta.allure.*

@Epic("SQLite mock")
@Feature("CRUD")
class TaskRepositoryTest {
    @Test @Story("Create & Read")
    fun create_and_read() = runTest {
        val repo = TaskRepository(FakeTaskDao())
        val created = repo.create("First")
        assertTrue(created.id > 0)
        assertEquals("First", repo.read(created.id)?.title)
    }
    @Test @Story("Update")
    fun update_task() = runTest {
        val repo = TaskRepository(FakeTaskDao())
        val t = repo.create("T"); val ok = repo.update(t.copy(done = true))
        assertTrue(ok); assertTrue(repo.read(t.id)!!.done)
    }
    @Test @Story("Delete")
    fun delete_task() = runTest {
        val repo = TaskRepository(FakeTaskDao())
        val t = repo.create("T"); val ok = repo.delete(t.id)
        assertTrue(ok); assertNull(repo.read(t.id))
    }
}
