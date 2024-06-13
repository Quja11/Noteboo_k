package com.example.notebook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomDialogFragment extends DialogFragment {

    private Removable removable;

    private int position;
    private Database db;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        removable = (Removable) context;
    }



    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        position = args.getInt("POSITION");

        AlertDialog.Builder builderDialog = new AlertDialog.Builder(getContext());

        builderDialog.setTitle(String.format("Заметка № %d", position));
        builderDialog.setMessage("Вы хотите удалить эту заметку?");
        builderDialog.setNegativeButton("Отмена", null);






        builderDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                db = new Database(getContext());
                int idFromDB = db.convertId(position);

                Log.i("nice", String.valueOf(idFromDB));
                db.deleteNote(idFromDB);

                //Динамическое обновление данных
                removable.remove(position);


                //Удалить элемент из ArrayList
                //Вызвать метод у адаптера notifyDataChanged?
            }
        });






        Dialog resultDialog = builderDialog.create();

        return resultDialog;
    }



















}
