package br.com.ramada.callboy.dao;

import android.content.ContentValues;
import android.database.Cursor;
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
        ContentValues values = new ContentValues();
        values.put(CAMPO_NOME, horario.getNome());

        long id = db.insert(TABELA_NOME, null, values);
        return id;
    }

    public Horario getHorario(int idHorario){
        Cursor cursor = db.query(TABELA_NOME,new String[] {CAMPO_ID, CAMPO_NOME},
                CAMPO_ID +"=?", new String[] {String.valueOf(idHorario)}, null,null,null,null);
        if(cursor!=null)
            try{
                cursor.moveToFirst();
            }
            catch (Exception e){
                Log.d("exc", e.getMessage());
                return null;
            }
        else
            return null;
        Horario horario = new Horario(cursor.getInt(0),cursor.getString(1));
        cursor.close();
        return horario;
    }

    // TODO: DEBUGGAR HARD
    public Horario getHorario(String nome){
        Cursor cursor = db.query(TABELA_NOME,new String[] {CAMPO_ID, CAMPO_NOME},
                CAMPO_NOME +"=?", new String[] {nome}, null,null,null,null);
        if(cursor!=null)
            try{
                cursor.moveToFirst();
            }
            catch (Exception e){
                Log.d("exc", e.getMessage());
                return null;
            }
        else
            return null;
        Horario horario = new Horario(cursor.getString(1));
        cursor.close();
        return horario;
    }

    public int getCount(String nome){
        String countQuery = "SELECT COUNT(*) AS COUNT FROM "+ TABELA_NOME + " WHERE " +
                CAMPO_NOME + " = '" + nome + "';";

        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor!=null)
            cursor.moveToFirst();
        else
            return 0;
        int count = Integer.parseInt(cursor.getString(0));
        return count;
    }

}
