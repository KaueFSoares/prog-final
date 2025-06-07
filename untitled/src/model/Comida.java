package model;

public class Comida extends Produto {

    private final boolean salgado;
    private final boolean acompanhamento;
    private final boolean quente;

    public Comida(String nome, double preco, boolean salgado, boolean acompanhamento, boolean quente) {
        super(nome, preco);
        this.salgado = salgado;
        this.acompanhamento = acompanhamento;
        this.quente = quente;
    }

    @Override
    public String toString() {
        return super.getNome() + " - " + super.getPreco() + " - " +
                (salgado ? "Salgado" : "Doce") + ", " +
                (acompanhamento ? "Acompanhamento" : "Prato Principal") + ", " +
                (quente ? "Quente" : "Frio");
    }
}
