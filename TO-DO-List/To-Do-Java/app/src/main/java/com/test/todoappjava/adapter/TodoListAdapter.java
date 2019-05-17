package com.test.todoappjava.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.test.todoappjava.R;
import com.test.todoappjava.model.TodoData;
import com.test.todoappjava.view.UpdateToDoActivity;
import com.test.todoappjava.viewmodel.ToDoViewModel;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> implements
        Filterable {
    private List<TodoData> todoDataList;
    private List<TodoData> todoFilteredDataList;
    private ToDoViewModel toDoViewModel;
    private Context context;

    public TodoListAdapter(List<TodoData> todoDataList,
                           ToDoViewModel toDoViewModel) {
        this.todoDataList = todoDataList;
        this.toDoViewModel = toDoViewModel;
        this.todoFilteredDataList = todoDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_pending, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TodoData todoData = todoFilteredDataList.get(position);
        holder.mTodo.setText("On " + todoData.getDate() + "\n" + todoData.getTodo());
        if (todoData.getStatus().equals("Pending")) {
            holder.mCheckBox.setChecked(false);
        } else if (todoData.getStatus().equals("Done")) {
            holder.mCheckBox.setChecked(true);
        }
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoData todoData1 = new TodoData();
                todoData1.setId(todoData.getId());
                todoData1.setTodo(todoData.getTodo());
                todoData1.setDate(todoData.getDate());
                todoData1.setPriority(todoData.getPriority());
                todoData1.setStatus("Done");
                toDoViewModel.insert(todoData1);
                Toast.makeText(context, "Task completed", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
        holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete todo")
                        .setMessage("Are you sure you want to delete this todo from the list?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                toDoViewModel.delete(todoData);
                                Toast.makeText(context, "Todo item deleted", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
        holder.mChip.setText(todoData.getPriority());
        if (todoData.getPriority().equals(context.getString(R.string.priority_low))) {
            holder.mChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorLow)));
        } else if (todoData.getPriority().equals(context.getString(R.string.priority_medium))) {
            holder.mChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorMedium)));
        } else if (todoData.getPriority().equals(context.getString(R.string.priority_high))) {
            holder.mChip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorHigh)));
        }
        holder.mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateToDoActivity.class);
                intent.putExtra("todo", todoData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoFilteredDataList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    todoFilteredDataList = todoDataList;
                } else {
                    List<TodoData> filteredList = new ArrayList<>();
                    for (TodoData row : todoDataList) {
                        if (row.getTodo().toLowerCase().contains(charString) ||
                            row.getPriority().toLowerCase().contains(charString)) {
                            filteredList.add(row);
                        }
                    }
                    todoFilteredDataList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = todoFilteredDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                todoFilteredDataList = (ArrayList<TodoData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox mCheckBox;
        public TextView mTodo;
        public AppCompatImageButton mDeleteBtn, mEditBtn;
        public Chip mChip;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mCheckBox = itemView.findViewById(R.id.item_checkbox_pending);
            this.mTodo = itemView.findViewById(R.id.item_tv_todo);
            mDeleteBtn = itemView.findViewById(R.id.btn_delete);
            mEditBtn = itemView.findViewById(R.id.btn_edit);
            mChip = itemView.findViewById(R.id.chip_priority);
        }
    }

}