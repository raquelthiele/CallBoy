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
public class Database extends SQLiteOpenHelper
{
    protected static SQLiteDatabase BD;
    private static final int DB_VERSION = 6;
    private static final String DB_NAME = "CallBoyDB";

    public static ContatoDataAccess contatoDAO;

    private static final String CREATE_DB =
            "CREATE TABLE agenda (" +
            "id_contato INTEGER  NULL," +
            "bloqueio BOOLEAN DEFAULT '0' NULL," +
            "anuncio BOOLEAN DEFAULT '0' NULL," +
            "leSMS BOOLEAN DEFAULT '0' NULL," +
            "excluiSMS BOOLEAN DEFAULT '0' NULL," +
            "FOREIGN KEY(id_contato) REFERENCES Contato(id_contato) ON UPDATE CASCADE ON DELETE CASCADE" +
            ");";

    private static final String UPDATE_DB =
            "DROP TABLE IF EXISTS contato;";



    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.BD = this.getWritableDatabase();

        contatoDAO = new ContatoDataAccess(BD);
    }

    public void close() {
        BD.close();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(CREATE_DB);
        db.execSQL(contatoDAO.CREATE_TABELA);
        Log.d("msg",contatoDAO.CREATE_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL(UPDATE_DB);
        db.execSQL(contatoDAO.DROP_TABELA);
        onCreate(db);
    }



}
