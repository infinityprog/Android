package com.example.projetandroid.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projetandroid.Entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class UserRepository {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "users.db";

    private static final String TABLE = "users";
    private static final String COL_ID = "id_user";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NAME= "name";
    private static final int NUM_COL_NAME = 1;
    private static final String COL_LOGIN = "login";
    private static final int NUM_COL_LOGIN = 2;
    private static final String COL_PASSWORD = "password";
    private static final int NUM_COL_PASSWORD = 3;
    private static final String COL_ROLE = "role";
    private static final int NUM_COL_ROLE = 4;
    private static final String COL_LAST_NAME = "last_name";
    private static final int NUM_COL_LAST_NAME = 5;

    private SQLiteDatabase bdd;

    private Database database;

    public UserRepository(Context context){
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

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insert(User user){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_NAME, user.getName());
        values.put(COL_LOGIN, user.getLogin());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_ROLE, user.getRole());
        values.put(COL_LAST_NAME,user.getLastName());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE, null, values);
    }

    public int update(int id, User user){
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_NAME, user.getName());
        values.put(COL_LOGIN, user.getLogin());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_ROLE, user.getRole());
        values.put(COL_LAST_NAME,user.getLastName());
        return bdd.update(TABLE, values, COL_ID + " = " +id, null);
    }

    public int delete(int id){
        //Suppression d'un livre de la BDD grâce à l'ID
        return bdd.delete(TABLE, COL_ID + " = " +id, null);
    }

    public User findUser(String login) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //Récupère dans un Cursor les valeurs correspondant à un livre contenu dans la BDD (ici on sélectionne le livre grâce à son titre)
        Cursor c = bdd.query(TABLE, new String[] {COL_ID, COL_NAME, COL_LOGIN, COL_PASSWORD,COL_ROLE,COL_LAST_NAME}, COL_LOGIN + " = \"" + login +"\"", null, null, null, null);
        return cursorToUser(c);
    }

    public User login(String login, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Cursor c = bdd.query(TABLE, new String[] {COL_ID, COL_NAME, COL_LOGIN, COL_PASSWORD,COL_ROLE,COL_LAST_NAME}, COL_LOGIN + " = \"" + login +"\" and " + COL_PASSWORD + " = \"" + password +"\"", null, null, null, null);
        return cursorToUser(c);
    }

    public ArrayList<User> findAll() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Cursor c = bdd.query(TABLE, new String[] {COL_ID, COL_NAME, COL_LOGIN, COL_PASSWORD,COL_ROLE,COL_LAST_NAME}, null, null, null, null, null);
        return cursorToList(c);
    }

    public boolean adminExist() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Cursor c = bdd.query(TABLE, new String[] {COL_ID, COL_NAME, COL_LOGIN, COL_PASSWORD,COL_ROLE,COL_LAST_NAME}, COL_ROLE + " = \"ADMIN\"", null, null, null, null);
        User user = cursorToUser(c);

        if(user == null){
            return false;
        }
        else {
            return true;
        }
    }

    //Cette méthode permet de convertir un cursor en un livre
    private User cursorToUser(Cursor c) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0) {
            return null;
        }

        //Sinon on se place sur le premier élément
        else {
            c.moveToFirst();
            //On créé un livre
            User user = new User(c.getInt(NUM_COL_ID), c.getString(NUM_COL_NAME), c.getString(NUM_COL_LOGIN), c.getString(NUM_COL_PASSWORD), c.getString(NUM_COL_ROLE),c.getString(NUM_COL_LAST_NAME));
            //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
            //On ferme le cursor
            c.close();

            //On retourne le livre
            return user;
        }
    }

    private ArrayList<User> cursorToList(Cursor c) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        ArrayList<User> users = new ArrayList<>();
        if (c.getCount() == 0) {
            return null;
        }
        else{
            while (c.moveToNext()){
                users.add(new User(c.getInt(NUM_COL_ID), c.getString(NUM_COL_NAME), c.getString(NUM_COL_LOGIN), c.getString(NUM_COL_PASSWORD), c.getString(NUM_COL_ROLE),c.getString(NUM_COL_LAST_NAME)));
            }
            c.close();
            return users;
        }
    }
}
