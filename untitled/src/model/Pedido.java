package model;

import java.util.List;

public class Pedido {

    private static int contadorPedidos = 1;

    private final String numero;
    private final List<ItemPedido> items;
    private Status status;

    public enum Status {
        EM_PREPARO,
        PRONTO,
        ENTREGUE
    }

    public Pedido(List<ItemPedido> items) {
        this.numero = String.valueOf(contadorPedidos++);
        this.items = items;
        this.status = Status.EM_PREPARO;
    }

    public String getNumero() {
        return numero;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public double getTotal() {
        return items.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(0.0, Double::sum);
    }

    public Status getStatus() {
        return status;
    }

    public void avancarStatus() {
        switch (status) {
            case EM_PREPARO -> status = Status.PRONTO;
            case PRONTO -> status = Status.ENTREGUE;
            // ENTREGUE será tratado fora como sinal para remoção
        }
    }

    @Override
    public String toString() {
        return "Pedido #" + numero + " - " + status +
                " - " + items.size() + " itens - Total: R$" + String.format("%.2f", getTotal());
    }
}
