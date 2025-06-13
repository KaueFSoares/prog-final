package model;

public class ItemPedido {
    private Produto produto;
    private int quantidade;

    public int getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return produto.getNome() + " - " + quantidade + "x - R$ " + String.format("%.2f", getSubtotal());
    }
}
