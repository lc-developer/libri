package com.example.archiviolibri.Model;

public class Genere {

    public static final String[] genere = {
            "Romanzo",
            "Saggio",
            "Poesia",
            "Fumetto",
            "Fiaba",
            "Racconti"
    };

    public Genere() {
    }

    public static String[] getGenere() {
        return genere;
    }
}
