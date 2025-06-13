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

        List<Pedido> pedidosParaRemover = new ArrayList<>();

        for (Pedido pedido : todosPedidos) {

                JPanel pedidoCard = criarPedidoCard(pedido);
                pedidosPanel.add(pedidoCard);

        }

        todosPedidos.removeAll(pedidosParaRemover);

        pedidosPanel.revalidate();
        pedidosPanel.repaint();
    }

    private JPanel criarPedidoCard(Pedido pedido) {
        JPanel card = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        card.setBackground(new Color(211, 211, 211));

        JLabel infoPedido = new JLabel("Pedido #" + pedido.getNumero() + " - " + formatarStatus(pedido.getStatus()));
        infoPedido.setFont(new Font("Arial", Font.BOLD, 18));

        card.add(infoPedido);
        if(pedido.getStatus() != Pedido.Status.ENTREGUE){
            JButton atualizarStatusButton = new JButton("Atualizar Status");
            atualizarStatusButton.setBackground(new Color(255, 140, 0));
            atualizarStatusButton.setForeground(Color.WHITE);
            atualizarStatusButton.setFont(new Font("Arial", Font.BOLD, 16));

            atualizarStatusButton.addActionListener(e -> {
                pedido.avancarStatus();
                atualizarPedidos();
            });
            card.add(atualizarStatusButton);
        }


        return card;
    }

    private String formatarStatus(Pedido.Status status) {
        return switch (status) {
            case EM_PREPARO -> "EM PREPARO";
            case PRONTO -> "PRONTO";
            case ENTREGUE -> "ENTREGUE";
        };
    }
}
