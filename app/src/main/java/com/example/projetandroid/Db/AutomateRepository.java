package com.example.projetandroid.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetandroid.Entity.Automate;
import com.example.projetandroid.Entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class AutomateRepository {

    private static final int VERSION_BDD = 3;
    private static final String NOM_BDD = "users.db";

    private static final String TABLE = "automate";
    private static final String COL_ID = "id_automate";
    private static final int NUM_COL_ID = 0;
    private static final String COL_DESCRIPTION = "description";
    private static final int NUM_COL_DESCRIPTION = 1;
    private static final String COL_IP = "ip";
    private static final int NUM_COL_IP = 2;
    private static final String COL_SLOT = "slot";
    private static final int NUM_COL_SLOT = 3;
    private static final String COL_RACK = "rack";
    private static final int NUM_COL_RACK = 4;
    private static final String COL_ID_USER = "id_user";
    private static final int NUM_COL_ID_USER = 5;

    private SQLiteDatabase bdd;

    private Database database;

    public AutomateRepository(Context context){
        //On crée la BDD et sa table
        database = new Database(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = database.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insert(Automate automate){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_DESCRIPTION, automate.getDescription());
        values.put(COL_IP, automate.getIp());
        values.put(COL_RACK, automate.getRack());
        values.put(COL_SLOT, automate.getSlot());
        values.put(COL_ID_USER, automate.getIdUser());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE, null, values);
    }

    public int update(int id, Automate automate){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_DESCRIPTION, automate.getDescription());
        values.put(COL_IP, automate.getIp());
        values.put(COL_RACK, automate.getRack());
        values.put(COL_SLOT, automate.getSlot());
        values.put(COL_ID_USER, automate.getIdUser());
        return bdd.update(TABLE, values, COL_ID + " = " +id, null);
    }

    public int delete(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE, COL_ID + " = " +id, null);
    }

    public ArrayList<Automate> findAll(int id){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE, new String[] {COL_ID, COL_DESCRIPTION, COL_IP,COL_RACK,COL_SLOT,COL_ID_USER}, COL_ID_USER + " = " +id, null, null, null, null);
        return cursorToList(c);
    }

    public int findLast(){
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.rawQuery("select max("+COL_ID+") from "+TABLE,null);
        return cursortToId(c);
    }

    //Cette méthode permet de convertir un cursor en un livre
    private Automate cursorToUser(Cursor c) {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0) {
            return null;
        }

        //Sinon on se place sur le premier élément
        else {
            c.moveToFirst();
            //On créé un livre
            Automate automate = new Automate(c.getInt(NUM_COL_ID), c.getString(NUM_COL_DESCRIPTION), c.getString(NUM_COL_IP), c.getInt(NUM_COL_RACK),c.getInt(NUM_COL_SLOT),c.getInt(NUM_COL_ID_USER));
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            //On ferme le cursor
            c.close();

            //On retourne le livre
            return automate;
        }
    }

    private ArrayList<Automate> cursorToList(Cursor c){

        ArrayList<Automate> automates = new ArrayList<>();
        if (c.getCount() == 0) {
            return null;
        }
        else{
            while (c.moveToNext()){
                automates.add(new Automate(c.getInt(NUM_COL_ID), c.getString(NUM_COL_DESCRIPTION), c.getString(NUM_COL_IP), c.getInt(NUM_COL_RACK),c.getInt(NUM_COL_SLOT),c.getInt(NUM_COL_ID_USER)));
            }
            c.close();
            return automates;
        }
    }

    private int cursortToId(Cursor c){

        if (c.getCount() == 0) {
            return -1;
        }
        else{
            c.moveToFirst();
            int id = c.getInt(NUM_COL_ID);
            c.close();
            return id;
        }

    }
}
