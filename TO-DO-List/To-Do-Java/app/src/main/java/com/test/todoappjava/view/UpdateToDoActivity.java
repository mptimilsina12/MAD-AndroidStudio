package com.test.todoappjava.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.test.todoappjava.R;
import com.test.todoappjava.model.TodoData;
import com.test.todoappjava.viewmodel.ToDoViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class UpdateToDoActivity extends AppCompatActivity {
    private ToDoViewModel mViewModel;

    private AppCompatEditText mEditTextDate, mEditTextTodo;
    private AppCompatButton mBtnEdit;
    private AppCompatSpinner mSpinner;
    TodoData mTodoData;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_todo);
        calendar = Calendar.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar_update);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        mEditTextDate = findViewById(R.id.et_edit_date);
        mEditTextTodo = findViewById(R.id.et_edit_todo);
        mBtnEdit = findViewById(R.id.btn_edit_save);
        mSpinner = findViewById(R.id.spinner_edit_priority);

        mTodoData = getIntent().getParcelableExtra("todo");
        mEditTextTodo.setText(mTodoData.getTodo());
        mEditTextDate.setText(mTodoData.getDate());

        if (mTodoData.getPriority().equals(getString(R.string.priority_low))) {
            mSpinner.setSelection(1);
        } else if (mTodoData.getPriority().equals(getString(R.string.priority_medium))) {
            mSpinner.setSelection(2);
        } else if (mTodoData.getPriority().equals(getString(R.string.priority_high))) {
            mSpinner.setSelection(3);
        }

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todo = mEditTextTodo.getText().toString().trim();
                String date = mEditTextDate.getText().toString().trim();
                if (todo.isEmpty() && date.isEmpty()) {
                    Toast.makeText(UpdateToDoActivity.this, "Fields cannot be empty",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (mSpinner.getSelectedItem().equals(getString(R.string.priority_select))) {
                        Toast.makeText(UpdateToDoActivity.this, "Priority is not selected", Toast.LENGTH_SHORT).show();
                    }
                    //save to db
                    TodoData todoData = new TodoData();
                    todoData.setId(mTodoData.getId());
                    todoData.setTodo(todo);
                    todoData.setDate(date);
                    todoData.setPriority(mSpinner.getSelectedItem().toString());
                    todoData.setStatus(mTodoData.getStatus());
                    mViewModel.insert(todoData);
                    Toast.makeText(UpdateToDoActivity.this, "Edited todo successfully",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        mEditTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        mEditTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicker();
                }
            }
        });
    }

    private void showDatePicker() {
        new DatePickerDialog(UpdateToDoActivity.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mViewModel.enteredDate.setValue(simpleDateFormat.format(calendar.getTime()));
            mEditTextDate.setText(mViewModel.enteredDate.getValue());
        }
    };
}
