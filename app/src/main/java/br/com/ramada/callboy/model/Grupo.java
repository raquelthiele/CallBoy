package br.com.ramada.callboy.model;

import java.util.List;

/**
 * Created by Ramada on 21/05/2016.
 */
public class Grupo {

    private int id;
    private String nome;
    private List<Contato> contatos;

    public Grupo(String nome) {
        this.nome = nome;
    }

    public Grupo(int id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void adicionaContato(Contato novoContato){}

    public void adicionaContatos(List<Contato> novosContatos){}

    public void removeContato(Contato contatoRemovido){}

    public void removeContatos(List<Contato> contatosRemovidos){}




}
