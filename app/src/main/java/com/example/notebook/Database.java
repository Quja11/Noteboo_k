package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.FileOutputStream;
import java.util.ArrayList;


//Класс БД для CRUD
public class Database extends AppCompatActivity{

    SQLiteDatabase db;
    Cursor cursorNotes;

    //Начальная инициализация БД
    public Database(Context context){
        db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        //db.execSQL("DROP TABLE IF EXISTS notes");
        db.execSQL("CREATE TABLE IF NOT EXISTS notes(id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT, date TEXT)");
    }

    //Чтение для списка (ListView)
    public void readNotes(ArrayList<Note> _notes){
        cursorNotes = db.rawQuery("SELECT * FROM notes", null);

        while(cursorNotes.moveToNext()){
            String id = String.valueOf(cursorNotes.getInt(0));
            String message = cursorNotes.getString(1);
            String date = cursorNotes.getString(2);
            _notes.add(new Note(message, date));
        }
        cursorNotes.close();
        db.close();
    }

    //Чтение для экспорта файла (получение каждой строки в массиве)
    public String[] readNotes(){
        cursorNotes = db.rawQuery("SELECT * FROM notes", null);
        int countNotes = cursorNotes.getCount();
        String notes[] = new String[countNotes];

        int numberLine = 0;
        while(cursorNotes.moveToNext()){
            String id = String.valueOf(cursorNotes.getInt(0));
            String message = cursorNotes.getString(1);
            String date = cursorNotes.getString(2);

            notes[numberLine] = message;
            numberLine++;
        }

        cursorNotes.close();
        db.close();

        return notes;
    }

    public int convertId(int idFromList){

        cursorNotes = db.rawQuery("SELECT * FROM notes", null);
        
        int idFromDB = 0;
        boolean isNotEmpty = cursorNotes.moveToPosition(idFromList);
        
        if(isNotEmpty){
            idFromDB = cursorNotes.getInt(0);
        }
        return idFromDB;
    }



    //Обновление данных (Update)
    public void updateNote(String _message, int _id){
        String query = String.format("UPDATE notes SET message = '%s' WHERE id = '%d'", _message, _id);
        db.execSQL(query);
        db.close();
    }


    //Вставка данных (CREATE)
    public void insertNote(String _message, String _date){
        String query = String.format("INSERT OR IGNORE INTO notes (message, date) VALUES ('%s', '%s')", _message, _date);
        db.execSQL(query);
        db.close();
    }

    //Удаление (DELETE)
    public void deleteNote(int _idFromDB){
        String query = String.format("DELETE FROM notes WHERE id = '%d'", _idFromDB);
        Log.i("QWE", "уДАЛЕНИЕ УСПЕШНО");
        db.execSQL(query);
        db.close();
    }
























}


