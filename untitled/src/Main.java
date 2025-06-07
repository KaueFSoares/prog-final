import model.Bebida;
import model.Comida;
import model.ItemPedido;
import model.Pedido;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private DefaultListModel<ItemPedido> listaModelo = new DefaultListModel<>();
    private JList<ItemPedido> listaItens = new JList<>(listaModelo);
    private List<ItemPedido> pedidoAtual = new ArrayList<>();

    public Main() {
        setTitle("Sistema de Pedido");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de inserção
        JPanel painelInsercao = new JPanel(new GridLayout(2, 1));

        // Comida
        JPanel comidaPanel = new JPanel();
        JTextField nomeComida = new JTextField(10);
        JTextField precoComida = new JTextField(5);
        JCheckBox vegana = new JCheckBox("Vegana");
        JCheckBox semGluten = new JCheckBox("Sem Glúten");
        JCheckBox quente = new JCheckBox("Quente");
        JButton addComida = new JButton("Adicionar Comida");

        comidaPanel.add(new JLabel("Nome:"));
        comidaPanel.add(nomeComida);
        comidaPanel.add(new JLabel("Preço:"));
        comidaPanel.add(precoComida);
        comidaPanel.add(vegana);
        comidaPanel.add(semGluten);
        comidaPanel.add(quente);
        comidaPanel.add(addComida);

        // Bebida
        JPanel bebidaPanel = new JPanel();
        JTextField nomeBebida = new JTextField(10);
        JTextField precoBebida = new JTextField(5);
        JTextField mls = new JTextField(5);
        JCheckBox comGelo = new JCheckBox("Com Gelo");
        JButton addBebida = new JButton("Adicionar Bebida");

        bebidaPanel.add(new JLabel("Nome:"));
        bebidaPanel.add(nomeBebida);
        bebidaPanel.add(new JLabel("Preço:"));
        bebidaPanel.add(precoBebida);
        bebidaPanel.add(new JLabel("ml:"));
        bebidaPanel.add(mls);
        bebidaPanel.add(comGelo);
        bebidaPanel.add(addBebida);

        painelInsercao.add(comidaPanel);
        painelInsercao.add(bebidaPanel);

        // Botões e lista
        JButton finalizar = new JButton("Finalizar Pedido");

        add(painelInsercao, BorderLayout.NORTH);
        add(new JScrollPane(listaItens), BorderLayout.CENTER);
        add(finalizar, BorderLayout.SOUTH);

        // Ações
        addComida.addActionListener(e -> {
            try {
                String nome = nomeComida.getText();
                double preco = Double.parseDouble(precoComida.getText());
                Comida comida = new Comida(nome, preco, vegana.isSelected(), semGluten.isSelected(), quente.isSelected());
                adicionarItem(new ItemPedido(comida, 1));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço inválido.");
            }
        });

        addBebida.addActionListener(e -> {
            try {
                String nome = nomeBebida.getText();
                double preco = Double.parseDouble(precoBebida.getText());
                int volume = Integer.parseInt(mls.getText());
                Bebida bebida = new Bebida(nome, preco, comGelo.isSelected(), volume);
                adicionarItem(new ItemPedido(bebida, 1));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço ou volume inválido.");
            }
        });

        finalizar.addActionListener(e -> {
            Pedido pedido = new Pedido("", pedidoAtual);
            JOptionPane.showMessageDialog(this, "Total do Pedido: R$" + pedido.getTotal());
            pedidoAtual.clear();
            listaModelo.clear();
        });
    }

    private void adicionarItem(ItemPedido item) {
        pedidoAtual.add(item);
        listaModelo.addElement(item);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main janela = new Main();
            janela.setVisible(true);
        });
    }
}