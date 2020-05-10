package com.Monett.todo_ry;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                loadDiaryList();
                loadTodoList();
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        findViewById(R.id.splash_image).startAnimation(anim);

        Handler hd = new Handler();
        hd.postDelayed(new Splashhandler(), 3000);
    }

    public void loadTodoList(){

        ArrayList<Todo> todos = new ArrayList<>();

        SQLiteDatabase db;
        DBHelper helper = new DBHelper(getApplicationContext());
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "tb_todo", null);

        if (cursor.moveToFirst()){
            do {
                Todo todo = new Todo(getApplicationContext(), cursor.getPosition(),
                        cursor.getString(cursor.getColumnIndex("content")));
                boolean complete = (cursor.getInt(cursor.getColumnIndex("ischecked"))) == 1;
                todo.setComplete(complete);
                todos.add(todo);

            }while(cursor.moveToNext());
        }

        UserData.getInstance().todos = todos;
    }

    public void loadDiaryList(){

        ArrayList<Diary> diaries = new ArrayList<>();

        DBHelper helper = new DBHelper(getApplicationContext());
        SQLiteDatabase db;
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "tb_diary", null);

        String today = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

        if (cursor.moveToFirst()){
            do {
                Diary diary = new Diary(getApplicationContext(), cursor.getString(cursor.getColumnIndex("title")));
                diary.setContent(cursor.getString(cursor.getColumnIndex("content")));
                diaries.add(diary);
            }while(cursor.moveToNext());
        }

        for (int i = 2; i < 5; i++) {
            boolean isExistToday = false;
            for (Diary diary: diaries) {
                if (diary.title.equals("2020050" + i)) {
                    isExistToday = true;
                }
            }

            if (!isExistToday) {
                Diary diary = new Diary(getApplicationContext(), "2020050" + i);
                diaries.add(diary);
                db.execSQL("insert into tb_diary (title, content) values (?, ?)",
                        new Object[]{diary.title, diary.content});
            }
        }

        //Check if have a diary today
        boolean isExistToday = false;
        for (Diary diary: diaries) {
            if (diary.title.trim().equals(today)) {
                isExistToday = true;
            }
        }
        if (!isExistToday){
            Diary diary = new Diary(getApplicationContext(), today);
            diaries.add(diary);
            db.execSQL("insert into tb_diary (title, content) values (?, ?)",
                    new Object[]{diary.title, diary.content});
        }

        UserData.getInstance().diaries = diaries;
    }

    private class Splashhandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class)); //로딩이 끝난 후, ChoiceFunction 이동
            SplashActivity.this.finish();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
