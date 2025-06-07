package model;

import java.util.List;

public class Pedido {

    private final String numero;
    private final List<ItemPedido> items;

    public Pedido(String numero, List<ItemPedido> items) {
        this.numero = numero;
        this.items = items;
    }

    public double getTotal() {
        return items.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(0.0, Double::sum);
    }

    private boolean pronto = false;

    public void setPronto(boolean pronto) {
        this.pronto = pronto;
    }

    @Override
    public String toString() {
        return numero + " - " + (pronto ? "Pronto" : "Em preparação") + " - " + items.size() + " itens - Total: R$" + String.format("%.2f", getTotal());
    }

}
