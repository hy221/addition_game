package com.applicationcommunity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;

//使ってない。。。。。。。

public class Delete_AlertDialog extends DialogFragment {

    private ToDoListDB helper = null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle args = Objects.requireNonNull(requireArguments());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder.setTitle("削除しますか？")
                .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        args.putInt("key",2);
                        setArguments(args);

                    }
                })

                .setNeutralButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        args.putInt("key",1);
                        setArguments(args);
                    }
                })

                .create();
    }
}
