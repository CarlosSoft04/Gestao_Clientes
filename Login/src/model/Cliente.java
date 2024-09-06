package model;

public class Cliente {
	
	private String id;
	private String cpf_cnpj;
	private String nome;
	private String email;
	private String telefone;
	private String endereco;
	

	
	
	
	public Cliente() {
	
	}


	public Cliente(String id, String cpf_cnpj, String nome, String email, String telefone, String endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.cpf_cnpj = cpf_cnpj;
		this.email = email;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public String getCpf_cnpj() {
		return cpf_cnpj;
	}


	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	 

}
