package entities;

import java.math.BigDecimal;

public class Funcionario {
	private Integer id;
	private String nome;
	private String cpf;
	private String endereco;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String telefone;
    private String cargo;
    private BigDecimal salario;
    private Usuario usuario;
    
    public Funcionario() {
    	
    }

	public Funcionario(Integer id, String nome, String cpf, String endereco, String bairro, String cidade, String uf,
			String cep, String telefone, String cargo, BigDecimal salario, Usuario usuario) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.cep = cep;
		this.telefone = telefone;
		this.cargo = cargo;
		this.salario = salario;
		this.usuario = usuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
    
}
