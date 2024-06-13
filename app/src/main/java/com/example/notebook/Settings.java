package com.example.notebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//Класс для настроек
public class Settings {
    private SharedPreferences sharedPreferences;
    private Editor editor;

    private int positionFont;
    private int sizeFont;
    private int positionTheme;

    private Context context;

    private static final String TAG = "settings";



    public Settings(Context _context){
        context = _context;
        sharedPreferences = context.getSharedPreferences("userSettings", context.MODE_PRIVATE);
    }

    //Getters
    public int getPositionFont(){
        return this.positionFont;
    }

    public int getSizeFont(){
        return this.sizeFont;
    }

    public int getPositionTheme(){
        return this.positionTheme;
    }

    //Setters
    public void setPositionFont(int _positionFont){
        this.positionFont = _positionFont;
    }

    public void setSizeFont(int _sizeFont){
        this.sizeFont = _sizeFont;
    }

    public void setPositionTheme(int _positionTheme){
        this.positionTheme = _positionTheme;
    }

    //sharedPreferences (запись сохраненных настроек)
    public void saveSettings(){
        editor = sharedPreferences.edit();
        editor.putInt("SAVED_POSITION_FONT", getPositionFont());
        editor.putInt("SAVED_SIZE_FONT", getSizeFont());
        editor.putInt("SAVED_POSITION_THEME", getPositionTheme());
        editor.commit();
    }

    //Чтение сохраненных настроек
    public int[] readSettings(){
        int[] savedSettings = new int[3];
        savedSettings[0] = sharedPreferences.getInt("SAVED_POSITION_FONT", 0);
        savedSettings[1] = sharedPreferences.getInt("SAVED_SIZE_FONT", 0);
        savedSettings[2] = sharedPreferences.getInt("SAVED_POSITION_THEME", 0);
        return savedSettings;
    }

    //Экспорт заметок
    public File exportNotes(){
        final String FILE_NAME = "notes.txt";
        FileOutputStream fos;

        Database db = new Database(context);
        String [] notesExport = db.readNotes();


        try{
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);

            for(int x = 0; x < notesExport.length; x++){
                StringBuilder stringBuilder = new StringBuilder(notesExport[x]);
                stringBuilder.append("\n");

                fos.write(stringBuilder.toString().getBytes());
            }
            fos.close();

            File file = new File(context.getFilesDir(), FILE_NAME);



            return file;

        }catch (IOException ex){
            System.out.println(ex.getMessage());
            return null;
        }



    }





}

