package br.com.ramada.callboy.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.ramada.callboy.model.Grupo;

/**
 * Created by Ramada on 22/05/2016.
 */
public class GrupoDataAccess {

    protected static final String TABELA_NOME = "grupo";
    protected static final String CAMPO_ID = "id_grupo";
    private static final String CAMPO_NOME = "nome";
    public SQLiteDatabase db;

    protected static final String CREATE_TABELA =
            "CREATE TABLE " + TABELA_NOME + "" +
                    "(" + CAMPO_ID + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_NOME + " VARCHAR(100)  NOT NULL );" ;


    protected static final String DROP_TABELA = "DROP TABLE IF EXISTS " + TABELA_NOME + " ;";

    public GrupoDataAccess(SQLiteDatabase db){
        this.db = db;
    }



    public long salvarGrupo(Grupo grupo){
        Log.d("msg","grupo adicionado");
        ContentValues values = new ContentValues();
        values.put(CAMPO_NOME, grupo.getNome());

        long id = db.insert(TABELA_NOME, null, values);
        return id;
    }

    // TODO: DEBUGGAR HARD
    public Grupo getGrupo(String nome){
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
        Grupo grupo = new Grupo(cursor.getString(1));
        cursor.close();
        return grupo;
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
