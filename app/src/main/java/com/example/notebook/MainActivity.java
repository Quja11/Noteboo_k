package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Removable{

    //Динамический массив для заметок
    private ArrayList<Note> notes = new ArrayList<>();
    private Database db;
    private ExtraAdapter extraAdapter;

    private ListView notesList;
    private FloatingActionButton addNote_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void init(){
        notesList = findViewById(R.id.notesList);
        addNote_btn = findViewById(R.id.add_btn);

        //Заполняем дин.массив объектами Note
        db = new Database(getBaseContext());
        db.readNotes(notes);

        //Вывод списка заметок на экран
        printList();
        Log.i("SSSS", "onCreate");
    }

    public void printList(){
        extraAdapter = new ExtraAdapter(this, notes);
        notesList.setAdapter(extraAdapter);

        //Подключаем слушатель короткого нажатия для перехода в редактирование текста
        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle args = new Bundle();
                args.putString("EDIT_TEXT", notes.get(i).getMessage());
                args.putInt("ID", i);

                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtras(args);

                Log.i("QQQ", notes.get(i).getMessage());

                startActivity(intent);
            }
        });

        //Подключаем слушатель длинного нажатия для вызова диалогового окна (удаление, копирование заметки)
        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                Bundle args = new Bundle();
                args.putInt("POSITION", i);

                CustomDialogFragment dialogWindow = new CustomDialogFragment();
                dialogWindow.setArguments(args);
                dialogWindow.show(getSupportFragmentManager(), "custom");



                return true;
            }
        });

    }





    public void addNote(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.new_note);

        mediaPlayer.setLooping(false);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();
            }
        });

        mediaPlayer.start();

        Intent intent = new Intent(this, CreateNoteActivity.class);
        startActivity(intent);
    }






    //Меню и его методы
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        switch(id){
            case R.id.item_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.item_info:
                intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void remove(int id) {
        Log.i("CURRET", String.valueOf(id));
        notes.remove(id);
        extraAdapter.notifyDataSetChanged();
    }








}