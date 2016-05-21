package br.com.ramada.callboy.model;

import java.security.Timestamp;

/**
 * Created by Ramada on 21/05/2016.
 */
public class FaixaHoraria {

    private Timestamp inicio;
    private Timestamp fim;

    public Timestamp getFim() {
        return fim;
    }

    public void setFim(Timestamp fim) {
        this.fim = fim;
    }

    public Timestamp getInicio() {
        return inicio;
    }

    public void setInicio(Timestamp inicio) {
        this.inicio = inicio;
    }

    public FaixaHoraria() {

    }

    public FaixaHoraria(Timestamp inicio, Timestamp fim) {

        this.inicio = inicio;
        this.fim = fim;
    }
}


