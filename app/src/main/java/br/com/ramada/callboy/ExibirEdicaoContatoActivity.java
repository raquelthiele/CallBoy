package br.com.ramada.callboy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import br.com.ramada.callboy.model.Configuracao;
import br.com.ramada.callboy.model.Contato;

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

        if(idContato != 0){
            if(BD.contatoDAO.getCount(idContato) == 0){
                Contato contato = BD.contatoDAO.getContato(idContato);;
                setaDefaults(contato);
            }

        }


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

    private void setaDefaults(Contato contato){

        EditText nomeContato = (EditText) findViewById( R.id.nomeContato);
        EditText numeroTelefone = (EditText) findViewById( R.id.numeroContato);
        CheckBox checkBoxBloquearChamada = (CheckBox) findViewById(R.id.checkBoxBloquearChamada);
        CheckBox checkBoxAnunciarChamada = (CheckBox) findViewById(R.id.checkBoxAnunciarChamada);
        CheckBox checkBoxBloquearSMS = (CheckBox) findViewById(R.id.checkBoxBloquearSMS);
        CheckBox checkBoxAnunciarSMS = (CheckBox) findViewById(R.id.checkBoxAnunciarSMS);

        nomeContato.setText(contato.getNome());
        numeroTelefone.setText(contato.getNumeroTelefone());


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
        int idNovoContato = BD.contatoDAO.atualizarContato(novoContato);

        novoContato.setId(idNovoContato);
        BD.agendaDAO.atualizarConfiguracao(novoContato, configuracao);

    }



}
