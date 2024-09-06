package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import dao.DAO;
import model.Cliente;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;
import java.awt.Window.Type;

public class cadastro extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtNome;
    private JTextField txtCpfcnpj;
    private JTextField txtTelefone;
    private JTextField txtEmail;
    private JTextField txtEndereco;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    cadastro frame = new cadastro(null, null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public cadastro(Cliente cliente, principal p2) {
        setType(Type.UTILITY);
        setTitle("Tela de Cadastro de clientes");

        DAO dao = new DAO();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 658, 365);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Nome");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel.setBounds(35, 27, 45, 13);
        contentPane.add(lblNewLabel);

        txtNome = new JTextField();
        txtNome.setBounds(35, 46, 259, 19);
        contentPane.add(txtNome);
        txtNome.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("CPF/CNPJ");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(35, 75, 81, 13);
        contentPane.add(lblNewLabel_1);

        txtCpfcnpj = new JTextField();
        txtCpfcnpj.setBounds(35, 98, 259, 19);
        contentPane.add(txtCpfcnpj);
        txtCpfcnpj.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Telefone");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_2.setBounds(339, 77, 95, 13);
        contentPane.add(lblNewLabel_2);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(338, 98, 201, 19);
        contentPane.add(txtTelefone);
        txtTelefone.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Email");
        lblNewLabel_3.setForeground(new Color(0, 0, 0));
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_3.setBounds(35, 127, 45, 13);
        contentPane.add(lblNewLabel_3);

        txtEmail = new JTextField();
        txtEmail.setBounds(35, 150, 504, 19);
        contentPane.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblNewLabel_4 = new JLabel("Endereco");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_4.setBounds(35, 205, 81, 13);
        contentPane.add(lblNewLabel_4);

        txtEndereco = new JTextField();
        txtEndereco.setBackground(new Color(255, 255, 255));
        txtEndereco.setBounds(35, 228, 288, 56);
        contentPane.add(txtEndereco);
        txtEndereco.setColumns(10);

        /**
         * Ação do botão 'incluir'
         */
        JButton btnIncluir = new JButton(cliente == null ? "Incluir" : "Alterar");
        btnIncluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                String cpfcnpj = txtCpfcnpj.getText().trim();
                String telefone = txtTelefone.getText().trim();
                String email = txtEmail.getText().trim();
                String endereco = txtEndereco.getText().trim();

                if (!nome.isEmpty() && !cpfcnpj.isEmpty()) {
                    if (validarCPF(cpfcnpj) || validarCNPJ(cpfcnpj)) {
                        if (validarEmail(email)) { //&& validarTelefone(telefone)){ //&& //validarEndereco(endereco)) {  
                            if (cliente == null) {  // Verifica se está incluindo um novo cliente
                                Cliente novoCliente = new Cliente(null, nome, cpfcnpj, email, telefone, endereco);
                                dao.cadastrarCliente(novoCliente);
                            } else {  // Senão, está alterando um cliente existente
                                cliente.setNome(nome);
                                cliente.setCpf_cnpj(cpfcnpj);
                                cliente.setEmail(email);
                                cliente.setTelefone(telefone);
                                cliente.setEndereco(endereco);
                                dao.alterarCliente(cliente.getId(), cliente);
                            }

                            // Verifique se p2 não é null antes de chamar atualizarTabela
                            if (p2 != null) {
                                p2.atualizarTabela();  // Chama o método para atualizar a tabela
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro: Tela principal é nula.");
                            }

                            dispose();  // Fecha a tela de cadastro após a ação
                        } else {
                            JOptionPane.showMessageDialog(null, "Dados inválidos: Confira o Email, Telefone ou Endereço.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "CPF ou CNPJ inválido.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Confira os campos Nome e CPF/CNPJ");
                }
            }
        });
        btnIncluir.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnIncluir.setBounds(370, 253, 95, 31);
        contentPane.add(btnIncluir);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBorder(new EmptyBorder(2, 2, 2, 2));
        btnExcluir.setForeground(new Color(255, 255, 255));
        btnExcluir.setBackground(new Color(184, 57, 35));
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (cliente != null) {
                    dao.excluirCliente(cliente.getId());
                    abrirTelaPrincipal(p2);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro: Cliente não encontrado.");
                }
            }
        });
        btnExcluir.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnExcluir.setBounds(497, 253, 95, 31);
        contentPane.add(btnExcluir);

        // Se cliente não for null, preenche os campos e torna o botão Excluir visível
        if (cliente != null) {
            Preenchercampos(cliente);
            btnExcluir.setVisible(true);
        } else {
            btnExcluir.setVisible(false);  // Garante que o botão Excluir esteja oculto se não houver cliente
        }
    }

    private void Preenchercampos(Cliente cliente) {
        txtNome.setText(cliente.getNome());
        txtCpfcnpj.setText(cliente.getCpf_cnpj());
        txtEmail.setText(cliente.getEmail());
        txtTelefone.setText(cliente.getTelefone());
        txtEndereco.setText(cliente.getEndereco());
    }

    private void abrirTelaPrincipal(principal jPrincipal) {
        if (jPrincipal != null) {
            jPrincipal.dispose();
        }
        jPrincipal = new principal();
        jPrincipal.setLocationRelativeTo(jPrincipal);
        jPrincipal.setVisible(true);
    }

    // Valida o CPF
    private boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11 || cpf.chars().distinct().count() == 1) return false;

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int digito1 = (soma * 10) % 11;
        if (digito1 == 10) digito1 = 0;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int digito2 = (soma * 10) % 11;
        if (digito2 == 10) digito2 = 0;

        return digito1 == Character.getNumericValue(cpf.charAt(9)) &&
               digito2 == Character.getNumericValue(cpf.charAt(10));
    }

    // Valida o CNPJ
    private boolean validarCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9]", "");
        if (cnpj.length() != 14 || cnpj.chars().distinct().count() == 1) return false;

        int[] peso1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma1 = 0;
        for (int i = 0; i < 12; i++) {
            soma1 += Character.getNumericValue(cnpj.charAt(i)) * peso1[i];
        }
        int digito1 = 11 - (soma1 % 11);
        if (digito1 >= 10) digito1 = 0;

        int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma2 = 0;
        for (int i = 0; i < 13; i++) {
            soma2 += Character.getNumericValue(cnpj.charAt(i)) * peso2[i];
        }
        int digito2 = 11 - (soma2 % 11);
        if (digito2 >= 10) digito2 = 0;

        return digito1 == Character.getNumericValue(cnpj.charAt(12)) &&
               digito2 == Character.getNumericValue(cnpj.charAt(13));
    }

    // Valida o e-mail
    private boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }

    // Valida o telefone (formato brasileiro)
//    private boolean validarTelefone(String telefone) {
//        String regex = "\\(\\d{2}\\) \\d{4,5}-\\d{4}";
//        return telefone.matches(regex);
//    }

    // Valida o endereço (checa se não está vazio e tem comprimento mínimo)
//    private boolean validarEndereco(String endereco) {
//        return endereco != null && !endereco.trim().isEmpty() && endereco.length() >= 5;
//    }
}
