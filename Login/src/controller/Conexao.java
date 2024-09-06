package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	
	//Essa variavel armazena a unica instancia da classe.Segue o padrao Singleton de que apenas uma instancia deve existir
	private static Conexao instancia;
	
	//Define o DRIVER a ser utilizado.Nesse caso o SQLITE
	private static final String DRIVER = "org.sqlite.JDBC";
	
	//url de conexao com o banco de dados
	private static final String BD = "jdbc:sqlite:resources/bdclientes.db";
	
	//Atributo estatico que define o tipo de conexao
	private static Connection conexao;
	
	//Construtor privado para garantir o padrao singleton.Garante que a unica maneira de obter uma instancia da classe eh acessando o metodo getInstance
	private Conexao() {
		
	}
	
	/**
	 * Garante se alguma instancia da classe ja foi criada.Se ela for null, entao cria uma nova instancia.Se ja foi criada, ela retorna a mesma instancia
	 * @return
	 */
	public static Conexao getInstance() {
		if(instancia == null) {
			instancia = new Conexao();		
		}
		return instancia;
		
	}
	
	/**
	 * Abre a conexao com o banco de dados
	 * @return
	 */
	public Connection abrirConexao() {
		try {
			//Garante que o driver do jdbc/sqlite esteja carregado e registrado antes de iniciar uma conexao
			Class.forName(DRIVER);
			
			//Tenta estaelecer a conexao com o banco de dados a partir da url configurada.A conexao eh armazena na variavel static= conexao;
			conexao = DriverManager.getConnection(BD);
			
			//Permite o controle manual das transacoes de conexao.
			conexao.setAutoCommit(false);
		/**
		 * Captura qualquer erro/excecao que possa ocorrer na tentaiva de conexao e manda uma mensagem apropriada
		 */
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
		}
		return conexao;
		
	}
	
	public void Close() {
		try {
		if(conexao != null && !conexao.isClosed()) {
			conexao.close();
			}
		} catch(SQLException e) {
			System.out.println("Erro ao fechar conexao com o banco de dados: " + e.getMessage());
			
		}finally {
			conexao = null;
		}
			
		}
	}
	
	


