package br.com.ramada.callboy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

//import br.com.ramada.callboy.dao.BancoDeDados;
import br.com.ramada.callboy.dao.ContatoDataAccess;
import br.com.ramada.callboy.model.Configuracao;
import br.com.ramada.callboy.model.Contato;
import br.com.ramada.callboy.model.Grupo;
import br.com.ramada.callboy.model.Horario;

import static br.com.ramada.callboy.CallBoy.BD;

public class  ExibirAdicaoContatoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_adicao_contato);


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

    public void cadastrarContato(View view){

        EditText nomeContato = (EditText) findViewById( R.id.nomeContato);
        EditText numeroTelefone = (EditText) findViewById( R.id.numeroContato);
        CheckBox checkBoxBloquearChamada = (CheckBox) findViewById(R.id.checkBoxBloquearChamada);
        CheckBox checkBoxAnunciarChamada = (CheckBox) findViewById(R.id.checkBoxAnunciarChamada);
        CheckBox checkBoxBloquearSMS = (CheckBox) findViewById(R.id.checkBoxBloquearSMS);
        CheckBox checkBoxAnunciarSMS = (CheckBox) findViewById(R.id.checkBoxAnunciarSMS);

        Configuracao configuracao = new Configuracao(checkBoxBloquearChamada.isChecked(),
                                                        checkBoxBloquearSMS.isChecked(),
                                                        checkBoxAnunciarChamada.isChecked(),
                                                        checkBoxAnunciarSMS.isChecked());
        Contato novoContato= new Contato(nomeContato.getText().toString(),numeroTelefone.getText().toString()
                                        /*,configuracao*/);
        int idNovoContato = BD.contatoDAO.salvarContato(novoContato);

        novoContato.setId(idNovoContato);
        Grupo grupo = BD.grupoDAO.getGrupo(1);
        Horario horario = BD.horarioDAO.getHorario(1);
        BD.agendaDAO.salvarConfiguracao(novoContato, grupo, horario,configuracao);
    }

}
