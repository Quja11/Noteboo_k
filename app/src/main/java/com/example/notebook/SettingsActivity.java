package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class SettingsActivity extends AppCompatActivity implements Animationable{

    //переменные ресурсов
    String[] fonts;
    String[] themes;

    //переменные статичных view
    TextView fontView;
    TextView sizeView;
    TextView themeView;
    TextView exportView;


    //переменные для view значений настроек
    TextView fontValueView;
    TextView sizeFontView;
    TextView themeValueView;

    //переменные для spinner, seekbar, imageView
    Spinner fontSpinner;
    SeekBar seekBar;
    Spinner themeSpinner;
    ImageView exportImageView;

    //переменные для кнопок
    Button saveSettings_btn;


    //Переменные для настроек
    Settings settings;

    //Переменные интентов для возвращения "назад"
    Intent intentBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initialization();

        setFontSpinner();
        setSeekBar();
        setThemeSpinner();

        setSavedValue();

    }

    private void initialization(){

        settings = new Settings(getBaseContext());

        fontView = findViewById(R.id.fontView);
        sizeView = findViewById(R.id.sizeView);;
        themeView = findViewById(R.id.themeView);;
        exportView = findViewById(R.id.exportView);

        fontValueView = (TextView) findViewById(R.id.fontValueView);
        sizeFontView = (TextView) findViewById(R.id.sizeFontView);
        themeValueView = (TextView) findViewById(R.id.themeValueView);

        fontSpinner = (Spinner) findViewById(R.id.font_spinner);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        themeSpinner = (Spinner) findViewById(R.id.themes_spinner);

        exportImageView = findViewById(R.id.export_ImageView);
        saveSettings_btn = (Button) findViewById(R.id.saveSettings_btn);

        //Анимация
        View[] views = {fontView, sizeView, themeView, exportView, fontValueView, sizeFontView, themeValueView, fontSpinner, seekBar, themeSpinner, exportImageView, saveSettings_btn};
        for(View view : views){
            animate(view);
        }

        fonts = getResources().getStringArray(R.array.fonts_array);
        themes = getResources().getStringArray(R.array.themes_array);
    }

    private void setSavedValue(){
        int[] savedSettings = settings.readSettings();

        int savedPositionFont = savedSettings[0];
        int savedSizeFont = savedSettings[1];
        int savedPositionTheme = savedSettings[2];

        System.out.println(savedPositionFont + savedSizeFont);

        fontValueView.setText(fonts[savedPositionFont]);
        sizeFontView.setText(String.valueOf(savedSizeFont));
        themeValueView.setText(themes[savedPositionTheme]);

        fontSpinner.setSelection(savedPositionFont);
        seekBar.setProgress(savedSizeFont);
        themeSpinner.setSelection(savedPositionTheme);
    }


    //Установка выпадающего списка шрифтов
    public void setFontSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                fonts
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String font = (String)parent.getItemAtPosition(position);
                settings.setPositionFont(position);
                fontValueView.setText(font);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        fontSpinner.setOnItemSelectedListener(itemSelectedListener);
    }

    //Установка полосы прокрутки размера шрифта
    public void setSeekBar(){

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sizeFontView.setText(String.valueOf(progress));
                settings.setSizeFont(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
    }

    //Установка выпадающего списка тем
    public void setThemeSpinner(){

        ArrayAdapter<String> adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                themes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                String theme = (String)parent.getItemAtPosition(position);
                settings.setPositionTheme(position);
                themeValueView.setText(theme);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        themeSpinner.setOnItemSelectedListener(itemSelectedListener);
    }




    //Экспорт всех заметок
    public void exportNotes(View view){
        File resultFile = settings.exportNotes();
        Uri uriFile = FileProvider.getUriForFile(this, "com.example.fileprovider", resultFile);


        Intent intent = ShareCompat.IntentBuilder.from(this)
                .setType("application/txt")
                .setStream(uriFile)
                .setChooserTitle("Выбор приложения")
                .createChooserIntent()
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent);


        //Toast.makeText(this, String.valueOf(resultFile.getName()), Toast.LENGTH_SHORT).show();




    }


    //Сохранение текущих настроек
    public void saveSettings(View view){
        settings.saveSettings();
        Toast.makeText(this, "Настройки успешно сохранены", Toast.LENGTH_SHORT).show();
        intentBack = new Intent(this, MainActivity.class);
        startActivity(intentBack);
    }


    @Override
    public void animate(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_animation);
        view.startAnimation(animation);
    }







}
