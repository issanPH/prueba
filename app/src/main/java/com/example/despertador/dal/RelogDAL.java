package com.example.despertador.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.despertador.dto.Relog;
import com.example.despertador.helper.DatabaseHelper;

import java.util.ArrayList;

public class RelogDAL {
    private DatabaseHelper dbHelper;
    private Relog relog;

    public RelogDAL(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.relog = new Relog();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }

    public RelogDAL(Context context,Relog relog) {
        this.dbHelper = new DatabaseHelper(context);
        this.relog = relog;
    }

    public ArrayList<Relog> seleccionar()
    {
        ArrayList<Relog> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor consulta = db.rawQuery("SELECT * FROM relog", null);

        if(consulta.moveToFirst()) {
            do {
                int id = consulta.getInt(0);
                int hora = consulta.getInt(1);
                int min = consulta.getInt(2);
                String ampm = consulta.getString(3);
                String estado = consulta.getString(4);

                Relog relog = new Relog(id,hora,min,ampm);
                lista.add(relog);


            } while(consulta.moveToNext());

        }

        return lista;
    }

    public boolean insertar() {
        return this.tryInsert();
    }

    public boolean insertar(int hora, int min, String ampm,String estado)
    {
        this.relog.setHora(hora);
        this.relog.setMinutos(min);
        this.relog.setAMPM(ampm);
        this.relog.setEstado(estado);

        return this.tryInsert();
    }

    private boolean tryInsert() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();



        ContentValues c = new ContentValues();
        c.put("hora", this.relog.getHora());
        c.put("min", this.relog.getMinutos());
        c.put("ampm", this.relog.getAMPM());
        c.put("estado", this.relog.getEstado());

        try {
            db.insert("relog", null, c);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    public boolean eliminar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int filasAfectadas;


        try {
            filasAfectadas = db.delete("relog","id = ?",
                    new String[] { String.valueOf(id) });
        } catch (Exception e) {
            return false;
        }

        return (filasAfectadas == 1);

    }

    public boolean actualizar(int id, Relog relog)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put("hora", relog.getHora());
        c.put("min", relog.getMinutos());
        c.put("seg", relog.getAMPM());
        c.put("estado",relog.getEstado());
        try {
            int filasAfectadas;
            filasAfectadas = db.update(
                    "relog",
                    c,
                    "id = ?",
                    new String[] { String.valueOf(id) }
            );
            return (filasAfectadas > 0);
        } catch (Exception e) {

        }

        return false;
    }

    public boolean actualizar(Relog relog)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues c = new ContentValues(); // Objeto tipo clave-valor
        c.put("hora", relog.getHora());
        c.put("min", relog.getMinutos());
        c.put("seg", relog.getAMPM());
        c.put("estado",relog.getEstado());
        try {
            int filasAfectadas;
            filasAfectadas = db.update(
                    "hora",
                    c,
                    "id = ?",
                    new String[] { String.valueOf(relog.getId()) }
            );
            return (filasAfectadas > 0);
        } catch (Exception e) {

        }

        return false;
    }

    public Relog getRelog()
    {
        return this.relog;
    }


}
