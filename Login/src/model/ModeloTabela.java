package model;



import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ModeloTabela extends AbstractTableModel {

	/**
	 * Lista de clientes que armazena os dados que vao ser exibidos na tabela
	 */
	private ArrayList<Cliente> clientes;
	
	/**
	 * Um array de Strings que define o nome das colunas
	 */
	private static final String[] colunas ={
			"ID", "CPF/CNPJ", "Nome", "Email", "Telefone", "Endereco"
		};
	

	public ModeloTabela(ArrayList<Cliente> clientes) {
		super();
		this.clientes = clientes;
	}

	
	/**
	 * Metodos sobreescritos da implementacao AbstractTableModel. 
	 */
	@Override
	public int getRowCount() {
		return clientes.size();
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	//Metodo que identifca a coluna com seu respectivo nome
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Cliente cliente = clientes.get(rowIndex);
		if (columnIndex == 0) {
			return cliente.getId();

		} else if (columnIndex == 1) {
			return cliente.getCpf_cnpj();

		} else if (columnIndex == 2) {
			return cliente.getNome();

		} else if (columnIndex == 3) {
			return cliente.getEmail();

		} else if (columnIndex == 4) {
			return cliente.getTelefone();

		} else if (columnIndex == 5) {
			return cliente.getEndereco();

		} else {
			
			return null;

		}
	}
	
	/**
	 * Classe especial da implementacao " AbstractTableModel", responsavel por fazer aparecer o nome das colunas na tabela 
	 */
	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}
	
	
	public void setClientes(List<Cliente> clientes) {
	    this.clientes = (ArrayList<Cliente>) clientes;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
