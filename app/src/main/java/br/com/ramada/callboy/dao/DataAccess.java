package br.com.ramada.callboy.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.ramada.callboy.model.Contato;


/**
 * Classe responsavel pela criacao do banco e tabelas
 * Created by Ramada on 21/05/2016.
 */
public class DataAccess extends SQLiteOpenHelper
{
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CallBoyDB";

    private static final String CREATE_DB =
            "BEGIN TRANSACTION;" +
            "CREATE TABLE [contato] (" +
            "[id_contato] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[nome] VARCHAR(100)  NOT NULL," +
            "[numero_telefone] VARCHAR(100)  NOT NULL" +
            ");" +
            "CREATE TABLE [agenda] (" +
            "[id_contato] INTEGER  NULL," +
            "[bloqueio] BOOLEAN DEFAULT '0' NULL," +
            "[anuncio] BOOLEAN DEFAULT '0' NULL," +
            "[leSMS] BOOLEAN DEFAULT '0' NULL," +
            "[excluiSMS] BOOLEAN DEFAULT '0' NULL," +
            "FOREIGN KEY(id_contato) REFERENCES Contato(id_contato) ON UPDATE CASCADE ON DELETE CASCADE" +
            ");" +
            "COMMIT;";

    private static final String UPDATE_DB =
            "BEGIN TRANSACTION;" +
            "DROP TABLE IF EXISTS [contato];" +
            "DROP TABLE IF EXISTS [agenda];"        ;



    public DataAccess(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABELA_CONTATO + "(" +KEY_ID +" INTEGER PRIMARY KEY, " + KEY_NOME + " TEXT, " + KEY_NUMERO + " TEXT UNIQUE)";
        db.execSQL(CREATE_DB);
        Log.d("msg",CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UPDATE_DB);
        onCreate(db);
    }

}
