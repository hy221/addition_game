


package com.applicationcommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ToDoListDB helper = null;
    private int position = 0;
    ArrayList<ListItem> data;
    ListAdapter adapter;
    private String [] title_cols = {"title","date","detail","now"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button_new = findViewById(R.id.button_new);
        listView = findViewById(R.id.listview);

        //＋ボタンクリック処理
        button_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),com.applicationcommunity.ToDoListDetail.class);
                startActivity(intent);
            }
        });


    //}
        helper = new ToDoListDB(this);

        //data = new ArrayList<>();

        //ListViewに値をセットするためのアダプター
        //adapter = new ListAdapter(this,data,R.layout.list_items);

        //DBに登録されている値(列指定)を全て取得し、ListViewに表示させる
        /*try(SQLiteDatabase db = helper.getReadableDatabase()){
            Cursor cs = db.query("todolist",title_cols,null,null,null,null,null,null);
            boolean boo = cs.moveToFirst();
            while(boo){
                //dbから取得した値をListItemオブジェクトに詰め替え
                ListItem item = new ListItem();
                item.setId((new Random()).nextLong());
                item.setTitle(cs.getString(0));
                item.setDate(cs.getString(1));
                item.setDetail(cs.getString(2));
                item.setKey(cs.getString(3));
                data.add(item);
                boo = cs.moveToNext();

            }
        }*/

        //ListViewに登録
        //listView.setAdapter(adapter);

        //リストをタップしたときの処理
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //タップされた値を取得し、インテントにてToDoListDBクラスに送る
                Intent intent = new Intent(getApplicationContext(),com.applicationcommunity.ToDoListDetail.class);
                intent.putExtra("key",adapter.getItemKey(position));
                startActivity(intent);

            }
        });


        //リスト長押し処理
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //タップされたpositionをsetPosにセット
                setPos(position);
                alertCheck(adapter.getItemKey(position),data,adapter);

               /* String [] key = {adapter.getItemKey(position)};
                try(SQLiteDatabase db = helper.getWritableDatabase()){
                    //dbから値を削除
                    db.delete("todolist","now=?",key);
                    //ArrayListからも削除する
                    data.remove(adapter.getItem(position));
                    //ListView更新
                    adapter.notifyDataSetChanged();
                    Toast toast = Toast.makeText(getApplicationContext(),"削除しました",Toast.LENGTH_SHORT);
                    toast.show();
                }*/
                //リストタップを検知させるにはfalseを指定。今回は長押しのみ検知させたいためtrueにしてる
                return true;
            }
        });

    }


    //onStart()
    @Override
    protected void onStart() {
        super.onStart();

        data = new ArrayList<>();
        adapter = new ListAdapter(this,data,R.layout.list_items);

        try(SQLiteDatabase db = helper.getReadableDatabase()){
            Cursor cs = db.query("todolist",title_cols,null,null,null,null,null,null);
            boolean boo = cs.moveToFirst();
            while(boo){
                //dbから取得した値をListItemオブジェクトに詰め替え
                ListItem item = new ListItem();
                item.setId((new Random()).nextLong());
                item.setTitle(cs.getString(0));
                item.setDate(cs.getString(1));
                item.setDetail(cs.getString(2));
                item.setKey(cs.getString(3));
                data.add(item);
                boo = cs.moveToNext();

            }
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy(){
        helper.close();
        super.onDestroy();

    }


    //リストが長押しされると警告ダイアログが表示される
    //引数にタップされたkeyの値やらアダプターのインスタントやらを受け取るけど、フィールドに定義してもよかったかも
    private void alertCheck(String keyval,ArrayList<ListItem> data,ListAdapter la){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("削除しますか？");  //タイトルをセット
        alert.setPositiveButton("はい", new DialogInterface.OnClickListener() {  //Yesボタン周り
            @Override
            public void onClick(DialogInterface dialog, int which) { //「はい」を選択したときの処理
                String [] key = {keyval};
                try(SQLiteDatabase db = helper.getReadableDatabase()){
                    //dbから値を削除
                    db.delete("todolist","now=?",key);
                }
                //ArrayListからも削除する
                data.remove(la.getItem(getPos()));
                //ListView更新
                la.notifyDataSetChanged();
                Toast toast = Toast.makeText(getApplicationContext(),"削除しました",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        alert.setNeutralButton("キャンセル", new DialogInterface.OnClickListener() { //キャンセルボタン周り
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //特に処理は不要なので何も記述しない
            }
        });
        alert.show();
    }

    private  void setPos(int pos){
        position = pos;
    }

    private int getPos(){
        return position;
    }


}