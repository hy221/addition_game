package com.addition_game;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Result_Database extends SQLiteOpenHelper {

    static final private String DBname = "result_database";
    static final private int version = 1;

    public Result_Database(Context context) {
        super(context, DBname, null, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE resulttable ("+ " date TEXT PRIMARY KEY,resultnum INTEGER,time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS resulttable");
        onCreate(db);
    }
}
