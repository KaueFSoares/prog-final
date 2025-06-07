package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

public class PedidoFrame extends JFrame {
    private Cliente cliente;
    private List<Pedido> todosPedidos = new ArrayList<>();
    private List<ItemPedido> itensAtuais = new ArrayList<>();
    private DefaultListModel<ItemPedido> listaItensModel = new DefaultListModel<>();
    private DefaultListModel<Pedido> pedidosModel = new DefaultListModel<>();

    public PedidoFrame(Cliente cliente) {
        this.cliente = cliente;

        setTitle("Pedido - " + cliente.getNome());
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel cardapioPanel = new JPanel(new GridLayout(0, 1));
        JComboBox<Produto> comboProdutos = new JComboBox<>(cliente.getCardapio().toArray(new Produto[0]));
        JTextField quantidadeField = new JTextField("1", 5);
        JButton adicionarItem = new JButton("Adicionar item");
        cardapioPanel.add(comboProdutos);
        cardapioPanel.add(new JLabel("Qtd:"));
        cardapioPanel.add(quantidadeField);
        cardapioPanel.add(adicionarItem);

        add(cardapioPanel, BorderLayout.NORTH);

        JList<ItemPedido> listaItens = new JList<>(listaItensModel);
        add(new JScrollPane(listaItens), BorderLayout.CENTER);

        JPanel rodape = new JPanel(new BorderLayout());
        JButton finalizarPedido = new JButton("Criar Pedido");
        rodape.add(finalizarPedido, BorderLayout.CENTER);
        add(rodape, BorderLayout.SOUTH);

        JList<Pedido> pedidosList = new JList<>(pedidosModel);
        pedidosList.setBorder(BorderFactory.createTitledBorder("Todos os pedidos"));
        add(new JScrollPane(pedidosList), BorderLayout.EAST);

        adicionarItem.addActionListener(e -> {
            Produto produto = (Produto) comboProdutos.getSelectedItem();
            try {
                int qtd = Integer.parseInt(quantidadeField.getText());
                if (produto != null && qtd > 0) {
                    ItemPedido item = new ItemPedido(produto, qtd);
                    itensAtuais.add(item);
                    listaItensModel.addElement(item);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida.");
            }
        });

        finalizarPedido.addActionListener(e -> {
            if (!itensAtuais.isEmpty()) {
                Pedido pedido = new Pedido("", new ArrayList<>(itensAtuais));
                todosPedidos.add(pedido);
                pedidosModel.addElement(pedido);
                itensAtuais.clear();
                listaItensModel.clear();
                JOptionPane.showMessageDialog(this, "Pedido criado!");
            }
        });

        pedidosList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    Pedido p = pedidosList.getSelectedValue();
                    if (p != null) {
                        p.setPronto(true);
                        pedidosList.repaint();
                    }
                }
            }
        });
    }
}
