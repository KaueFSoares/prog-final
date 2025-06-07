package model;

import java.util.List;

public class Cliente {

    private final String nome;
    private final List<Produto> cardapio;

    public Cliente(String nome, List<Produto> cardapio) {
        this.nome = nome;
        this.cardapio = cardapio;
    }

    public String getNome() {
        return nome;
    }

    public List<Produto> getCardapio() {
        return cardapio;
    }

    @Override
    public String toString() {
        return "Cliente: " + nome;
    }
}
