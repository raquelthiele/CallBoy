package br.com.ramada.callboy.model;

/**
 * Created by Ramada on 21/05/2016.
 */
public class Configuracao {

    private boolean bloqueioChamada;
    private boolean anuncioChamada;
    private boolean bloqueioSms;
    private boolean anuncioSms;

    public Configuracao() {
    }

    public Configuracao(boolean bloqueioChamada, boolean bloqueioSms, boolean anuncioChamada, boolean anuncioSms) {
        this.bloqueioChamada = bloqueioChamada;
        this.bloqueioSms = bloqueioSms;
        this.anuncioChamada = anuncioChamada;
        this.anuncioSms = anuncioSms;
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
}
