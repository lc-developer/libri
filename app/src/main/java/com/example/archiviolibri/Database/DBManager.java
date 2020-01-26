package com.example.archiviolibri.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.archiviolibri.MainActivity;
import com.example.archiviolibri.Model.LibriAdapter;
import com.example.archiviolibri.Model.Libro;
import com.example.archiviolibri.R;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public boolean isEditmode;

    public boolean getEditmode() {
        return isEditmode;
    }

    public void setEditmode(boolean editmode) {
        isEditmode = editmode;
    }

    public DBManager(@Nullable Context context) {
        super(context, dbUtil.DATABASE_NAME, null, dbUtil.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(dbUtil.SQL_CREATE_TABLE_LIBRI);

        //onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL(dbUtil.SQL_DELETE_TABLE_LIBRI);

        onCreate(db);

    }

    public void aggiornaRecord(ContentValues values, long id){
        SQLiteDatabase db = this.getWritableDatabase();

        //ContentValues newValues = new ContentValues();

        values.put(dbUtil.KEY_TITOLO, values.getAsString(dbUtil.KEY_TITOLO));
        values.put(dbUtil.KEY_AUTORE, values.getAsString(dbUtil.KEY_AUTORE));
        values.put(dbUtil.KEY_GENERE, values.getAsString(dbUtil.KEY_GENERE));
        values.put(dbUtil.KEY_EDITORE, values.getAsString(dbUtil.KEY_EDITORE));

        long pippo = db.update(dbUtil.TABLE_NAME, values, "WHERE _id = " + dbUtil.KEY_ID, null);

        Log.d("database", "pippo " + pippo + "id " + id);


    }

    public void salvaNuovoRecord(ContentValues values)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(dbUtil.KEY_TITOLO, values.getAsString(dbUtil.KEY_TITOLO));
        values.put(dbUtil.KEY_AUTORE, values.getAsString(dbUtil.KEY_AUTORE));
        values.put(dbUtil.KEY_GENERE, values.getAsString(dbUtil.KEY_GENERE));
        values.put(dbUtil.KEY_EDITORE, values.getAsString(dbUtil.KEY_EDITORE));

        long newRowID = db.insert(dbUtil.TABLE_NAME, null, values);

        if( newRowID == -1 ) {
            Log.d("database", "DATABASE ERROR");
        } else {
            Log.d("database", "E' stato inserito? " + newRowID);
        }

        //Toast.makeText(this, "Questo è il nuovo id " + newRowID, Toast.LENGTH_LONG).show();

        //Log.d("database", "E' stato inserito? " + newRowID);

    }

    public Libro leggiRecord(long id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(dbUtil.TABLE_NAME, new String[] {dbUtil.KEY_ID, dbUtil.KEY_TITOLO,
                                    dbUtil.KEY_AUTORE, dbUtil.KEY_GENERE, dbUtil.KEY_EDITORE}, dbUtil.KEY_ID +"=?",
                                    new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

            Libro libro = new Libro();
            libro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbUtil.KEY_ID))));
            libro.setTitolo(cursor.getString(cursor.getColumnIndex(dbUtil.KEY_TITOLO)));
            libro.setAutore(cursor.getString(cursor.getColumnIndex(dbUtil.KEY_AUTORE)));
            libro.setGenere(cursor.getString(cursor.getColumnIndex(dbUtil.KEY_GENERE)));
            libro.setEditore(cursor.getString(cursor.getColumnIndex(dbUtil.KEY_EDITORE)));

        return libro;
    }

    public List<Libro> getTuttiLibri(){

        SQLiteDatabase db = this.getWritableDatabase();

        List<Libro> listaLibri = new ArrayList<>();

        Cursor cursor = db.query(dbUtil.TABLE_NAME, new String[] {dbUtil.KEY_ID, dbUtil.KEY_TITOLO,
                dbUtil.KEY_AUTORE, dbUtil.KEY_GENERE, dbUtil.KEY_EDITORE},
                null, null, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                Libro libro = new Libro();
                libro.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbUtil.KEY_ID))));
                libro.setTitolo(cursor.getString(cursor.getColumnIndex(dbUtil.KEY_TITOLO)));
                libro.setAutore(cursor.getString(cursor.getColumnIndex(dbUtil.KEY_AUTORE)));
                libro.setGenere(cursor.getString(cursor.getColumnIndex(dbUtil.KEY_GENERE)));
                libro.setEditore(cursor.getString(cursor.getColumnIndex(dbUtil.KEY_EDITORE)));

                //Aggiungere alla listView

                listaLibri.add(libro);

            } while (cursor.moveToNext());
        }

        return listaLibri;
    }


    public Cursor getItemID(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(dbUtil.SQL_SELECT_ALL, null);


        /*listView.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String g = "";
            }
        });*/

        return cursor;

    }

    public void caricaDati() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(dbUtil.SQL_SELECT_ALL, null);

        int titolo = cursor.getColumnIndex(dbUtil.KEY_TITOLO);
        int autore = cursor.getColumnIndex(dbUtil.KEY_AUTORE);
        int genere = cursor.getColumnIndex(dbUtil.KEY_GENERE);
        int editore = cursor.getColumnIndex(dbUtil.KEY_EDITORE);

        String totale = "";
        String record = "";

        if (cursor.getCount() != 0) {

            cursor.moveToFirst();

            while (cursor.moveToNext()) {
                record = cursor.getString(titolo) + ", "
                        + cursor.getString(autore) + ", "
                        + cursor.getString(genere) + ", "
                        + cursor.getString(editore) + "\n";

                totale = totale + record;
                //totale = totale + data.getString(1) + "\n";
                //Log.d("database", "Posizione? " + data.getPosition());
            }
        }

        cursor.close();

        Log.d("database", "quanti dati? " + cursor.getCount());

        //return totale;

    }

}
