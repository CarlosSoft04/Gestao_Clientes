package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;
import controller.GeraRelatorio;
import dao.DAO;
import javax.swing.JScrollPane;
import model.Cliente;
import model.ModeloTabela;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class principal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtBuscar;
    private TableRowSorter<ModeloTabela> rowsorter;
    private ModeloTabela modeloTabela;
    private JTable table;

    /**
     * Lista que armazena os clientes cadastrados.
     */
    private ArrayList<Cliente> clientes;

    /**
     * Método inicializador
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    principal frame = new principal();
                    frame.setVisible(true);
                    frame.setLocationRelativeTo(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Cria a frame da tela principal.
     */
    public principal() {
        // Inicializa DAO e lista de clientes
        DAO dao = new DAO();
        try {
            clientes = dao.listarCliente();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 938, 630);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        /**
         * Botão de cadastrar da tela principal
         */
        JButton btnNewButton = new JButton("Cadastrar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastro telacad = new cadastro(null, principal.this);
                telacad.setLocationRelativeTo(telacad);
                telacad.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                telacad.setVisible(true);
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnNewButton.setBounds(70, 51, 134, 34);
        contentPane.add(btnNewButton);

        txtBuscar = new JTextField();
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrar();
            }
        });
        txtBuscar.setBounds(224, 51, 610, 33);
        contentPane.add(txtBuscar);
        txtBuscar.setColumns(10);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(70, 108, 764, 377);
        contentPane.add(scrollPane);

        modeloTabela = new ModeloTabela(clientes);

        table = new JTable();
        table.setModel(modeloTabela);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    try {
                        DAO dao = new DAO();
                        Cliente cliente = dao.consultarCliente(modeloTabela.getValueAt(table.getSelectedRow(), 0).toString());
                        cadastro cad = new cadastro(cliente, principal.this);
                        cad.setLocationRelativeTo(cad);
                        cad.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        cad.setVisible(true);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        rowsorter = new TableRowSorter<>(modeloTabela);
        table.setRowSorter(rowsorter);

        table.setFont(new Font("Tahoma", Font.PLAIN, 13));
        scrollPane.setViewportView(table);

        JButton btnGerarRelatorio = new JButton("Gerar Relatorio");
        btnGerarRelatorio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GeraRelatorio();
            }
        });
        btnGerarRelatorio.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnGerarRelatorio.setBounds(667, 495, 167, 39);
        contentPane.add(btnGerarRelatorio);
        
        JButton btnNewButton_1 = new JButton("BackUps");
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		telaBackup back = new telaBackup();
        		back.setVisible(true);
        	}
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnNewButton_1.setBounds(494, 501, 134, 33);
        contentPane.add(btnNewButton_1);
    }

    private void filtrar() {
        String busca = txtBuscar.getText().trim();

        if (busca.isEmpty()) {
            rowsorter.setRowFilter(null);
        } else {
            rowsorter.setRowFilter(RowFilter.regexFilter("(?i)" + busca));
        }
    }

    public void atualizarTabela() {
        DAO dao = new DAO();
        try {
            clientes = dao.listarCliente();
            modeloTabela.setClientes(clientes);  // Atualiza os dados na tabela
            modeloTabela.fireTableDataChanged();  // Notifica a tabela para recarregar os dados
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
