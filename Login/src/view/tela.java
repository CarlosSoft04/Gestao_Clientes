package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controller.Criptografia;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class tela extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                tela frame = new tela();
                frame.setVisible(true);

                // Esse trecho faz com que o view abra no meio da tela
                frame.setLocationRelativeTo(null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public tela() {
        setResizable(false);
        setType(Type.UTILITY);
        setTitle("Tela Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 437, 457);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(null);

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBorder(null);
        panel.setBounds(10, 10, 409, 400);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Bem vindo");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 27));
        lblNewLabel.setBounds(127, 36, 146, 33);
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Usuario");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel_1.setBounds(78, 113, 84, 22);
        panel.add(lblNewLabel_1);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(74, 145, 244, 22);
        panel.add(txtUsuario);
        txtUsuario.setColumns(10);

        JLabel lblNewLabel_1_1 = new JLabel("Senha");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel_1_1.setBounds(78, 196, 84, 22);
        panel.add(lblNewLabel_1_1);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(74, 228, 244, 22);
        panel.add(txtSenha);

        /**
         * Ação do botão Entrar
         */
        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ValidarCamposLogin();
            }
        });
        btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 17));
        btnEntrar.setBounds(160, 343, 92, 33);
        panel.add(btnEntrar);
    }

    /*
     * Método que pega o texto digitado pelo usuário nos campos de texto e converte
     * para String. Chamado em "ValidarCamposLogin"
     */
    private String ObterTexto(JTextField campo) {
        return campo.getText().trim();
    }

    /**
     * Método que valida o login.
     */
    public void ValidarCamposLogin() {
        String nome = ObterTexto(txtUsuario);
        String senha = new String(txtSenha.getPassword());

        // Verifica se os campos estão preenchidos, se sim ele chama "ValidarUsuario",
        // se não ele avisa ao usuário
        if (!nome.isEmpty() && !senha.isEmpty()) {
            ValidarUsuario(nome, senha);
        } else {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Método que valida e dá acesso ao usuário. Verifica se o nome é igual ao definido e senha também.
     * Se sim, ele avisa que foi liberado, se não, informa mensagem de erro
     * 
     * @param nome
     * @param senha
     */
    public void ValidarUsuario(String nome, String senha) {
        Criptografia cripto = new Criptografia(senha, Criptografia.getMd5());
        String senhaCriptografada = cripto.criptografar();
        String hashSenhaArmazenada = "E10ADC3949BA59ABBE56E057F20F883E"; // Senha criptografada para "123456"

        if ("carlos".equals(nome) && senhaCriptografada.equals(hashSenhaArmazenada)) {
            JOptionPane.showMessageDialog(this, "Acesso Liberado");
            dispose();

            principal p = new principal(); 
            p.setLocationRelativeTo(null);
            p.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha inválido", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}
