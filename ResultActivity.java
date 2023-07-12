package com.addition_game;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private Result_Database helper = null;
    private String[] cols = {"date","resultnum","time"};
    String total = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultactivity);


        TextView textView = findViewById(R.id.textView5);
        helper = new Result_Database(this);

        try (SQLiteDatabase db = helper.getWritableDatabase()){
            Cursor cs = db.query("resulttable",null,null,null,
                    null,null,null,null);
            boolean b = cs.moveToFirst();
            while (b) {
                String date = cs.getString(0).substring(0,10);
                String result;
                result= date +"      "+ cs.getString(2) + "  " + cs.getString(1) + "\n";
                total += result;
                textView.setText(total);
                b = cs.moveToNext();
            }
        }

    }

}
