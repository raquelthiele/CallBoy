package br.com.ramada.callboy.model;

import java.util.*;

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


    public List<Contato> getContatos(){
        List<Contato> contatos = new ArrayList<>();
        Set<Identificador> identificadores = agendamento.keySet();
        Iterator<Identificador> iterator = identificadores.iterator();

        while(iterator.hasNext()){
            contatos.add(iterator.next().getContato());
        }

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

    public void adicionaConfiguracao(Contato contato, Grupo grupo, Horario horario,
                                     Configuracao configuracao /*, Modo modo*/) {
        Identificador identificador = new Identificador(contato,grupo,horario);
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
        private Contato contato;
        private Grupo grupo;
        private Horario horario;

        protected Identificador(Contato contato, Grupo grupo, Horario horario) {
            this.contato = contato;
            this.grupo = grupo;
            this.horario = horario;
        }

        protected Contato getContato() {
            return contato;
        }

        protected void setContato(Contato contato) {
            this.contato = contato;
        }

        protected Grupo getGrupo() {
            return grupo;
        }

        protected void setGrupo(Grupo grupo) {
            this.grupo = grupo;
        }

        protected Horario getHorario() {
            return horario;
        }

        protected void setHorario(Horario horario) {
            this.horario = horario;
        }
    }



}
