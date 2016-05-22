package br.com.ramada.callboy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.ramada.callboy.model.Contato;

import java.util.ArrayList;
import java.util.List;

import br.com.ramada.callboy.model.Configuracao;

/**
 * Created by Ramada on 21/05/2016.
 */
public class ContatoDataAccess {

    private static final String TABELA_NOME = "contato";
    private static final String CAMPO_ID = "id_contato";
    private static final String CAMPO_NOME = "nome";
    private static final String CAMPO_NUMERO = "numero_telefone";
    public SQLiteDatabase db;

    /*
    private static final String CAMPO_BLOQ_CHAMADA = "bloq_chamada";
    private static final String CAMPO_ANUNC_CHAMADA = "anuncia_chamada";
    private static final String CAMPO_BLOQ_SMS = "bloqueia_sms";
    private static final String CAMPO_ANUNCIA_SMS = "anuncia_sms";
    */

    protected static final String CREATE_TABELA =
            "CREATE TABLE " + TABELA_NOME + "" +
                    "(" + CAMPO_ID + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    CAMPO_NOME + " VARCHAR(100)  NOT NULL, " +
                    CAMPO_NUMERO + " VARCHAR(100)  NOT NULL );" ;

    protected static final String DROP_TABELA = "DROP TABLE IF EXISTS " + TABELA_NOME + " ;";



    public ContatoDataAccess(SQLiteDatabase db){
        this.db = db;
    }



    public long salvarContato(Contato contato){
      //  limparBanco();
        Log.d("msg","contato adicionado");
        ContentValues values = new ContentValues();
        values.put(CAMPO_NOME, contato.getNome());
        values.put(CAMPO_NUMERO, contato.getNumeroTelefone());
        //values.put(CAMPO_BLOQ_CHAMADA, obterIntDeBooleano(contato.getConfiguracao().isBloqueioChamada()));
        //values.put(CAMPO_ANUNC_CHAMADA, obterIntDeBooleano(contato.getConfiguracao().isAnuncioChamada()));
        //values.put(CAMPO_BLOQ_SMS, obterIntDeBooleano(contato.getConfiguracao().isBloqueioSms()));
        //values.put(CAMPO_ANUNCIA_SMS, obterIntDeBooleano(contato.getConfiguracao().isAnuncioSms()));

        long id = db.insert(TABELA_NOME, null, values);
        return id;
    }

    private int obterIntDeBooleano(boolean b){
        if(b){
            return 1;
        }else {
            return 0;
        }
    }

    private boolean obterBooleanDeInt(int i){
        if(i == 1){
            return true;
        }else{
            return false;
        }
    }

    public Contato getContact(int id){
        Cursor cursor = db.query(TABELA_NOME,new String[] {CAMPO_ID, CAMPO_NOME, CAMPO_NUMERO}, CAMPO_ID +"=?",new String[] {String.valueOf(id)},null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();
        else
            return null;
        Contato contact = new Contato(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        return contact;
    }

    public List<Contato> getAllContacts(){
        List<Contato> contactList = new ArrayList<Contato>();
        String selectQuery = "SELECT * FROM " + TABELA_NOME +" ORDER BY "+ CAMPO_NOME +" ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Contato contato = new Contato();
                contato.setId(Integer.parseInt(cursor.getString(0)));
                contato.setNome(cursor.getString(1));
                contato.setNumeroTelefone(cursor.getString(2));
                //Configuracao configuracao = new Configuracao();
                //configuracao.setBloqueioChamada(obterBooleanDeInt(Integer.parseInt(cursor.getString(3))));
                //configuracao.setAnuncioChamada(obterBooleanDeInt(Integer.parseInt(cursor.getString(4))));
                //configuracao.setBloqueioSms(obterBooleanDeInt(Integer.parseInt(cursor.getString(5))));
                //configuracao.setAnuncioSms(obterBooleanDeInt(Integer.parseInt(cursor.getString(6))));
                //contato.setConfiguracao(configuracao);
                contactList.add(contato);
            }while(cursor.moveToNext());
        }else
            return null;
        return contactList;
    }

    public List<String> getAllNumbers(){
        List<String> numList = new ArrayList<String>();
        String selectQuery = "SELECT "+ CAMPO_NUMERO +" FROM "+ TABELA_NOME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                numList.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }else{
            return null;
        }
        return numList;
    }

    public int getCount(){
        String countQuery = "SELECT COUNT(*) AS COUNT FROM "+ TABELA_NOME;
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor!=null)
            cursor.moveToFirst();
        else
            return 0;
        int count = Integer.parseInt(cursor.getString(0));
        return count;
    }

    public void deleteContact(long id){
        db.delete(TABELA_NOME, CAMPO_ID +"=?", new String[] {String.valueOf(id)});
    }





}
