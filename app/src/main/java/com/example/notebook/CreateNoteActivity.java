package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import java.time.LocalDateTime;

public class CreateNoteActivity extends AppCompatActivity {
    private Database db;
    private Note note;
    private EditText editText;
    private Settings settings;


    private static final String TAG = "CURRENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        init();


        setSavedValue();





    }

    private void init(){
        settings = new Settings(getBaseContext());
        editText = findViewById(R.id.message);

    }

    private void setSavedValue(){

        int[] savedSettings = settings.readSettings();
        int positionFont = savedSettings[0];
        int textSize = savedSettings[1];

        Typeface typeface;

        switch(positionFont){
            case 0: typeface = getResources().getFont(R.font.babayka_font);break;
            case 1: typeface = getResources().getFont(R.font.cakra);break;
            case 2: typeface = getResources().getFont(R.font.hight);break;
            case 3: typeface = getResources().getFont(R.font.segoescript);break;

            default: typeface = getResources().getFont(R.font.babayka_font);
        }

        Log.i(TAG, String.valueOf(positionFont));
        Log.i(TAG, String.valueOf(typeface));

        editText.setTypeface(typeface);
        editText.setTextSize(textSize);
    }



    //Метод из xmlki Onclick
    public void saveNote(View view) {
        String message = editText.getText().toString();
        String date = getCurrentDate();
        note = new Note(message, date);

        System.out.println("Значение message: " + message);

        if(message != null && !message.trim().isEmpty()){
            db = new Database(getBaseContext());
            db.insertNote(note.getMessage(), note.getDate());

            Toast toast = Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(this, "Смысл пустого текста, мэн?", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public String getCurrentDate(){
        LocalDateTime ldt = LocalDateTime.now();
        String day = String.valueOf(ldt.getDayOfMonth());
        String month = String.valueOf(ldt.getMonth()).substring(0, 3);
        String year = String.valueOf(ldt.getYear());

        String hour = String.valueOf(ldt.getHour());
        String minutes = String.valueOf(ldt.getMinute());
        if(Integer.parseInt(minutes) < 15){
            minutes = "0" + minutes;
        }

        String time = hour + ":" + minutes;

        String result = String.format("%s %s. %s, %s", day, month, year, time);

        return result;
    }






















}