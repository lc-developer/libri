package com.example.archiviolibri.Model;

public class Libro {

    private int id;
    private String Titolo;
    private String Autore;
    private String Genere;
    private String Editore;

    public Libro() {
    }

    public Libro(String titolo, String autore, String genere, String editore) {
        Titolo = titolo;
        Autore = autore;
        Genere = genere;
        Editore = editore;
    }

    public Libro(int id, String titolo, String autore, String genere, String editore) {
        this.id = id;
        Titolo = titolo;
        Autore = autore;
        Genere = genere;
        Editore = editore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        Titolo = titolo;
    }

    public String getAutore() {
        return Autore;
    }

    public void setAutore(String autore) {
        Autore = autore;
    }

    public String getGenere() {
        return Genere;
    }

    public void setGenere(String genere) {
        Genere = genere;
    }

    public String getEditore() {
        return Editore;
    }

    public void setEditore(String editore) {
        Editore = editore;
    }
}
