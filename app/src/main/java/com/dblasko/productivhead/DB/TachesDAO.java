package com.dblasko.productivhead.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dblasko.productivhead.Todolist.Taches;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TachesDAO {

    private MySQLite mySQLite;
    private SQLiteDatabase db;

    public TachesDAO(Context context) {
        mySQLite = MySQLite.getInstance(context);
    }

    public void open() {
        db = mySQLite.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void addTache(Taches tache) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", tache.getId());
        contentValues.put("DATEDEB", tache.getDateDebut());
        contentValues.put("DATEFIN", tache.getDateFin());
        contentValues.put("HEURE", tache.getHeure());
        contentValues.put("NOM", tache.getNom());
        contentValues.put("RESUME", tache.getResume());
        db.insert("Taches",null, contentValues);
    }

    public Taches getTache(int id){

        Taches tache = null;

        Cursor c = db.rawQuery("SELECT * FROM Taches WHERE id = " + id, null);
        if (c.moveToFirst()) {
            tache = new Taches(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4),c.getString(5));
        }
        c.close();
        return tache;

    }
    public void supprimerTache(int id){


        String where = "id = ?";
        String[] whereArgs = {id + ""};
        db.delete("Taches", where, whereArgs);

    }


    public List<Taches> getAllTaches() {
        ArrayList<Taches> taches = new ArrayList<>();

        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dat = dateFormat.format(actuelle);
        System.out.println(dat);

        Cursor c = db.rawQuery("SELECT * FROM Taches ", null);
        while(c.moveToNext()) {
            Taches tache = new Taches(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4),c.getString(5));
            taches.add(tache);
        }
        c.close();
        return taches;
    }

}