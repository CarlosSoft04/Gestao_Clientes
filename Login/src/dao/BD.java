package dao;

import java.sql.Connection;


import controller.Conexao;

public class BD {
	private static Connection connection = null;
	
	public static void main(String[] args) {
		
		try {
			connection = Conexao.getInstance().abrirConexao();
			System.out.println("Base criada com sucesso");
			Conexao.getInstance().Close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
	}

}
