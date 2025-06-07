    package ui;

    import model.*;

    import javax.swing.*;
    import javax.swing.table.AbstractTableModel;
    import javax.swing.table.TableCellRenderer;
    import javax.swing.table.TableColumn;
    import java.awt.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.util.ArrayList;
    import java.util.List;

    public class PedidoFrame extends JFrame {
        private int numeroPedidoAtual = 1;
        private Cliente cliente;
        private List<Pedido> todosPedidos = new ArrayList<>();
        private List<ItemPedido> itensAtuais = new ArrayList<>();
        private DefaultListModel<ItemPedido> listaItensModel = new DefaultListModel<>();
        private PedidoTableModel pedidoTableModel = new PedidoTableModel();

        public PedidoFrame(Cliente cliente) {
            this.cliente = cliente;

            setTitle("Pedido - " + cliente.getNome());
            setSize(800, 600);
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

            JTable pedidosTable = new JTable(pedidoTableModel);
            pedidosTable.setRowHeight(30);
            JScrollPane scrollPane = new JScrollPane(pedidosTable);
            scrollPane.setBorder(BorderFactory.createTitledBorder("Todos os pedidos"));
            add(scrollPane, BorderLayout.EAST);

            // Configurar botões na tabela
            pedidosTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer("Finalizar"));
            pedidosTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(new JCheckBox(), "Finalizar"));
            pedidosTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer("Remover"));
            pedidosTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox(), "Remover"));

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
                    Pedido pedido = new Pedido("Pedido #" + (numeroPedidoAtual++), new ArrayList<>(itensAtuais));
                    todosPedidos.add(pedido);
                    pedidoTableModel.addPedido(pedido);
                    itensAtuais.clear();
                    listaItensModel.clear();
                    JOptionPane.showMessageDialog(this, "Pedido criado!");
                }
            });
        }

        // Modelo da Tabela
        class PedidoTableModel extends AbstractTableModel {
            private final String[] colunas = {"Pedido", "Finalizar", "Remover"};
            private final List<Pedido> pedidos = new ArrayList<>();

            public void addPedido(Pedido pedido) {
                pedidos.add(pedido);
                fireTableRowsInserted(pedidos.size() - 1, pedidos.size() - 1);
            }

            @Override
            public int getRowCount() {
                return pedidos.size();
            }

            @Override
            public int getColumnCount() {
                return colunas.length;
            }

            @Override
            public String getColumnName(int column) {
                return colunas[column];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Pedido pedido = pedidos.get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return pedido.toString();
                    case 1:
                    case 2:
                        return "Botão"; // Placeholder
                }
                return null;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 || column == 2;
            }

            public void finalizarPedido(int row) {
                Pedido p = pedidos.get(row);
                p.setPronto(true);
                fireTableRowsUpdated(row, row);
            }

            public void removerPedido(int row) {
                pedidos.remove(row);
                fireTableRowsDeleted(row, row);
            }
        }

        // Renderer dos botões
        class ButtonRenderer extends JButton implements TableCellRenderer {
            public ButtonRenderer(String label) {
                setText(label);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                return this;
            }
        }

        // Editor dos botões
        class ButtonEditor extends DefaultCellEditor {
            private JButton button;
            private String label;
            private boolean isPushed;
            private int currentRow;

            public ButtonEditor(JCheckBox checkBox, String label) {
                super(checkBox);
                this.label = label;
                button = new JButton(label);
                button.setOpaque(true);
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        fireEditingStopped();
                        if (label.equals("Finalizar")) {
                            pedidoTableModel.finalizarPedido(currentRow);
                        } else if (label.equals("Remover")) {
                            pedidoTableModel.removerPedido(currentRow);
                        }
                    }
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                                                         boolean isSelected, int row, int column) {
                currentRow = row;
                return button;
            }

            @Override
            public Object getCellEditorValue() {
                return label;
            }
        }
    }
