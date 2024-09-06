package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.backup;
import javax.swing.JButton;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class telaBackup extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private backup back = new backup();
	private ArrayList<String> arquivosBackup = new ArrayList<>();
	private JList<String> list; // Especificar o tipo de JList
	private String[] nomeBackup;
	private String itemSelecionado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					telaBackup frame = new telaBackup();
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
	public telaBackup() {
		setType(Type.UTILITY);
		setTitle("Backups");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 824, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 10, 738, 299);
		contentPane.add(scrollPane);

		// Inicialização do JList
		list = new JList<>();
		scrollPane.setViewportView(list);

		// Atualiza a lista de arquivos ao iniciar
		atualizarLista();

		JButton btnGerarBackup = new JButton("Gerar Backup");
		btnGerarBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(btnGerarBackup,
						"Deseja gerar o backup?") == JOptionPane.YES_NO_OPTION) {
					back.gerarBackup();
					atualizarLista();
				}
			}
		});
		btnGerarBackup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnGerarBackup.setBounds(47, 319, 117, 36);
		contentPane.add(btnGerarBackup);
		JButton btnRestaurarBackup = new JButton("Restaurar Backup");
		btnRestaurarBackup.setEnabled(false);
		btnRestaurarBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(btnRestaurarBackup,
						"Deseja restaurar o backup?") == JOptionPane.YES_NO_OPTION) {
					try {
						back.RestaurarBackup(itemSelecionado);
					} catch (IOException e1) {

						e1.printStackTrace();
					}

				}
			}
		});
		btnRestaurarBackup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRestaurarBackup.setBounds(184, 319, 164, 36);
		contentPane.add(btnRestaurarBackup);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (list.getSelectedIndex() == -1) {
						list.setSelectedIndex(e.getFirstIndex());

					}
					itemSelecionado = ((JList<String>) e.getSource()).getSelectedValue();
					if (itemSelecionado != null) {
						btnRestaurarBackup.setEnabled(true);

					}

				}

			}
		});
	}

	// Método para atualizar a lista de backups
	private void atualizarLista() {
		arquivosBackup = back.listarArquivos();
		nomeBackup = arquivosBackup.toArray(new String[0]); // Converte ArrayList para Array
		list.setListData(nomeBackup);
	}
}
