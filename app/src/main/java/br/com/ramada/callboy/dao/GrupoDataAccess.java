package br.com.ramada.callboy.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.ramada.callboy.model.Grupo;

import java.util.ArrayList;
import java.util.List;

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
        ContentValues values = new ContentValues();
        values.put(CAMPO_NOME, grupo.getNome());

        long id = db.insert(TABELA_NOME, null, values);
        return id;
    }


    public Grupo getGrupo(int idGrupo){
        Cursor cursor = db.query(TABELA_NOME,new String[] {CAMPO_ID, CAMPO_NOME},
                CAMPO_ID +"=?", new String[] {String.valueOf(idGrupo)}, null,null,null,null);
        if(cursor.getCount() != 0)
            try{
                cursor.moveToFirst();
            }
            catch (Exception e){
                Log.d("exc", e.getMessage());
                return null;
            }
        else
            return null;
        Grupo grupo = new Grupo(cursor.getInt(0),cursor.getString(1));
        cursor.close();
        return grupo;
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
        if(cursor.getCount() != 0)
            cursor.moveToFirst();
        else
            return 0;
        int count = Integer.parseInt(cursor.getString(0));
        return count;
    }

    public int getCount(){
        String countQuery = "SELECT COUNT(*) AS COUNT FROM "+ TABELA_NOME + ";";

        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor.getCount() != 0)
            cursor.moveToFirst();
        else
            return 0;
        int count = cursor.getInt(0);
        return count;
    }

    public List<Grupo> getAllGroups(){
        List<Grupo> groupList = new ArrayList<Grupo>();
        String selectQuery = "SELECT * FROM " + TABELA_NOME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        //Construindo a lista
        if(cursor.moveToFirst()){
            do{
                Grupo grupo = new Grupo(cursor.getInt(0), cursor.getString(1));
                groupList.add(grupo);
            }while(cursor.moveToNext());
        }else
            return null;
        cursor.close();
        return groupList;
    }


}
