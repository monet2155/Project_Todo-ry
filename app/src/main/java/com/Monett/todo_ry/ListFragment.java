package com.Monett.todo_ry;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ListFragment extends Fragment {
    ViewGroup rootView;

    SQLiteDatabase db;

    ArrayList<Todo> todos = UserData.getInstance().todos;
    TodoAdapter todoAdapter;

    ArrayList<Diary> diaries = UserData.getInstance().diaries;
    DiaryAdapter diaryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        todos = UserData.getInstance().todos;
        diaries = UserData.getInstance().diaries;

        // init Lists
        initTodoList();
        initDiaryList();

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        // save DATABASE
        DBHelper helper = new DBHelper(getContext());
        db = helper.getWritableDatabase();

        // save todo
        Cursor cursor = db.rawQuery("SELECT * FROM " + "tb_todo", null);
        cursor.moveToFirst();

        for (int i = 0; i < todos.size(); i++) {
            db.execSQL("UPDATE tb_todo SET ischecked = " + (todos.get(i).isComplete ? 1 : 0) +
                    " where _id = " + cursor.getInt(cursor.getColumnIndex("_id")));

            db.execSQL("UPDATE tb_todo SET content = '" + todos.get(i).content +
                    "' where _id = " + cursor.getInt(cursor.getColumnIndex("_id")));

            cursor.moveToNext();
        }

        // save diary
        cursor = db.rawQuery("SELECT * FROM " + "tb_diary", null);
        cursor.moveToFirst();

        for (int i = 0; i < diaries.size(); i++) {
            db.execSQL("UPDATE tb_diary SET content = '" + diaries.get(i).content +
                    "' where _id = " + cursor.getInt(cursor.getColumnIndex("_id")));

            cursor.moveToNext();
        }

        db.close();
    }

    public void initTodoList(){

        ListView todoList = rootView.findViewById(R.id.list_todo);
        todoAdapter = new TodoAdapter(getContext(), todos);

        Button todoAddBtn = rootView.findViewById(R.id.button_todo_add);
        todoAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo todo = new Todo(getContext(), todos.size(), "");
                todos.add(todo);

                DBHelper helper = new DBHelper(getContext());
                db = helper.getReadableDatabase();

                db.execSQL("insert into tb_todo (ischecked, content) values (?, ?)", new Object[]{0 , ""});

                todoAdapter.notifyDataSetChanged();
            }
        });

        todoList.setAdapter(todoAdapter);
    }

    public void initDiaryList(){


        ListView diaryList = rootView.findViewById(R.id.list_diary);
        diaryAdapter = new DiaryAdapter(getContext(), UserData.getInstance().diaries);
        diaryList.setAdapter(diaryAdapter);

        diaryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserData.getInstance().editDiary = UserData.getInstance().diaries.get(position);

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.onFragmentChanged(1);
            }
        });
    }
}

class TodoAdapter extends BaseAdapter {

    ArrayList<Todo> todos;
    Context context;

    public TodoAdapter(Context context, ArrayList<Todo> todos){
        this.context = context;
        this.todos = todos;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int position) {
        return todos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return todos.get(position).getView();
    }
}

class DiaryAdapter extends BaseAdapter {

    ArrayList<Diary> diaries;
    Context context;

    public DiaryAdapter(Context context, ArrayList<Diary> diaries){
        this.context = context;
        this.diaries = diaries;
    }

    @Override
    public int getCount() {
        return diaries.size();
    }

    @Override
    public Object getItem(int position) {
        return diaries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return diaries.get(position).getView();
    }


}