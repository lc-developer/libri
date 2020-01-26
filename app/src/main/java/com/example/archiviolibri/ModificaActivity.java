package com.example.archiviolibri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.archiviolibri.Database.DBManager;
import com.example.archiviolibri.Database.dbUtil;

public class ModificaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText titoloET;
    private EditText autoreET;
    private Spinner genereSP;
    private EditText editoreET;

    private DBManager libriDB;
    private Button buttonSalva;

    private long idModifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica);

        setTitle("Modifica libro");

        libriDB = new DBManager(this);

        titoloET = (EditText) findViewById(R.id.titoloEditText);
        autoreET = (EditText) findViewById(R.id.autoreEditText);
        genereSP = (Spinner) findViewById(R.id.genereSpinner);
        editoreET = (EditText) findViewById(R.id.editoreEditText);
        preparaSpinner();

        buttonSalva = (Button) findViewById(R.id.buttonSalva);

        buttonSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateData();
                libriDB.caricaDati();

            }
        });


        Log.d("database", "sono dentro edit mode");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String genere = extras.getString("GENERE");
        int position = 0;

        switch (genere) {
            case "Romanzo":
                position = 0;
                break;
            case "Saggio":
                position = 1;
                break;

            case "Poesia":
                position = 2;
                break;

            case "Fumetto":
                position = 3;
                break;
            case "Fiaba":
                position = 4;
                break;
            case "Altro":
                position = 5;
                break;
        }
        idModifica = extras.getLong("ID");
        titoloET.setText(extras.getString("TITOLO"));
        autoreET.setText(extras.getString("AUTORE"));
        genereSP.setSelection(position);;
        editoreET.setText(extras.getString("EDITORE"));


        /*if())
                {
                    *//*Libro libroAggiornato = new Libro();
                    libroAggiornato.setTitolo();
                    libroAggiornato.setAutore();
                    libroAggiornato.setGenere();
                    libroAggiornato.setEditore();*//*

                    //MOdiifica del record
                    //selezionare il record in base all'id
                    //fare l'update
                } else {

                }*/
    }

    private void preparaSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_genere, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        genereSP.setAdapter(adapter);
        genereSP.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String scelta = adapterView.getItemAtPosition(i).toString().trim();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //nel caso qui ci và il valore Altro
    }

    public void updateData() {

        if (isEmpty(titoloET) && isEmpty(autoreET) && isEmptySpinner(genereSP) && isEmpty(editoreET)) {

            /*AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
            miaAlert.setTitle("Perfetto");
            miaAlert.setMessage("Tutto vero");
            miaAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Non faccio nulla
                    //finish();

                    //RICHIAMARE IL METODO INSERT

                }
            });
            miaAlert.setNegativeButton("FORSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Non faccio nulla
                    //finish();
                }
            });
            AlertDialog alert = miaAlert.create();
            alert.show();*/

            ContentValues values = new ContentValues();

            values.put(dbUtil.KEY_TITOLO, titoloET.getText().toString().trim());
            values.put(dbUtil.KEY_AUTORE, autoreET.getText().toString().trim());
            values.put(dbUtil.KEY_GENERE, genereSP.getSelectedItem().toString().trim());
            values.put(dbUtil.KEY_EDITORE, editoreET.getText().toString().trim());

            ////////////////////////
            //Aggiornare il record
            libriDB.aggiornaRecord(values, idModifica);


            /*String pluto = "VERO " + titoloET.getText().toString().trim() + " " + isEmpty(titoloET)  + " " +
                    autoreET.getText().toString().trim() + " " + isEmpty(autoreET)  + " " +
                    genereSP.getSelectedItem().toString().trim() + " " + isEmptySpinner(genereSP)  + " " +
                    editoreET.getText().toString().trim() + " " + isEmpty(editoreET);

            Log.d("database", pluto);*/

            finish();

        } else {

            AlertDialog.Builder miaAlert = new AlertDialog.Builder(this);
            miaAlert.setTitle("Attenzione");
            miaAlert.setMessage("Compilare tutti i campi");
            miaAlert.setPositiveButton("OK", null);
            AlertDialog alert = miaAlert.create();
            alert.show();

            /*String pippo = "FALSO " + titoloET.getText().toString().trim() + " " + isEmpty(titoloET)  + " " +
                    autoreET.getText().toString().trim() + " " + isEmpty(autoreET)  + " " +
                    genereSP.getSelectedItem().toString().trim() + " " + isEmptySpinner(genereSP)  + " " +
                    editoreET.getText().toString().trim() + " " + isEmpty(editoreET);

            Log.d("database", pippo);*/

            /*SQLiteDatabase db = libriDB.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(dbUtil.KEY_TITOLO, titoloET.getText().toString().trim());
            values.put(dbUtil.KEY_AUTORE, autoreET.getText().toString().trim());
            values.put(dbUtil.KEY_GENERE, genereSP.getSelectedItem().toString().trim());
            values.put(dbUtil.KEY_EDITORE, editoreET.getText().toString().trim());

            String verifica =  titoloET.getText().toString().trim() + " " +
                    autoreET.getText().toString().trim() + " " +
                    genereSP.getSelectedItem().toString().trim() + " " +
                    editoreET.getText().toString().trim();

            long newRowID = db.insert(dbUtil.TABLE_NAME, null, values);

            Log.d("database", verifica);

            Toast.makeText(this, "Questo è il nuovo id " + newRowID, Toast.LENGTH_LONG).show();*/

        }


    }

    private boolean isEmpty(EditText text) {
        String empty = text.getText().toString().trim();

        if (!empty.matches("")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmptySpinner (Spinner text){
        String empty = text.getSelectedItem().toString().trim();

        if (!empty.matches("")) {
            return true;
        } else {
            return false;
        }

    }

}
