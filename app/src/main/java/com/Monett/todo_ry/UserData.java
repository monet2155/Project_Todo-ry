package com.Monett.todo_ry;

import java.util.ArrayList;

public class UserData {

    ArrayList<Todo> todos;
    ArrayList<Diary> diaries;

    Diary editDiary;

    private static UserData instance;

    public static UserData getInstance(){
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public UserData(){
        this.todos = new ArrayList<>();
        this.diaries = new ArrayList<>();
    }

    public int getEditDiaryPosition() {
        return diaries.indexOf(editDiary);
    }
}
