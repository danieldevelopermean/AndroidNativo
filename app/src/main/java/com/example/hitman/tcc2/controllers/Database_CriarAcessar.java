package com.example.hitman.tcc2.controllers;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hitman.tcc2.models.Contato;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Database_CriarAcessar extends SQLiteOpenHelper {

    //Criar as variaveis de acesso a database contato
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contato.db";
    private static final String TABLE_NAME = "contato";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "nome";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASS = "senha";
    private static final String COL_DATE = "dataHoje";
    private static final String COL_ENDERECO = "endereco";

    SQLiteDatabase db;

    // cria o sql responsavel pela existencia da tabela contato
    private static final String TABLE_CREATE = "create table contato (id integer primary key not null, nome text not null," +
        " email text not null, senha text not null, dataHoje date not null, endereco text not null );" ;

    // construtor - cria a database
    public Database_CriarAcessar(Context c){
           super(c, DATABASE_NAME,null ,DATABASE_VERSION);
    }

    // metodo padrao
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE); // query executada
        this.db = db; // database e criada
    }

    public void Inserir(Contato c){
        db = this.getWritableDatabase();// da a habilidade de escrever na tabela
        ContentValues values = new ContentValues(); // colocar os dados dentro da tabela

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate = sdf.format(new Date());

        String query = "select * from contato"; // cria uma query que recupera todos os dados da tabela
        Cursor cursor = db.rawQuery(query, null); // percorrer a tabela
        int count = cursor.getCount(); // conta o numero de voltas

        values.put(COL_ID, count); // valor retornado pelo cursor
        values.put(COL_NAME, c.getNome()); //  recupera os valores existentes
        values.put(COL_EMAIL, c.getEmail());
        values.put(COL_PASS, c.getSenha());
        values.put(COL_DATE, strDate);
        values.put(COL_ENDERECO, c.getEndereco());

        //inseri os valores na tabela
        db.insert(TABLE_NAME, null, values);
        db.close(); // finaliza a transacao
    }// fim inserir


    // verifica se nome do usuario ja esta cadastrado
    public String jaCadastrado(String nome){
        db = this.getReadableDatabase(); // le dados da tabela
        String query = "Select nome, senha from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        String a, b; // auxiliares

        b = "Usuario nao cadastrado"; // caso nao exista comparacao correta retora esse b
        // percorre a tabela
        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(0); // inicializa com a 1 posicao do cursor
                // verifica se o valor de a e igual a o valor passado a o metodo jaCadastrado
                if(a.equals(nome)){
                    b = cursor.getString(1); // recupera o valor da senha e repassa a MainActivity
                }
            }while (cursor.moveToNext()); // percore a tebela ate achar uma comparacao de nome

            return b ;
        }
        return b;
    }// JaCadastrado

    // metodo padrao -  nao usado
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query = "drop table if exists" + TABLE_NAME; // caso exista ja uma tabela ela sera apagada
        db.execSQL(query);// executa a instrucao
        this.onCreate(db); // recria a tabela
    }// fim onUpgrade

    public Contato[] recuperarDados() {

        SQLiteDatabase db = getWritableDatabase();
        String sql = "Select * from  contato";
        Contato[] lista = null;
        Cursor c = db.rawQuery(sql, null);

        lista = new Contato[c.getCount()];
        int i = 0;

        if (c.moveToFirst()) {
            do {
                Contato contato = new Contato();
                contato.setId((c.getInt(0)));
                contato.setNome((c.getString(1)));
                contato.setEmail((c.getString(2)));
                contato.setDate(c.getString(4));
                contato.setEndereco(c.getString(5));
                lista[i] = contato;
            } while (c.moveToNext());
        }// fim if
        c.close();
        db.close();
        return lista;
    }// fim recuperarDados
}// fim classe
