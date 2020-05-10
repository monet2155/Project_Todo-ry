package com.Monett.todo_ry;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.EditText;

public class Todo {

    public int ID;

    boolean isComplete = false;
    String content;

    TodoView todoView;

    public Todo(Context context, int ID ,String content){
        this.ID = ID;
        this.content = content;

        todoView = new TodoView(context, this);
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
        todoView.todoCheckbox.setChecked(complete);
    }

    public View getView(){
        return todoView;
    }
}

class TodoView extends LinearLayout {

    Context context;

    Todo todo;

    CheckBox todoCheckbox;
    EditText todoContent;

    public TodoView(Context context, Todo todo){
        super(context);
        this.context = context;
        this.todo = todo;

        init(context);
    }

    public TodoView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
    }

    public TodoView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void init(Context context){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_todo, this, true);

        todoCheckbox =  (CheckBox) view.findViewById(R.id.todo_checkbox);
        todoCheckbox.setChecked(false);
        todoCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                todo.isComplete = isChecked;
            }
        });

        todoContent = (EditText) view.findViewById(R.id.todo_content);
        todoContent.setText(todo.content);
        todoContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                todo.content = s.toString();
            }
        });
    }
}

