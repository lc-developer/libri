package com.example.archiviolibri.Database;

public class dbUtil {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "db";
    public static final String TABLE_NAME = "libri";


    public static final String KEY_ID = "_id";
    public static final String KEY_TITOLO = "titolo";
    public static final String KEY_AUTORE = "autore";
    public static final String KEY_GENERE = "genere";
    public static final String KEY_EDITORE = "editore";


    public static final String SQL_CREATE_TABLE_LIBRI = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_TITOLO + " TEXT, "
            + KEY_AUTORE + " TEXT, "
            + KEY_GENERE + " TEXT, "
            + KEY_EDITORE + " TEXT);";


    public static final String SQL_DELETE_TABLE_LIBRI = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public static final String SQL_SELECT_ALL = "SELECT * FROM " + TABLE_NAME + ";";

}
