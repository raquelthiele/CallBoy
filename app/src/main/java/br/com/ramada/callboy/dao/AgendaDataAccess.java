package br.com.ramada.callboy.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.ramada.callboy.model.Configuracao;
import br.com.ramada.callboy.model.Contato;
import br.com.ramada.callboy.model.Grupo;
import br.com.ramada.callboy.model.Horario;

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
                    "( " + ContatoDataAccess.CAMPO_ID + " ) ON DELETE CASCADE " +
                    " FOREIGN KEY( " + CAMPO_ID_GRUPO + " ) REFERENCES " + GrupoDataAccess.TABELA_NOME +
                    "( " + GrupoDataAccess.CAMPO_ID + " ) ON DELETE CASCADE " +
                    " FOREIGN KEY( " + CAMPO_ID_HORARIO + " ) REFERENCES " + HorarioDataAccess.TABELA_NOME +
                    "( " + HorarioDataAccess.CAMPO_ID + " ) ON DELETE CASCADE " +
                    " PRIMARY KEY( " + CAMPO_ID_CONTATO + " , " + CAMPO_ID_GRUPO + " , " + CAMPO_ID_HORARIO + " )" +
                    " );" ;


    protected static final String DROP_TABELA = "DROP TABLE IF EXISTS " + TABELA_NOME + " ;";

    public AgendaDataAccess(SQLiteDatabase db){
        this.db = db;
    }

    public long salvarConfiguracao(Contato contato, Grupo grupo, Horario horario, Configuracao configuracao){

        ContentValues values = new ContentValues();
        values.put(CAMPO_ID_CONTATO, contato.getId());
        values.put(CAMPO_ID_GRUPO, grupo.getId());
        values.put(CAMPO_ID_HORARIO, horario.getId());
        values.put(CAMPO_BLOQUEIO_CHAMADA, configuracao.isBloqueioChamada());
        values.put(CAMPO_ANUNCIO_CHAMADA, configuracao.isAnuncioChamada());
        values.put(CAMPO_BLOQUEIO_SMS, configuracao.isBloqueioSms());
        values.put(CAMPO_ANUNCIO_SMS, configuracao.isAnuncioSms());

        int id = (int) db.insert(TABELA_NOME, null, values);
        return id;
    }

    // TODO: some serious need of debugging
    public int atualizarConfiguracao(Contato contato, /*Grupo grupo, Horario horario,*/ Configuracao configuracao){
        Log.d("msg","contato atualizado");
        getConfiguracao(contato);
        Log.d("msgUPDATECONTATO", String.valueOf(contato.getId()) );
        Log.d("msgUPDATECONTATO", String.valueOf(contato.getNome()));
        Log.d("msgUPDATECONTATO", String.valueOf(contato.getNumeroTelefone()));
        Log.d("msgUPDATE", String.valueOf(configuracao.isBloqueioChamada()) );
        Log.d("msgUPDATE", String.valueOf(configuracao.isAnuncioChamada()));
        Log.d("msgUPDATE", String.valueOf(configuracao.isBloqueioSms()));
        Log.d("msgUPDATE", String.valueOf(configuracao.isAnuncioSms()));
        ContentValues values = new ContentValues();
        values.put(CAMPO_BLOQUEIO_CHAMADA, configuracao.isBloqueioChamada());
        values.put(CAMPO_ANUNCIO_CHAMADA, configuracao.isAnuncioChamada());
        values.put(CAMPO_BLOQUEIO_SMS, configuracao.isBloqueioSms());
        values.put(CAMPO_ANUNCIO_SMS, configuracao.isAnuncioSms());

        // TODO: TESTAR ->  CAMPO_ID_CONTATO + " = " + contato.getId() + " " +
        // CAMPO_ID_GRUPO + " = 1 " + CAMPO_ID_HORARIO + " = 1 ",
        int linhasAlteradas = 0;
        try{
            linhasAlteradas = db.update(TABELA_NOME, values, CAMPO_ID_CONTATO + " = " + contato.getId(), null);
            Log.d("msgSQL", linhasAlteradas + " linhas alteradas");

        }
        catch (Exception e){
            Log.d("msgSQL", e.getMessage());
            e.printStackTrace();
        }

        if(linhasAlteradas > 0){
            Configuracao novaConfig = getConfiguracao(contato);
            Log.d("msgUPDATE", String.valueOf(novaConfig.isBloqueioChamada()) );
            Log.d("msgUPDATE", String.valueOf(novaConfig.isAnuncioChamada()));
            Log.d("msgUPDATE", String.valueOf(novaConfig.isBloqueioSms()));
            Log.d("msgUPDATE", String.valueOf(novaConfig.isAnuncioSms()));
        }


        return linhasAlteradas;
    }

    public Configuracao getConfiguracao(Contato contato/*, int idGrupo, int idHorario*/){
        Cursor cursor = db.query(TABELA_NOME,
                new String[] {CAMPO_ID_CONTATO, CAMPO_ID_GRUPO, CAMPO_ID_HORARIO,
                CAMPO_BLOQUEIO_CHAMADA, CAMPO_ANUNCIO_CHAMADA, CAMPO_BLOQUEIO_SMS, CAMPO_ANUNCIO_SMS},
                CAMPO_ID_CONTATO +"=?"/* + CAMPO_ID_GRUPO + "=?" + CAMPO_ID_HORARIO + "=?"*/,
                new String[] {String.valueOf(contato.getId())/*,String.valueOf(idGrupo),
                        String.valueOf(idHorario)*/},
                null,null,null,null);

        if(cursor.getCount() != 0)
            try{
                cursor.moveToFirst();
            }
            catch (Exception e){
                Log.d("exc", e.getMessage());
                salvarConfiguracao(contato,new Grupo(1, "Geral"), new Horario(1, "Permanente"),
                        new Configuracao(false,false,false,false));
                return getConfiguracao(contato);
            }
        else{
            salvarConfiguracao(contato,new Grupo(1, "Geral"), new Horario(1, "Permanente"),
                    new Configuracao(false,false,false,false));
            return getConfiguracao(contato);
        }

        Configuracao config = new Configuracao(obterBooleanDeInt(cursor.getInt(3)),
                                                obterBooleanDeInt(cursor.getInt(5)),
                                                obterBooleanDeInt(cursor.getInt(4)),
                                                obterBooleanDeInt(cursor.getInt(6)));
        cursor.close();
        return config;
    }

    private boolean obterBooleanDeInt(int i){
        if(i == 1){
            return true;
        }else{
            return false;
        }
    }





}
