package ui;

import model.Pedido;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PedidosFrame extends JFrame {

    private static List<Pedido> todosPedidos = new ArrayList<>();
    private JPanel pedidosPanel;
    private CardapioFrame cardapioFrame;

    public PedidosFrame(Pedido novoPedido, CardapioFrame cardapioFrame) {
        this.cardapioFrame = cardapioFrame;

        setTitle("Pedidos");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        if (novoPedido != null) {
            todosPedidos.add(novoPedido);
        }

        JLabel titulo = new JLabel("PEDIDOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 40));
        titulo.setForeground(new Color(218, 165, 32));
        add(titulo, BorderLayout.NORTH);

        pedidosPanel = new JPanel();
        pedidosPanel.setLayout(new BoxLayout(pedidosPanel, BoxLayout.Y_AXIS));
        pedidosPanel.setBackground(new Color(211, 211, 211));

        atualizarPedidos();

        JScrollPane scrollPane = new JScrollPane(pedidosPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton novoPedidoButton = new JButton("NOVO PEDIDO");
        novoPedidoButton.setFont(new Font("Arial", Font.BOLD, 20));
        novoPedidoButton.setBackground(new Color(0, 102, 204));
        novoPedidoButton.setForeground(Color.WHITE);
        novoPedidoButton.setFocusPainted(false);
        novoPedidoButton.addActionListener(e -> {
            this.dispose();
            cardapioFrame.resetarPedido();
            cardapioFrame.setVisible(true);
        });

        JPanel botaoPanel = new JPanel();
        botaoPanel.setBackground(new Color(211, 211, 211));
        botaoPanel.add(novoPedidoButton);

        add(botaoPanel, BorderLayout.SOUTH);
    }

    private void atualizarPedidos() {
        pedidosPanel.removeAll();

        for (Pedido pedido : todosPedidos) {
            JPanel pedidoCard = criarPedidoCard(pedido);
            pedidosPanel.add(pedidoCard);
        }

        pedidosPanel.revalidate();
        pedidosPanel.repaint();
    }

    private JPanel criarPedidoCard(Pedido pedido) {
        JPanel card = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        card.setBackground(new Color(211, 211, 211));

        JLabel infoPedido = new JLabel("#" + pedido.getNumero() + " " + (pedido.isPronto() ? "FINALIZADO" : "EM ANDAMENTO"));
        infoPedido.setFont(new Font("Arial", Font.BOLD, 18));

        JButton finalizarButton = new JButton("FINALIZAR");
        finalizarButton.setBackground(new Color(0, 200, 0));
        finalizarButton.setForeground(Color.WHITE);
        finalizarButton.setFont(new Font("Arial", Font.BOLD, 16));

        finalizarButton.addActionListener(e -> {
            pedido.setPronto(true);
            atualizarPedidos();
        });

        JButton removerButton = new JButton("REMOVER");
        removerButton.setBackground(new Color(255, 69, 0));
        removerButton.setForeground(Color.WHITE);
        removerButton.setFont(new Font("Arial", Font.BOLD, 16));

        removerButton.addActionListener(e -> {
            todosPedidos.remove(pedido);
            atualizarPedidos();
        });

        card.add(infoPedido);
        if (!pedido.isPronto()) {
            card.add(finalizarButton);
        }
        card.add(removerButton);

        return card;
    }
}
