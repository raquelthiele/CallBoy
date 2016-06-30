package br.com.ramada.callboy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import br.com.ramada.callboy.R;
import br.com.ramada.callboy.model.Configuracao;
import br.com.ramada.callboy.model.Contato;
import br.com.ramada.callboy.model.Grupo;
import br.com.ramada.callboy.model.Horario;

import java.util.List;

import static br.com.ramada.callboy.CallBoy.BD;

public class AnuncioGeralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anunciar_geral);

        CheckBox checkbox = ( CheckBox ) findViewById( R.id.checkBoxAnunciarTodasChamadas );
        checkbox.setChecked(getEstadoAtualDeAnuncio());

    }

    private boolean getEstadoAtualDeAnuncio(){
        List<Contato> contatos = BD.contatoDAO.getAllContacts();

        Grupo grupo = BD.grupoDAO.getGrupo(1);
        Horario horario = BD.horarioDAO.getHorario(1);

        for(Contato contato : contatos){
            Configuracao config = BD.agendaDAO.getConfiguracao(contato, grupo);
            if(config.isAnuncioChamada() && config.isAnuncioSms()){
                return true;
            }
            else return false;
        }
        return false;
    }

    public void anunciarTudo(View view){
        CheckBox checkbox = ( CheckBox ) findViewById( R.id.checkBoxAnunciarTodasChamadas );

        if(checkbox.isChecked()){
            List<Contato> contatos = BD.contatoDAO.getAllContacts();

            Grupo grupo = BD.grupoDAO.getGrupo(1);
            Horario horario = BD.horarioDAO.getHorario(1);

            for(Contato contato : contatos){
                Configuracao config = BD.agendaDAO.getConfiguracao(contato, grupo);
                if(!config.isAnuncioChamada() && !config.isAnuncioSms()){
                    config.setAnuncioChamada(true);
                    config.setAnuncioSms(true);
                    config.setBloqueioChamada(false);
                    config.setBloqueioSms(false);
                    BD.agendaDAO.atualizarConfiguracao(contato,  grupo, config);
                }
            }
        }
        else{
            List<Contato> contatos = BD.contatoDAO.getAllContacts();

            Grupo grupo = BD.grupoDAO.getGrupo(1);
            Horario horario = BD.horarioDAO.getHorario(1);

            for(Contato contato : contatos){
                Configuracao config = BD.agendaDAO.getConfiguracao(contato, grupo);
                if(config.isAnuncioChamada() && config.isAnuncioSms()){
                    config.setAnuncioChamada(false);
                    config.setAnuncioSms(false);
                    BD.agendaDAO.atualizarConfiguracao(contato,  grupo, config);
                }
            }
        }

        this.finish();
    }
}
