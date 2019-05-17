package com.test.todoappjava.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.test.todoappjava.model.TodoData;

import java.util.List;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTodo(TodoData todoData);

    @Query("SELECT * from todo_table ORDER BY date ASC")
    LiveData<List<TodoData>> getAllToDo();

    @Delete
    void deleteTodo(TodoData todoData);
}
