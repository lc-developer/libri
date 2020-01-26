package com.example.archiviolibri.Model;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.archiviolibri.Database.dbUtil;
import com.example.archiviolibri.R;

public class LibriAdapter extends CursorAdapter {

    public LibriAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.item_layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView titoloTV = (TextView) view.findViewById(R.id.titoloTextView);
        TextView autoreTV = (TextView) view.findViewById(R.id.autoreTextView);
        TextView genereTV = (TextView) view.findViewById(R.id.genereTextView);
        TextView editoreTV = (TextView) view.findViewById(R.id.editoreTextView);
        ImageView avatarIV = (ImageView) view.findViewById(R.id.avatarImaveView);

        int titolo = cursor.getColumnIndex(dbUtil.KEY_TITOLO);
        int autore = cursor.getColumnIndex(dbUtil.KEY_AUTORE);
        int genere = cursor.getColumnIndex(dbUtil.KEY_GENERE);
        int editore = cursor.getColumnIndex(dbUtil.KEY_EDITORE);

        titoloTV.setText(cursor.getString(titolo));
        autoreTV.setText(cursor.getString(autore));
        genereTV.setText(cursor.getString(genere));
        editoreTV.setText(cursor.getString(editore));

        switch (cursor.getString(genere)){
            case "Romanzo":
                avatarIV.setImageResource(R.drawable.avatar_romanzo);
                break;
            case "Saggio":
                avatarIV.setImageResource(R.drawable.avatar_saggio);
                break;
            case "Poesia":
                avatarIV.setImageResource(R.drawable.avatar_poesia);
                break;
            case "Fumetto":
                avatarIV.setImageResource(R.drawable.avatar_fumetto);
                break;
            case "Fiaba":
                avatarIV.setImageResource(R.drawable.avatar_fiaba);
                break;
            case "Altro":
                avatarIV.setImageResource(R.drawable.avatar_altro);
                break;
            default:
                avatarIV.setImageResource(R.drawable.avatar_altro);
                break;
        }

        /*if (cursor.getCount() != 0) {

            cursor.moveToFirst();

            while (cursor.moveToNext()) {

                titoloTV.setText(cursor.getString(titolo));
                autoreTV.setText(cursor.getString(autore));
                genereTV.setText(cursor.getString(genere));
                editoreTV.setText(cursor.getString(editore));

            }
        }*/

        //cursor.close(); //??????


    }
}
