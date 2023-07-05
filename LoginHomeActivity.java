
package com.applicationcommunity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class LoginHomeActivity extends AppCompatActivity {

    private PassWordManaDB helper = null;
    public String [] cols = {"Password"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginhome);

        Button button_login = findViewById(R.id.button_login);
        Button button_insert = findViewById(R.id.button_insert);
        TextView textview_error = findViewById(R.id.textView_error);
        EditText editText_inputpass = findViewById(R.id.editText_inputpass);
        Button button_reset = findViewById(R.id.button_reset);

        //ヘルパーのサブクラスをインスタンス化
        helper = new PassWordManaDB(this);

        //取得する列を配列型で受け取る
        //列"Password"を指定し、値が一つ以上あれば新規登録ボタンを非表示にする
        try(SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.query("PassMana",cols,null,null,null,null,null)){
                if(cursor.getCount() > 0){
                    button_insert.setVisibility(View.INVISIBLE);
                }
        }

        //ログインボタン押下時の処理。
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //入力された値がDBに登録されている値と一致すれば画面遷移する
                String [] selectionargs = {editText_inputpass.getText().toString()};//パスワード入力欄の値を取得して配列に格納
                try(SQLiteDatabase db = helper.getReadableDatabase()){
                    Cursor cursor = db.query("PassMana",cols,"Password=?",selectionargs,null,null,null);

                    //ログイン成功！
                    if(cursor.moveToFirst()){
                        textview_error.setText("");
                        editText_inputpass.setText("");
                        Intent intent = new Intent(getApplicationContext(),com.applicationcommunity.MainActivity.class);
                        startActivity(intent);

                    //新規登録ボタンが表示されている場合は通知を表示
                    }else if(button_insert.getVisibility()==View.VISIBLE) {
                        textview_error.setText("パスワードを登録してください。");

                    //ログイン失敗。。
                    }else{
                        textview_error.setText("ログインに失敗しました。正しいパスワードを入力してください。");
                    }
                }
            }
        });

        //新規登録ボタン押下時の処理。入力されたパスワードをDBに登録
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //文字数が〇文字以内は登録できない
                if(editText_inputpass.getText().toString().length() < 3){

                    textview_error.setText("3文字以上で登録してください。");

                }else {

                    try (SQLiteDatabase db = helper.getWritableDatabase()) {
                        ContentValues values = new ContentValues();
                        values.put("Password", editText_inputpass.getText().toString());
                        db.insert("PassMana", null, values);
                    }

                    //EditText欄に入力された値をクリア
                    editText_inputpass.setText("");
                    button_insert.setVisibility(View.INVISIBLE);
                    Toast toast = Toast.makeText(getApplicationContext(), "登録しました！", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });
        //リセットボタン※長押し
        button_reset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try(SQLiteDatabase db = helper.getWritableDatabase()){
                    db.delete("PassMana",null,null);
                    Toast toast = Toast.makeText(getApplicationContext(),"パスワードを初期化しました。",Toast.LENGTH_SHORT);
                    toast.show();
                    editText_inputpass.setText("");
                    textview_error.setText("");
                    button_insert.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

    }

    //onDestroy()呼び出し時にデータベースを閉じる
    @Override
    protected void onDestroy() {
        super.onDestroy();
        helper.close();
    }

}
