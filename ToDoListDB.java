package com.applicationcommunity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//SQLiteデータベース専用クラス
//テーブルが存在しない場合は新規作成する。テーブルが存在している場合は開くだけ
public class ToDoListDB extends SQLiteOpenHelper {
    static final private String DBNAME = "todolist.db";
    static final private int VERSION  = 1;

    //コンストラクター(引数：context コンテキスト　name:データベースファイル名　factory:カーソルのファクトリー　version:バージョン番号)
    public ToDoListDB(Context context){
            super(context,DBNAME,null,VERSION);
    }

    //データベースを開く
    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    //データベースを作成
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE todolist (" + " now TEXT PRIMARY KEY,title TEXT,date TEXT,time TEXT,detail TEXT)");
    }

    //データベースをアップグレード
    @Override
    public  void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS todolist");
        onCreate(db);
    }
}
