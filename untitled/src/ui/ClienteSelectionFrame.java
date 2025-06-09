package ui;

import model.Bebida;
import model.Cliente;
import model.Comida;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ClienteSelectionFrame extends JFrame {

    private static List<Cliente> clientes = Arrays.asList(
            new Cliente("Tiririca", Arrays.asList(
                    new Comida("Hamburguer", 25.0, true, false, true),
                    new Comida("Pao de Queijo", 5.0, true, false, true),
                    new Comida("Sanduiche", 7, true, false, true),
                    new Bebida("Coca-Cola", 7.0, false, 350),
                    new Bebida("Suco de Laranja", 7.0, false, 350),
                    new Bebida("Suco de granola", 7.0, false, 350)
            ),
                    "resources/images/tiririca.png"
            ),
            new Cliente("Point", Arrays.asList(
                    new Comida("Chocolate Nestle", 9.0, false, false, false),
                    new Bebida("Chocolate quente", 7.5, true, 300)
            ),
                    "resources/images/point.png"
            )
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
                new CardapioFrame(cliente).setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClienteSelectionFrame().setVisible(true));
    }
}
