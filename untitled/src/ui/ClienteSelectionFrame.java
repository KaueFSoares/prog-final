package ui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ClienteSelectionFrame extends JFrame {
    private static List<Cliente> clientes = Arrays.asList(
            new Cliente("Cliente A", Arrays.asList(
                    new Comida("Hamburguer", 25.0, true, false, true),
                    new Bebida("Coca-Cola", 7.0, false, 350)
            )),
            new Cliente("Cliente B", Arrays.asList(
                    new Comida("Salada", 18.0, true, true, false),
                    new Bebida("Suco Natural", 10.0, false, 300)
            ))
    );

    public ClienteSelectionFrame() {
        setTitle("Selecionar Cliente");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JList<Cliente> listaClientes = new JList<>(new DefaultListModel<>());
        DefaultListModel<Cliente> model = (DefaultListModel<Cliente>) listaClientes.getModel();
        clientes.forEach(model::addElement);
        add(new JScrollPane(listaClientes), BorderLayout.CENTER);

        JButton selecionar = new JButton("Selecionar");
        add(selecionar, BorderLayout.SOUTH);

        selecionar.addActionListener(e -> {
            Cliente cliente = listaClientes.getSelectedValue();
            if (cliente != null) {
                dispose();
                new PedidoFrame(cliente).setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteSelectionFrame().setVisible(true));
    }
}
