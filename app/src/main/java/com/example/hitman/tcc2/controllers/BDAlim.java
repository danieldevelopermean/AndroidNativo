package com.example.hitman.tcc2.controllers;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.example.hitman.tcc2.models.alimento;

public class BDAlim extends SQLiteOpenHelper {

    private static int version=1;
    private static String nomeBaseDados = "Produtos";
    private static CursorFactory factory = null;

    public BDAlim(Context context) {
        super(context, nomeBaseDados, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE alimentos(ID INTEGER PRIMARY KEY, " +
                "BQUANT INTEGER, BCHECK INTEGER ," +
                "BQUANT2 INTEGER, BCHECK2 INTEGER," +
                "T1 NUMBER, T2 NUMBER, TGERAL NUMBER " +
                ")";
        db.execSQL(query);
    } // fim onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}// fim onUpgrade

    public void Salvar(int bquant, int bcheck, int bquant2, int bcheck2,
                       double tot1 ,double tot2,double tgeral ){

        SQLiteDatabase db = getWritableDatabase();

        if(db != null){
            ContentValues values = new ContentValues();
            values.put("BQUANT", bquant);
            values.put("BCHECK", bcheck);
            values.put("BQUANT2", bquant2);
            values.put("BCHECK2", bcheck2);
            values.put("T1", tot1);
            values.put("T2", tot2);
            values.put("TGERAL", tgeral);

            db.insert("alimentos", null, values);
            db.close();
        }//fim if
    }// fim Salvar


    public alimento[] recuperarDados() {

        SQLiteDatabase db = getWritableDatabase();
        String sql = "Select * from  alimentos";
        alimento[] lista = null;
        Cursor c = db.rawQuery(sql, null);

        lista = new alimento[c.getCount()];
        int i = 0;

        if (c.moveToFirst()) {
            do {
                alimento alimen = new alimento();
                alimen.setId((c.getInt(0)));
                alimen.setQuant1(c.getInt(1));
                alimen.setCh1(c.getInt(2));
                alimen.setQuant2(c.getInt(3));
                alimen.setCh2(c.getInt(4));
                alimen.setT1(c.getDouble(5));
                alimen.setT2(c.getDouble(6));
                alimen.setTgeral(c.getDouble(7));
                lista[i] = alimen;
            } while (c.moveToNext());
        }// fim if
            c.close();
            db.close();
            return lista;
        }// fim recuperarDados
}// fim Class
