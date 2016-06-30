package br.com.ramada.callboy.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import br.com.ramada.callboy.model.Contato;
import br.com.ramada.callboy.model.Grupo;

import static br.com.ramada.callboy.CallBoy.BD;


/**
 * Classe responsavel pela criacao do banco e tabelas
 *
 * Created by Ramada on 21/05/2016.
 */
public class Database extends SQLiteOpenHelper
{
    protected static SQLiteDatabase BD;
    private static final int DB_VERSION = 14;
    private static final String DB_NAME = "CallBoyDB";

    public static ContatoDataAccess contatoDAO;
    public static GrupoDataAccess grupoDAO;
    public static HorarioDataAccess horarioDAO;
    public static AgendaDataAccess agendaDAO;


    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.BD = this.getWritableDatabase();

        contatoDAO = new ContatoDataAccess(BD);
        grupoDAO = new GrupoDataAccess(BD);
        horarioDAO = new HorarioDataAccess(BD);
        agendaDAO = new AgendaDataAccess(BD);
    }

    public void close() {
        BD.close();
    }



    // TODO : retornar PRAGMA foreign_keys; PRAGMA foreign_keys = ON; Testar essas titicas depois e ver o q acontece
    // NÃO TÁ PEGANDO, VOU ALI ME MATAR E JÁ VOLTO :(
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON;");
        db.execSQL(contatoDAO.CREATE_TABELA);
        db.execSQL(grupoDAO.CREATE_TABELA);
        db.execSQL(horarioDAO.CREATE_TABELA);
        db.execSQL(agendaDAO.CREATE_TABELA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Deleta as tabelas antigas
        db.execSQL(contatoDAO.DROP_TABELA);
        db.execSQL(grupoDAO.DROP_TABELA);
        db.execSQL(horarioDAO.DROP_TABELA);
        db.execSQL(agendaDAO.DROP_TABELA);

        //Cria tudo de novo
        onCreate(db);
    }



}
