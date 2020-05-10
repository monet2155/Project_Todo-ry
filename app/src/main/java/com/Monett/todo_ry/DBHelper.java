package com.Monett.todo_ry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static  int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, "todorydb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create TABLE
        db.execSQL("create table if not exists " + "tb_todo" +
                "(_id integer primary key autoincrement," +
                "ischecked integer," +
                "content text)");

        // TODO: 2020-05-04 MAKE DIARY TABLE
        db.execSQL("create table if not exists " + "tb_diary" +
                "(_id integer primary key autoincrement," +
                "title text," + "content text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == DATABASE_VERSION){
            db.execSQL("drop table if exists "+ "tb_todo");
            db.execSQL("drop table if exists "+ "tb_diary");
            onCreate(db);
        }
    }
}
