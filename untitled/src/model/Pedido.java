package model;

import java.util.List;

public class Pedido {

    private static int contadorPedidos = 1;

    private final String numero;
    private final List<ItemPedido> items;
    private boolean pronto = false;

    public Pedido(List<ItemPedido> items) {
        this.numero = String.valueOf(contadorPedidos++);
        this.items = items;
    }

    public double getTotal() {
        return items.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(0.0, Double::sum);
    }

    public boolean isPronto() {
        return pronto;
    }

    public void setPronto(boolean pronto) {
        this.pronto = pronto;
    }

    public String getNumero() {
        return numero;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Pedido #" + numero + " - " + (pronto ? "Finalizado" : "Em preparação") +
                " - " + items.size() + " itens - Total: R$" + String.format("%.2f", getTotal());
    }
}
