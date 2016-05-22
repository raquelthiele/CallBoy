package br.com.ramada.callboy.model;

import java.util.List;

/**
 * Created by Ramada on 21/05/2016.
 */
public class Horario {

    private String nome;
    private List<FaixaHoraria> faixasHorarias;

    public Horario() {
    }

    public Horario(String nome, List<FaixaHoraria> faixasHorarias) {

        this.nome = nome;
        this.faixasHorarias = faixasHorarias;
    }

    public Horario(String nome) {

        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void adicionaFaixaHoraria(FaixaHoraria faixaHoraria){}

    public void removeFaixaHoraria(FaixaHoraria faixaHoraria){}
}
