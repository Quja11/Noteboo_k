package com.example.notebook;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExtraAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ArrayList<Note> objects;

    public ExtraAdapter(Context _context, ArrayList<Note> _products) {
        context = _context;
        objects = _products;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.layout_list_view, parent, false);
        }

        Note note = getNote(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        TextView messageView = (TextView) view.findViewById(R.id.messageView);
        TextView dateView = (TextView) view.findViewById(R.id.dateView);

        messageView.setText(note.getMessage());
        dateView.setText(note.getDate());

        return view;
    }

    // заметка по позиции
    public Note getNote(int position) {
        return ((Note) getItem(position));
    }







}
