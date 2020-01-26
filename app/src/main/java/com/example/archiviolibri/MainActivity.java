package com.example.archiviolibri;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.example.archiviolibri.Database.DBManager;
import com.example.archiviolibri.Database.dbUtil;
import com.example.archiviolibri.Model.LibriAdapter;
import com.example.archiviolibri.Model.Libro;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    DBManager libriDB;
    Libro libroAttuale;
    TextView risultato;
    ListView listView;
    ListView itemsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        libriDB = new DBManager(this);
        listView = (ListView) findViewById(R.id.listView);

        caricaListView(listView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long item) {
                Toast.makeText(MainActivity.this, "Item selected" + item, Toast.LENGTH_LONG).show();

                libroAttuale = libriDB.leggiRecord(item);
                modificaLibro(libroAttuale);

            }
        });





        /////////////////////////
        // risultato = (TextView) findViewById(R.id.hello);


        //SQLiteDatabase db = libriDB.getReadableDatabase();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), GestioneActivity.class);
                startActivity(intent);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
            }
        });

        /////////////////////////
        /*Button vediamo = (Button) findViewById(R.id.vediamo);
        vediamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                risultato.setText(libriDB.caricaDati());

            }
        });*/

    } //FINE ON CREATE

    private void modificaLibro(Libro libro){

        //libriDB.setEditmode(true);
        Log.d("dadabase","ho cliccato sull item" );


        Intent intent = new Intent(getApplicationContext(), ModificaActivity.class);


        intent.putExtra("ID", libro.getId());
        intent.putExtra("TITOLO",libro.getTitolo());
        intent.putExtra("AUTORE",libro.getAutore());
        intent.putExtra("GENERE",libro.getGenere());
        intent.putExtra("EDITORE",libro.getEditore());

        startActivity(intent);

    }


    private void selezionaItem(){

        SQLiteDatabase db = libriDB.getWritableDatabase();

        Cursor cursor = db.rawQuery(dbUtil.SQL_SELECT_ALL, null);

        /*ListAdapter adapter = new ArrayAdapter<>(this, cursor);
        itemsListView.setAdapter(adapter);*/

        /*itemsListView.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String g = "";
            }
        });*/

    }

    @Override
    protected void onResume() {
        caricaListView(listView);
        super.onResume();
    }

    /*private void caricaDati() {

        TextView mostra = (TextView) findViewById(R.id.hello);

        SQLiteDatabase db = libriDB.getWritableDatabase();

        //db.execSQL(dbUtil.SQL_SELECT_ALL);

        Cursor data = db.rawQuery(dbUtil.SQL_SELECT_ALL, null);

        String totale = "";

        if (data.getCount() != 0) {
            while (data.moveToNext()) {
                totale = totale + data.getString(1) + "\n";
            }
        }
        mostra.setText(totale);

        Log.d("database", "quanti dati? " + data.getCount());

    }*/

    private void caricaListView(ListView listView){

        SQLiteDatabase db = libriDB.getWritableDatabase();

        Cursor cursor = db.rawQuery(dbUtil.SQL_SELECT_ALL, null);

        LibriAdapter adapter = new LibriAdapter(this, cursor);

        listView.setAdapter(adapter);
    }

    /*private void inseriscDatiTest() {
        SQLiteDatabase db = libriDB.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(dbUtil.KEY_TITOLO, "Il barone rampante");
        values.put(dbUtil.KEY_AUTORE, "Italo Calvino");
        values.put(dbUtil.KEY_GENERE, "Romanzo");
        values.put(dbUtil.KEY_EDITORE, "Mondadori");

        long newRowID = db.insert(dbUtil.TABLE_NAME, null, values);

        if( newRowID == -1 ) {
            Toast.makeText(MainActivity.this, "DATABASE ERROR" + newRowID, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Questo è il nuovo id " + newRowID, Toast.LENGTH_LONG).show();
        }


        //Log.d("database", "" + newRowID);

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.cancellaTutto){

            AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
            miaAlert.setTitle("Attenzione");
            miaAlert.setMessage("Sei scicuro di volere cancellate tutto?");
            miaAlert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    SQLiteDatabase db = libriDB.getWritableDatabase();

                    libriDB.onUpgrade(db,1, 1);

                    libriDB.caricaDati();

                    caricaListView(listView);

                }
            });
            miaAlert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alert = miaAlert.create();
            alert.show();

        }


        /*switch (id) {
            case R.id.cancellaTutto:

                AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
                miaAlert.setTitle("Attenzione");
                miaAlert.setMessage("Sei scicuro di volere cancellate tutto?");
                miaAlert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SQLiteDatabase db = libriDB.getWritableDatabase();

                        libriDB.onUpgrade(db,1, 1);

                        libriDB.caricaDati();

                    }
                });
                miaAlert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alert = miaAlert.create();
                alert.show();



                break;

            case R.id.testDatabase:

                inseriscDatiTest();
                break;

            case R.id.caricaDati:
                risultato.setText(libriDB.caricaDati());
                break;

            default:
                break;
        }*/

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings)
//            return true;
//        if (id == R.id.testDatabase)
//            return false;

        return super.onOptionsItemSelected(item);
    }


}
