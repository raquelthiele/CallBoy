package br.com.ramada.callboy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import br.com.ramada.callboy.R;
import br.com.ramada.callboy.model.Configuracao;
import br.com.ramada.callboy.model.Contato;
import br.com.ramada.callboy.model.Grupo;
import br.com.ramada.callboy.model.Horario;

import static br.com.ramada.callboy.CallBoy.BD;

/**
 * Created by danie on 24/05/2016.
 */
public class ExibirEdicaoContatoActivity extends AppCompatActivity {

    private int idContato;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_edicao_contato);

        int idContato = getIntent().getIntExtra("idContato", 0);
        this.idContato = idContato;
        Log.d("msgIDCONTATO", String.valueOf(this.idContato));

        if(idContato != 0){
            if(BD.contatoDAO.getCount(idContato) != 0){
                Contato contato = BD.contatoDAO.getContato(idContato);
                Configuracao config = BD.agendaDAO.getConfiguracao(contato);
                contato.setConfiguracao(config);
                setaDefaults(contato);
            }

        }


        CheckBox checkBloqueio = ( CheckBox ) findViewById( R.id.checkBoxBloquearChamada );
        checkBloqueio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                validarCheckBoxBloqueioDeChamadas(isChecked);

            }
        });

        CheckBox checkAnuncio = ( CheckBox ) findViewById( R.id.checkBoxAnunciarChamada);
        checkAnuncio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                validarCheckBoxAnuncioDeChamadas(isChecked);

            }
        });

        CheckBox checkBloqueioSMS = ( CheckBox ) findViewById( R.id.checkBoxBloquearSMS );
        checkBloqueioSMS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                validarCheckBoxBloqueioDeSMS(isChecked);

            }
        });

        CheckBox checkAnuncioSMS = ( CheckBox ) findViewById( R.id.checkBoxAnunciarSMS );
        checkAnuncioSMS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                validarCheckBoxAnuncioDeSMS(isChecked);

            }
        });
    }

    private void validarCheckBoxBloqueioDeChamadas(boolean isChecked) {
        if (isChecked)
        {
            CheckBox checkBoxDisabled = ( CheckBox ) findViewById( R.id.checkBoxAnunciarChamada );
            checkBoxDisabled.setEnabled(false);
            checkBoxDisabled.setChecked(false);

        }else {
            CheckBox checkBoxEnable = ( CheckBox ) findViewById( R.id.checkBoxAnunciarChamada );
            checkBoxEnable.setEnabled(true);
        }
    }
    private void validarCheckBoxAnuncioDeChamadas(boolean isChecked) {
        if (isChecked)
        {
            CheckBox checkBoxDisabled = ( CheckBox ) findViewById( R.id.checkBoxBloquearChamada );
            checkBoxDisabled.setEnabled(false);
            checkBoxDisabled.setChecked(false);

        }else {
            CheckBox checkBoxEnable = ( CheckBox ) findViewById( R.id.checkBoxBloquearChamada );
            checkBoxEnable.setEnabled(true);
        }
    }


    private void validarCheckBoxBloqueioDeSMS(boolean isChecked) {
        if ( isChecked )
        {
            CheckBox checkBoxDisabled = ( CheckBox ) findViewById( R.id.checkBoxAnunciarSMS );
            checkBoxDisabled.setEnabled(false);
            checkBoxDisabled.setChecked(false);

        }else {
            CheckBox checkBoxEnable = ( CheckBox ) findViewById( R.id.checkBoxAnunciarSMS );
            checkBoxEnable.setEnabled(true);
        }
    }

    private void validarCheckBoxAnuncioDeSMS(boolean isChecked) {
        if ( isChecked )
        {
            CheckBox checkBoxDisabled = ( CheckBox ) findViewById( R.id.checkBoxBloquearSMS );
            checkBoxDisabled.setEnabled(false);
            checkBoxDisabled.setChecked(false);

        }else {
            CheckBox checkBoxEnable = ( CheckBox ) findViewById( R.id.checkBoxBloquearSMS );
            checkBoxEnable.setEnabled(true);
        }
    }

    private void setaDefaults(Contato contato){

        EditText nomeContato = (EditText) findViewById( R.id.nomeContato);
        EditText numeroTelefone = (EditText) findViewById( R.id.numeroContato);
        CheckBox checkBoxBloquearChamada = (CheckBox) findViewById(R.id.checkBoxBloquearChamada);
        CheckBox checkBoxAnunciarChamada = (CheckBox) findViewById(R.id.checkBoxAnunciarChamada);
        CheckBox checkBoxBloquearSMS = (CheckBox) findViewById(R.id.checkBoxBloquearSMS);
        CheckBox checkBoxAnunciarSMS = (CheckBox) findViewById(R.id.checkBoxAnunciarSMS);


        nomeContato.setText(contato.getNome());
        numeroTelefone.setText(contato.getNumeroTelefone());

        if(contato.getConfiguracao() != null){
            if(contato.getConfiguracao().isBloqueioChamada()){
                checkBoxBloquearChamada.setChecked(contato.getConfiguracao().isBloqueioChamada());
                validarCheckBoxBloqueioDeChamadas(contato.getConfiguracao().isBloqueioChamada());
            }
            else if(contato.getConfiguracao().isAnuncioChamada()){
                checkBoxAnunciarChamada.setChecked(contato.getConfiguracao().isAnuncioChamada());
                validarCheckBoxAnuncioDeChamadas(contato.getConfiguracao().isAnuncioChamada());
            }
            if(contato.getConfiguracao().isBloqueioSms()){
                checkBoxBloquearSMS.setChecked(contato.getConfiguracao().isBloqueioSms());
                validarCheckBoxBloqueioDeSMS(contato.getConfiguracao().isBloqueioSms());
            }
            else if(contato.getConfiguracao().isAnuncioSms()){
                checkBoxAnunciarSMS.setChecked(contato.getConfiguracao().isAnuncioSms());
                validarCheckBoxAnuncioDeSMS(contato.getConfiguracao().isBloqueioSms());
            }
        }
    }

    public void atualizarContato(View view){
        EditText nomeContato = (EditText) findViewById( R.id.nomeContato);
        EditText numeroTelefone = (EditText) findViewById( R.id.numeroContato);
        CheckBox checkBoxBloquearChamada = (CheckBox) findViewById(R.id.checkBoxBloquearChamada);
        CheckBox checkBoxAnunciarChamada = (CheckBox) findViewById(R.id.checkBoxAnunciarChamada);
        CheckBox checkBoxBloquearSMS = (CheckBox) findViewById(R.id.checkBoxBloquearSMS);
        CheckBox checkBoxAnunciarSMS = (CheckBox) findViewById(R.id.checkBoxAnunciarSMS);

        Configuracao configuracao = new Configuracao(checkBoxBloquearChamada.isChecked(),checkBoxBloquearSMS.isChecked(),checkBoxAnunciarChamada.isChecked(),checkBoxAnunciarSMS.isChecked());
        Contato novoContato= new Contato(this.idContato,nomeContato.getText().toString(),
                                            numeroTelefone.getText().toString()/*,configuracao*/);
        Log.d("DEBUG", novoContato.getNome());
        Log.d("DEBUG", novoContato.getNumeroTelefone());

        BD.contatoDAO.updateContato(novoContato);
        Grupo grupo = BD.grupoDAO.getGrupo(1);
        Horario horario = BD.horarioDAO.getHorario(1);
        BD.agendaDAO.atualizarConfiguracao(novoContato, configuracao);

        this.finish();

    }





}
