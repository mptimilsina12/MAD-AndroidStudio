package com.test.todoappjava.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class TodoData implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "todo")
    private String todo = "";

    @NonNull
    @ColumnInfo(name = "date")
    private String date = "";

    @NonNull
    @ColumnInfo(name = "status")
    private String status="";

    @NonNull
    @ColumnInfo(name="priority")
    private String priority;

    public TodoData(){}

    protected TodoData(Parcel in) {
        id = in.readInt();
        todo = in.readString();
        date = in.readString();
        status = in.readString();
        priority = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(todo);
        dest.writeString(date);
        dest.writeString(status);
        dest.writeString(priority);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TodoData> CREATOR = new Creator<TodoData>() {
        @Override
        public TodoData createFromParcel(Parcel in) {
            return new TodoData(in);
        }

        @Override
        public TodoData[] newArray(int size) {
            return new TodoData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NonNull
    public String getPriority() {
        return priority;
    }

    public void setPriority(@NonNull String priority) {
        this.priority = priority;
    }
}
