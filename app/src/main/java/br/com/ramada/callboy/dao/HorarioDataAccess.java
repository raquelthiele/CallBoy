package br.com.ramada.callboy.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.ramada.callboy.model.Horario;

/**
 * Created by Ramada on 22/05/2016.
 */
public class HorarioDataAccess {

    protected static final String TABELA_NOME = "horario";
    protected static final String CAMPO_ID = "id_horario";
    private static final String CAMPO_NOME = "nome";
    //private static final String CAMPO_ID_FAIXA_HORARIA;
    public SQLiteDatabase db;

    protected static final String CREATE_TABELA =
            "CREATE TABLE " + TABELA_NOME + "" +
                    "(" + CAMPO_ID + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_NOME + " VARCHAR(100)  NOT NULL );" ;


    protected static final String DROP_TABELA = "DROP TABLE IF EXISTS " + TABELA_NOME + " ;";


    public HorarioDataAccess(SQLiteDatabase db){
        this.db = db;
    }



    public long salvarHorario(Horario horario){
        Log.d("msg","horario adicionado");
        ContentValues values = new ContentValues();
        values.put(CAMPO_NOME, horario.getNome());

        long id = db.insert(TABELA_NOME, null, values);
        return id;
    }


}
