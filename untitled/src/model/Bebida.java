package model;

public class Bebida extends Produto {

    private final boolean quente;
    private final int mls;

    public Bebida(String nome, double preco, boolean quente, int mls) {
        super(nome, preco);
        this.quente = quente;
        this.mls = mls;
    }

    @Override
    public String toString() {
        return super.getNome() + " - " + super.getPreco() + " - " + (quente ? "Quente" : "Fria") + " - " + mls + "ml";
    }
}
