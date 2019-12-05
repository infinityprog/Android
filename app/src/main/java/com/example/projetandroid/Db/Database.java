package com.example.projetandroid.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String TABLE = "users";
    private static final String COL_ID = "id_user";
    private static final String COL_NAME = "name";
    private static final String COL_LOGIN = "login";
    private static final String COL_PASSWORD = "password";
    private static final String COL_ROLE = "role";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT NOT NULL, "
            + COL_LOGIN + " TEXT NOT NULL, " + COL_PASSWORD + " TEXT NOT NULL, " + COL_ROLE + " TEXT NOT NULL" + ");";

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on crée la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE + ";");
        onCreate(db);
    }
}
