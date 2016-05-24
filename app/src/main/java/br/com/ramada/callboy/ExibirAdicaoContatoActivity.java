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

import static br.com.ramada.callboy.CallBoy.BD;

public class  ExibirAdicaoContatoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_adicao_contato);
        CheckBox repeatChkBx = ( CheckBox ) findViewById( R.id.checkBoxBloquearChamada );
        repeatChkBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                validarCheckBoxDeChamadas(isChecked);

            }
        });

        CheckBox checkSMS = ( CheckBox ) findViewById( R.id.checkBoxBloquearSMS );
        checkSMS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                validarCheckBoxDeSMS(isChecked);

            }
        });
    }

    private void validarCheckBoxDeChamadas(boolean isChecked) {
        if (isChecked)
        {
            CheckBox checkBoxDisabled = ( CheckBox ) findViewById( R.id.checkBoxAnunciarChamada );
            checkBoxDisabled.setEnabled(false);

        }else {
            CheckBox checkBoxEnable = ( CheckBox ) findViewById( R.id.checkBoxAnunciarChamada );
            checkBoxEnable.setEnabled(true);
        }
    }

    private void validarCheckBoxDeSMS(boolean isChecked) {
        if ( isChecked )
        {
            CheckBox checkBoxDisabled = ( CheckBox ) findViewById( R.id.checkBoxAnunciarSMS );
            checkBoxDisabled.setEnabled(false);

        }else {
            CheckBox checkBoxEnable = ( CheckBox ) findViewById( R.id.checkBoxAnunciarSMS );
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

        Configuracao configuracao = new Configuracao(checkBoxBloquearChamada.isChecked(),checkBoxBloquearSMS.isChecked(),checkBoxAnunciarChamada.isChecked(),checkBoxAnunciarSMS.isChecked());
        Contato novoContato= new Contato(nomeContato.getText().toString(),numeroTelefone.getText().toString()/*,configuracao*/);
        int idNovoContato = BD.contatoDAO.salvarContato(novoContato);

        novoContato.setId(idNovoContato);
        BD.agendaDAO.salvarConfiguracao(novoContato, configuracao);
    }

}
