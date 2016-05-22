package br.com.ramada.callboy.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.ramada.callboy.model.Configuracao;
import br.com.ramada.callboy.model.Contato;

/**
 * Classe responsavel pela criacao do banco e tabelas
 * Created by Ramada on 21/05/2016.
 */
public class BancoDeDados extends SQLiteOpenHelper

    {

        private static final int DB_VERSION = 2;
        private static final String DB_NAME = "CallBoyDB";
        private static final String TABELA_CONTATO = "contato";
        private static final String CAMPO_ID = "id";
        private static final String CAMPO_NOME = "nome";
        private static final String CAMPO_NUMERO = "numero_telefone";
        private static final String CAMPO_BLOQ_CHAMADA = "bloq_chamada";
        private static final String CAMPO_ANUNC_CHAMADA = "anuncia_chamada";
        private static final String CAMPO_BLOQ_SMS = "bloqueia_sms";
        private static final String CAMPO_ANUNCIA_SMS = "anuncia_sms";



        public BancoDeDados(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        // TODO Auto-generated constructor stub
    }

        @Override
        public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                TABELA_CONTATO + "(" + CAMPO_ID +" INTEGER PRIMARY KEY, " +
                CAMPO_NOME + " TEXT, " + CAMPO_NUMERO + " TEXT UNIQUE,"
                + CAMPO_BLOQ_CHAMADA + " INTEGER," + CAMPO_ANUNC_CHAMADA +
                " INTEGER," + CAMPO_BLOQ_SMS + " INTEGER," + CAMPO_ANUNCIA_SMS + " INTEGER"+
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.d("msg",CREATE_CONTACTS_TABLE);
    }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS "+ TABELA_CONTATO);
        onCreate(db);
    }

        private void limparBanco(){
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS "+ TABELA_CONTATO);

            String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                    TABELA_CONTATO + "(" + CAMPO_ID +" INTEGER PRIMARY KEY, " +
                    CAMPO_NOME + " TEXT, " + CAMPO_NUMERO + " TEXT UNIQUE,"
                    + CAMPO_BLOQ_CHAMADA + "INTEGER," + CAMPO_ANUNC_CHAMADA +
                    "INTEGER," + CAMPO_BLOQ_SMS + "INTEGER," + CAMPO_ANUNCIA_SMS + "INTEGER"+
                    ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }

    public long salvarContato(Contato contato){
      //  limparBanco();
        Log.d("msg","contato adicionado");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAMPO_NOME, contato.getNome());
        values.put(CAMPO_NUMERO, contato.getNumeroTelefone());
        values.put(CAMPO_BLOQ_CHAMADA, obterIntDeBooleano(contato.getConfiguracao().isBloqueioChamada()));
        values.put(CAMPO_ANUNC_CHAMADA, obterIntDeBooleano(contato.getConfiguracao().isAnuncioChamada()));
        values.put(CAMPO_BLOQ_SMS, obterIntDeBooleano(contato.getConfiguracao().isBloqueioSms()));
        values.put(CAMPO_ANUNCIA_SMS, obterIntDeBooleano(contato.getConfiguracao().isAnuncioSms()));

        long id = db.insert(TABELA_CONTATO, null, values);
        db.close();
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
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABELA_CONTATO,new String[] {CAMPO_ID, CAMPO_NOME, CAMPO_NUMERO}, CAMPO_ID +"=?",new String[] {String.valueOf(id)},null,null,null,null);
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
        String selectQuery = "SELECT * FROM " + TABELA_CONTATO +" ORDER BY "+ CAMPO_NOME +" ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Contato contato = new Contato();
                contato.setId(Integer.parseInt(cursor.getString(0)));
                contato.setNome(cursor.getString(1));
                contato.setNumeroTelefone(cursor.getString(2));
                Configuracao configuracao = new Configuracao();
                configuracao.setBloqueioChamada(obterBooleanDeInt(Integer.parseInt(cursor.getString(3))));
                configuracao.setAnuncioChamada(obterBooleanDeInt(Integer.parseInt(cursor.getString(4))));
                configuracao.setBloqueioSms(obterBooleanDeInt(Integer.parseInt(cursor.getString(5))));
                configuracao.setAnuncioSms(obterBooleanDeInt(Integer.parseInt(cursor.getString(6))));
                contato.setConfiguracao(configuracao);
                contactList.add(contato);
            }while(cursor.moveToNext());
        }else
            return null;
        db.close();
        return contactList;
    }

    public List<String> getAllNumbers(){
        List<String> numList = new ArrayList<String>();
        String selectQuery = "SELECT "+ CAMPO_NUMERO +" FROM "+ TABELA_CONTATO;
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
        db.delete(TABELA_CONTATO, CAMPO_ID +"=?", new String[] {String.valueOf(id)});
        db.close();
    }
}
