package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditNoteActivity extends AppCompatActivity {
    private Database db;
    private Settings settings;
    private EditText editText;
    private Bundle args;
    private String text;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        init();
        editText.setText(text);
    }

    private void init(){
        editText = findViewById(R.id.editMessage);
        db = new Database(this);
        settings = new Settings(this);

        args = getIntent().getExtras();
        text = args.getString("EDIT_TEXT").toString();
        id = args.getInt("ID");

        setSavedValue();
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

        editText.setTypeface(typeface);
        editText.setTextSize(textSize);
    }






















    public void saveEditNote(View view){
        String newValueText = editText.getText().toString();
        int id = this.id + 1; //В бд с 1 строки, а в listView с 0

        db.updateNote(newValueText, id);

        Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
        startActivity(intent);
    }


}