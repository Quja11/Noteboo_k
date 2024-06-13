package com.example.notebook;

//Класс для заметки
public class Note{
    private String message;
    private String date;

    public Note(String _message, String _date){
        setMessage(_message);
        setDate(_date);
    }

    public String getMessage(){
        return this.message;
    }

    public String getDate(){
        return this.date;
    }

    public void setMessage(String _message){
        this.message = _message;
    }

    public void setDate(String _date){
        this.date = _date;
    }

}
