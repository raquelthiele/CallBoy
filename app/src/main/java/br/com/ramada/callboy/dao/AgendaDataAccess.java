package br.com.ramada.callboy.dao;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by danie on 22/05/2016.
 */
public class AgendaDataAccess {

    private static final String TABELA_NOME = "agenda";
    private static final String CAMPO_ID_CONTATO = "id_contato";
    private static final String CAMPO_ID_GRUPO = "id_grupo";
    private static final String CAMPO_ID_HORARIO = "id_horario";
    private static final String CAMPO_BLOQUEIO_CHAMADA = "bloqueio_chamada";
    private static final String CAMPO_ANUNCIO_CHAMADA = "anuncio_chamada";
    private static final String CAMPO_BLOQUEIO_SMS = "bloqueio_sms";
    private static final String CAMPO_ANUNCIO_SMS = "anuncio_sms";
    public SQLiteDatabase db;

    protected static final String CREATE_TABELA =
            "CREATE TABLE " + TABELA_NOME + " ( " +
                    CAMPO_ID_CONTATO + " INTEGER NULL , " +
                    CAMPO_ID_GRUPO + " INTEGER NULL , " +
                    CAMPO_ID_HORARIO + " INTEGER NULL , " +
                    CAMPO_BLOQUEIO_CHAMADA + " BOOLEAN DEFAULT '0' NULL, " +
                    CAMPO_ANUNCIO_CHAMADA + " BOOLEAN DEFAULT '0' NULL, " +
                    CAMPO_BLOQUEIO_SMS + " BOOLEAN DEFAULT '0' NULL, " +
                    CAMPO_ANUNCIO_SMS + " BOOLEAN DEFAULT '0' NULL, " +
                    " FOREIGN KEY( " + CAMPO_ID_CONTATO + " ) REFERENCES " + ContatoDataAccess.TABELA_NOME +
                    "( " + ContatoDataAccess.CAMPO_ID + " ) ON UPDATE CASCADE ON DELETE CASCADE " +
                    " FOREIGN KEY( " + CAMPO_ID_GRUPO + " ) REFERENCES " + GrupoDataAccess.TABELA_NOME +
                    "( " + GrupoDataAccess.CAMPO_ID + " ) ON UPDATE CASCADE ON DELETE CASCADE " +
                    " FOREIGN KEY( " + CAMPO_ID_HORARIO + " ) REFERENCES " + HorarioDataAccess.TABELA_NOME +
                    "( " + HorarioDataAccess.CAMPO_ID + " ) ON UPDATE CASCADE ON DELETE CASCADE " +
                    " PRIMARY KEY( " + CAMPO_ID_CONTATO + " , " + CAMPO_ID_GRUPO + " , " + CAMPO_ID_HORARIO + " )" +
                    " );" ;


    protected static final String DROP_TABELA = "DROP TABLE IF EXISTS " + TABELA_NOME + " ;";

    public AgendaDataAccess(SQLiteDatabase db){
        this.db = db;
    }




}
