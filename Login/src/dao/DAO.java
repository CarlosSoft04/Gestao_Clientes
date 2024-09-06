package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import controller.Conexao;
import model.Cliente;
import model.Usuario;

public class DAO {
	
	
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;

	
	/**
	 * Metodos de manipulacao de banco de dados SQLITE
	 */
	private static String CADASTRAR_CLIENTE = "INSERT INTO CLIENTE (ID, CPFCNPJ, NOME, EMAIL, TELEFONE, ENDERECO) VALUES (NULL, ?, ?, ?, ?, ?)";

	private static String CONSULTAR_CLIENTE = "SELECT * FROM CLIENTE WHERE ID = ?";

	private static String ALTERAR_CLIENTE = "UPDATE CLIENTE SET CPFCNPJ = ?, NOME = ?, EMAIL = ?, TELEFONE = ?, ENDERECO = ? WHERE ID = ?";

	private static String EXCLUIR_CLIENTE = "DELETE FROM CLIENTE WHERE ID = ?";

	private static String LISTAR_CLIENTE = "SELECT * FROM CLIENTE WHERE 1=1";

	private static String CONSULTAR_USUARIO = "SELECT USUARIO, SENHA FROM USUARIO WHERE USUARIO = ? AND SENHA = ?";


	public DAO() {

	}

	
	public void cadastrarCliente(Cliente cliente) {
		Connection connection = Conexao.getInstance().abrirConexao();
		String query = CADASTRAR_CLIENTE;
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, cliente.getCpf_cnpj());
			preparedStatement.setString(i++, cliente.getNome());
			preparedStatement.setString(i++, cliente.getEmail());
			preparedStatement.setString(i++, cliente.getTelefone());
			preparedStatement.setString(i++, cliente.getEndereco());

			preparedStatement.execute();
			connection.commit();
			JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso");

		} catch (SQLException e) {
			System.out.println("Erro ao cadastrar cliente" + e.getMessage());
		} finally {
			fecharConexao();

		}

	}
	
	public void alterarCliente(String id,Cliente cliente) {
		Connection connection = Conexao.getInstance().abrirConexao();
		String query = ALTERAR_CLIENTE;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, cliente.getCpf_cnpj());
			preparedStatement.setString(i++, cliente.getNome());
			preparedStatement.setString(i++, cliente.getEmail());
			preparedStatement.setString(i++, cliente.getTelefone());
			preparedStatement.setString(i++, cliente.getEndereco());
			preparedStatement.setString(i++,id);
			
			preparedStatement.execute();
			connection.commit();
			JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!:");
			
		} catch (SQLException e) {
			System.out.println("Erro ao alterar clientes: " + e.getMessage());
			
		
		}finally {
			fecharConexao();
		}
		
	}

	public Cliente consultarCliente(String id) throws Exception {
		Connection connection = Conexao.getInstance().abrirConexao();

		Cliente cliente = null;

		String query = CONSULTAR_CLIENTE;

		try {
			int i = 1;
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(i++, id);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				cliente = new Cliente(resultSet.getString("ID"),
						resultSet.getString("CPFCNPJ"),
						resultSet.getString("NOME"), 
						resultSet.getString("EMAIL"),
						resultSet.getString("TELEFONE"),
						resultSet.getString("ENDERECO"));

			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente: " + e.getMessage());

		} finally {
			fecharConexao();

		}

		if (cliente == null) {
			JOptionPane.showMessageDialog(null, "Cliente nao encontrado: ", "", JOptionPane.WARNING_MESSAGE);
			throw new Exception("Nao foi possivel localizar o cliente selecionado");

		}
		return cliente;

	}

	public void excluirCliente(String id) {
		Connection connection = Conexao.getInstance().abrirConexao();
		String query = EXCLUIR_CLIENTE;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			int i = 1;
			preparedStatement.setString(i++, id);
			
			preparedStatement.execute();
			connection.commit();
			
			JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso: ");
			
		} catch (SQLException e) {
			System.out.println("Erro ao excluir um cliente: " + e.getMessage());
			
		}finally {
			fecharConexao();
		}
		
	}

	
	public ArrayList<Cliente> listarCliente() throws Exception{
		Connection connection = Conexao.getInstance().abrirConexao();
		ArrayList<Cliente> clientes = new ArrayList<>();
		String query = LISTAR_CLIENTE;
		
		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				clientes.add(new Cliente(resultSet.getString("ID"),
						resultSet.getString("CPFCNPJ"),
						resultSet.getString("NOME"),
						resultSet.getString("EMAIL"),
						resultSet.getString("TELEFONE"),
						resultSet.getString("ENDERECO")));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			fecharConexao();
		}
		if(clientes.size() < 0) {
			JOptionPane.showMessageDialog(null, "Nao foi cadastrado nenhum cliente: classe listar.","",JOptionPane.WARNING_MESSAGE);
			throw new Exception("Nao foi possivel listar os clientes");
			
		}
		return clientes;
		
	
		
	}
	
	public Usuario consultarUsuarios(String nomeUsuario, String senhaCriptografada) throws Exception {
		Connection connection = Conexao.getInstance().abrirConexao();
		String query = CONSULTAR_USUARIO;
		Usuario usuario = null;
		
		try {
			int i = 1;
			preparedStatement = connection.prepareStatement(query);
			
			
			preparedStatement.setString(i++, nomeUsuario);
			preparedStatement.setString(i++, senhaCriptografada);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				usuario = new Usuario(resultSet.getInt("ID"),
						resultSet.getString("USUARIO"),
						resultSet.getString("SENHA"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			fecharConexao();
		}
		if(usuario == null) {
			JOptionPane.showMessageDialog(null, "Nao foi possvel listar os usurios","",JOptionPane.WARNING_MESSAGE);
			
		}
		return usuario;
		
	}
	
	
	
	
	private void fecharConexao() {

		try {
			if (resultSet != null) {
				resultSet.close();

			}
			if (preparedStatement != null) {
				preparedStatement.close();

			}
			Conexao.getInstance().Close();

		} catch (SQLException e) {
			System.out.println("Erro ao fechar conexao: " + e.getMessage());

		}

	}
	
	

	
	
	
	
	
	
	
	
	
}
