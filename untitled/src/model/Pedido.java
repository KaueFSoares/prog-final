package model;

import java.util.List;

public class Pedido {

    private final String numero;
    private final List<ItemPedido> items;

    private Status status;

    public enum Status {
        PENDENTE,
        PRONTO,
    }

    public Pedido(String numero, List<ItemPedido> items) {
        this.numero = numero;
        this.items = items;
        this.status = Status.PENDENTE;
    }

    public void mudarParaPronto() {
        this.status = Status.PRONTO;
    }

    public double getTotal() {
        return items.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(0.0, Double::sum);
    }

    private boolean pronto = false;

    public boolean isPronto() {
        return pronto;
    }

    public void setPronto(boolean pronto) {
        this.pronto = pronto;
    }

    @Override
    public String toString() {
        //single line
        return numero + " - " + status + " - " + items.size() + " itens - Total: R$" + String.format("%.2f", getTotal());
    }

}
