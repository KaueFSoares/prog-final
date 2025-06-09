package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CardapioFrame extends JFrame {

    private Cliente cliente;
    private List<ItemPedido> itensSelecionados = new ArrayList<>();
    private JPanel pedidoPanel;
    private DefaultListModel<ItemPedido> pedidoListModel;
    private JList<ItemPedido> pedidoList;
    private JLabel totalLabel;

    public CardapioFrame(Cliente cliente) {
        this.cliente = cliente;

        setTitle(cliente.getNome());
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ImageIcon logoOriginal = new ImageIcon(getClass().getClassLoader().getResource("resources/images/logo.png"));
        Image imagemRedimensionada = logoOriginal.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(imagemRedimensionada);
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        add(logoLabel, BorderLayout.NORTH);

        JPanel produtosPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        produtosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Produto produto : cliente.getCardapio()) {
            JPanel produtoCard = criarProdutoCard(produto);
            produtosPanel.add(produtoCard);
        }

        JScrollPane scrollProdutos = new JScrollPane(produtosPanel);
        add(scrollProdutos, BorderLayout.CENTER);

        pedidoPanel = new JPanel();
        pedidoPanel.setLayout(new BoxLayout(pedidoPanel, BoxLayout.Y_AXIS));
        pedidoPanel.setBorder(BorderFactory.createTitledBorder("Seu Pedido"));

        pedidoListModel = new DefaultListModel<>();
        pedidoList = new JList<>(pedidoListModel);
        pedidoPanel.add(new JScrollPane(pedidoList));

        totalLabel = new JLabel("Total: R$ 0.00", JLabel.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pedidoPanel.add(totalLabel);

        JButton finalizarPedidoButton = new JButton("Finalizar Pedido");
        finalizarPedidoButton.setBackground(Color.RED);
        finalizarPedidoButton.setForeground(Color.WHITE);
        finalizarPedidoButton.setFont(new Font("Arial", Font.BOLD, 20));

        finalizarPedidoButton.addActionListener(e -> {
            if (!itensSelecionados.isEmpty()) {
                Pedido pedido = new Pedido(new ArrayList<>(itensSelecionados));
                this.setVisible(false);
                new PedidosFrame(pedido, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seu pedido está vazio.");
            }
        });

        pedidoPanel.add(finalizarPedidoButton);

        add(pedidoPanel, BorderLayout.SOUTH);
    }

    private JPanel criarProdutoCard(Produto produto) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 400));

        String nomeImagem = "resources/images/" + produto.getNome().toLowerCase().replace(" ", "-") + ".png";
        java.net.URL imagemUrl = getClass().getClassLoader().getResource(nomeImagem);

        if (imagemUrl == null) {
            imagemUrl = getClass().getClassLoader().getResource("resources/images/null.png");
        }

        ImageIcon icon = new ImageIcon(imagemUrl);

        JLabel imagemLabel = new JLabel(icon);
        imagemLabel.setPreferredSize(new Dimension(300, 300));
        imagemLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel nomeLabel = new JLabel(produto.getNome(), JLabel.CENTER);
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel precoLabel = new JLabel("R$ " + String.format("%.2f", produto.getPreco()), JLabel.CENTER);
        precoLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Arial", Font.BOLD, 20));
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(Color.WHITE);

        addButton.addActionListener(e -> adicionarProduto(produto));

        JButton removeButton = new JButton("-");
        removeButton.setFont(new Font("Arial", Font.BOLD, 20));
        removeButton.setBackground(Color.RED);
        removeButton.setForeground(Color.WHITE);

        removeButton.addActionListener(e -> removerProduto(produto));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(imagemLabel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(nomeLabel);
        infoPanel.add(precoLabel);

        centerPanel.add(infoPanel, BorderLayout.SOUTH);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.NORTH);

        return panel;
    }

    private void adicionarProduto(Produto produto) {
        ItemPedido item = new ItemPedido(produto, 1);
        itensSelecionados.add(item);
        pedidoListModel.addElement(item);
        atualizarTotal();
    }

    private void removerProduto(Produto produto) {
        for (int i = 0; i < itensSelecionados.size(); i++) {
            if (itensSelecionados.get(i).getProduto().equals(produto)) {
                itensSelecionados.remove(i);
                pedidoListModel.remove(i);
                break;
            }
        }
        atualizarTotal();
    }

    private void atualizarTotal() {
        double total = 0;
        for (ItemPedido item : itensSelecionados) {
            total += item.getProduto().getPreco() * item.getQuantidade();
        }
        totalLabel.setText("Total: R$ " + String.format("%.2f", total));
    }

    public void resetarPedido() {
        itensSelecionados.clear();
        pedidoListModel.clear();
        atualizarTotal();
    }
}
