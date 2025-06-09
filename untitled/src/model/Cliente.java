package model;

import java.util.List;

public class Cliente {

    private final String nome;
    private final List<Produto> cardapio;

    private final String logo;

    public Cliente(String nome, List<Produto> cardapio, String logo) {
        this.nome = nome;
        this.cardapio = cardapio;
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
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
