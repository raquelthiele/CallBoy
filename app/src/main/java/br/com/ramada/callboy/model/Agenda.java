package br.com.ramada.callboy.model;

import java.util.List;

/**
 * Created by Ramada on 21/05/2016.
 */
public class Agenda {
    private boolean bloqueioChamada;
    private boolean anuncioChamada;
    private boolean bloqueioSms;
    private boolean anuncioSms;

    private List<Contato> contatos;
    private List<Grupo> grupos;
    private List<Modo> modos;
    private List<Horario> horarios;

    public Agenda(boolean bloqueioChamada, boolean anuncioChamada, boolean bloqueioSms, boolean anuncioSms, List<Contato> contatos, List<Grupo> grupos, List<Modo> modos, List<Horario> horarios) {
        this.bloqueioChamada = bloqueioChamada;
        this.anuncioChamada = anuncioChamada;
        this.bloqueioSms = bloqueioSms;
        this.anuncioSms = anuncioSms;
        this.contatos = contatos;
        this.grupos = grupos;
        this.modos = modos;
        this.horarios = horarios;
    }

    public boolean isBloqueioChamada() {
        return bloqueioChamada;
    }

    public void setBloqueioChamada(boolean bloqueioChamada) {
        this.bloqueioChamada = bloqueioChamada;
    }

    public boolean isAnuncioChamada() {
        return anuncioChamada;
    }

    public void setAnuncioChamada(boolean anuncioChamada) {
        this.anuncioChamada = anuncioChamada;
    }

    public boolean isBloqueioSms() {
        return bloqueioSms;
    }

    public void setBloqueioSms(boolean bloqueioSms) {
        this.bloqueioSms = bloqueioSms;
    }

    public boolean isAnuncioSms() {
        return anuncioSms;
    }

    public void setAnuncioSms(boolean anuncioSms) {
        this.anuncioSms = anuncioSms;
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

    public void adicionaConfiguracao(Contato contato, boolean bloqueioChamada, boolean anuncioChamada, boolean bloqueioSms, boolean anuncioSms, Modo modo) {

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



    }
