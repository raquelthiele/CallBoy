package br.com.ramada.callboy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import br.com.ramada.callboy.R;
import br.com.ramada.callboy.model.Configuracao;
import br.com.ramada.callboy.model.Contato;
import br.com.ramada.callboy.model.Grupo;
import br.com.ramada.callboy.model.Horario;

import java.util.List;

import static br.com.ramada.callboy.CallBoy.BD;

public class BloqueioGeralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueio_geral);

        CheckBox checkbox = ( CheckBox ) findViewById( R.id.checkBoxBloquearTodasChamadas );
        if(getEstadoAtualDeBloqueio()){
            Log.d("msgestressada", "capeta");
        }

        checkbox.setChecked(getEstadoAtualDeBloqueio());


    }

    private boolean getEstadoAtualDeBloqueio(){
        List<Contato> contatos = BD.contatoDAO.getAllContacts();

        Grupo grupo = BD.grupoDAO.getGrupo(1);

        for(Contato contato : contatos){

            Configuracao config = BD.agendaDAO.getConfiguracao(contato, grupo);
            if(config.isBloqueioSms() && config.isBloqueioChamada()){
                return true;
            }
            else return false;
        }
        return false;
    }

    public void bloquearTudo(View view){
        CheckBox checkbox = ( CheckBox ) findViewById( R.id.checkBoxBloquearTodasChamadas );

        if(checkbox.isChecked()){
            List<Contato> contatos = BD.contatoDAO.getAllContacts();

            Grupo grupo = BD.grupoDAO.getGrupo(1);

            for(Contato contato : contatos){
                Configuracao config = BD.agendaDAO.getConfiguracao(contato, grupo);
                if(!config.isBloqueioSms() && !config.isBloqueioChamada()){
                    Log.d("atrasado", contato.getNome());
                    config.setBloqueioChamada(true);
                    config.setBloqueioSms(true);
                    config.setAnuncioSms(false);
                    config.setAnuncioChamada(false);
                    BD.agendaDAO.atualizarConfiguracao(contato,  grupo, config);
                }
            }
        }
        else{
            List<Contato> contatos = BD.contatoDAO.getAllContacts();

            Grupo grupo = BD.grupoDAO.getGrupo(1);

            for(Contato contato : contatos){
                Configuracao config = BD.agendaDAO.getConfiguracao(contato, grupo);
                if(config.isBloqueioSms() && config.isBloqueioChamada()){
                    config.setBloqueioChamada(false);
                    config.setBloqueioSms(false);
                    BD.agendaDAO.atualizarConfiguracao(contato,  grupo, config);
                }
            }
        }

        this.finish();
    }
}
