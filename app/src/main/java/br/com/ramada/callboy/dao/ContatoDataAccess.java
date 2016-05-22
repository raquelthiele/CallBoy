package br.com.ramada.callboy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.ramada.callboy.model.Contato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramada on 21/05/2016.
 */
public class ContatoDataAccess extends DataAccess {

    private static final String TABELA_CONTATO = "contato";
    private static final String KEY_ID = "id_contato";
    private static final String KEY_NOME = "nome";
    private static final String KEY_NUMERO = "numero_telefone";



    public ContatoDataAccess(Context context){
        super(context);
    }


    public long addNumber(Contato contact){
        Log.d("msg","added contact");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOME, contact.getNome());
        values.put(KEY_NUMERO, contact.getNumeroTelefone());
        long id = db.insert(TABELA_CONTATO, null, values);
        db.close();
        return id;
    }

    public Contato getContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABELA_CONTATO,new String[] {KEY_ID, KEY_NOME, KEY_NUMERO},KEY_ID+"=?",new String[] {String.valueOf(id)},null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        else
            return null;
        Contato contact = new Contato(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        db.close();
        return contact;
    }

    public List<Contato> getAllContacts(){
        List<Contato> contactList = new ArrayList<Contato>();
        String selectQuery = "SELECT * FROM " + TABELA_CONTATO +" ORDER BY "+ KEY_NOME +" ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Contato contato = new Contato();
                contato.setId(Integer.parseInt(cursor.getString(0)));
                contato.setNome(cursor.getString(1));
                contato.setNumeroTelefone(cursor.getString(2));
                contactList.add(contato);
            }while(cursor.moveToNext());
        }else
            return null;
        db.close();
        return contactList;
    }

    public List<String> getAllNumbers(){
        List<String> numList = new ArrayList<String>();
        String selectQuery = "SELECT "+ KEY_NUMERO +" FROM "+ TABELA_CONTATO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                numList.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }else{
            db.close();
            return null;
        }
        db.close();
        return numList;
    }

    public int getCount(){
        SQLiteDatabase db = this.getWritableDatabase();
        String countQuery = "SELECT COUNT(*) AS COUNT FROM "+ TABELA_CONTATO;
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor!=null)
            cursor.moveToFirst();
        else
            return 0;
        int count = Integer.parseInt(cursor.getString(0));
        db.close();
        return count;
    }

    public void deleteContact(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_CONTATO, KEY_ID+"=?", new String[] {String.valueOf(id)});
        db.close();
    }





}
