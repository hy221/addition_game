package com.applicationcommunity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PassWordManaDB extends SQLiteOpenHelper {

    static final private String DBNAME = "PassWordMana.db";
    static final private int VERSION = 1;

    public PassWordManaDB(Context context) {
        super(context, DBNAME, null,VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PassMana (Password TEXT PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PassWordMana.db");
        onCreate(db);
    }
}
