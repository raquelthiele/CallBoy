package br.com.ramada.callboy.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ramada on 21/05/2016.
 */
public class Agenda {

    private Map<Identificador, Configuracao> agendamento = new HashMap<>();

    private List<Contato> contatos;
    private List<Grupo> grupos;
    private List<Modo> modos;
    private List<Horario> horarios;
    private List<Configuracao> configs;

    public Agenda(List<Contato> contatos, List<Grupo> grupos, List<Modo> modos, List<Horario> horarios, List<Configuracao> configs) {
        this.contatos = contatos;
        this.grupos = grupos;
        this.modos = modos;
        this.horarios = horarios;
        this.configs = configs;
    }


    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public List<Modo> getModos() {
        return modos;
    }

    public void setModos(List<Modo> modos) {
        this.modos = modos;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public void adicionaConfiguracao(Contato contato, Configuracao configuracao /*, Modo modo*/) {
        Identificador identificador = new Identificador(contato.getId(),1,1);
        agendamento.put(identificador,configuracao);

    }

    public void adicionaConfiguracao(Grupo grupo, boolean bloqueioChamada, boolean anuncioChamada, boolean bloqueioSms, boolean anuncioSms, Modo modo) {

    }

    public void adicionaConfiguracao(Contato contato, boolean bloqueioChamada, boolean anuncioChamada, boolean bloqueioSms, boolean anuncioSms, Modo modo, Horario horario) {

    }

    public void adicionaConfiguracao(Grupo grupo, boolean bloqueioChamada, boolean anuncioChamada, boolean bloqueioSms, boolean anuncioSms, Modo modo, Horario horario) {

    }

    public void removeConfiguracao(Contato contato){

    }

    public void removeConfiguracao(Grupo grupo){

    }

    public void selecionar(Contato contato){

    }

    public void selecionar(Grupo grupo){

    }


    private static class Identificador{
        private int idContato;
        private int idGrupo;
        private int idHorario;

        public Identificador(int idContato, int idGrupo, int idHorario) {
            this.idContato = idContato;
            this.idGrupo = idGrupo;
            this.idHorario = idHorario;
        }
    }



}
