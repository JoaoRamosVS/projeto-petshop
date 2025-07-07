package entities;

import java.math.BigDecimal;

import enums.TamanhoPetEnum;

public class Pet {
    private Integer id;
    private String nome;
    private String raca;
    private TamanhoPetEnum tamanho;
    private BigDecimal peso;
    private int idade;
    private String obs;
    private String ocorrencias;
    private Tutor tutor;

    public Pet(Integer id, String nome, String raca, TamanhoPetEnum tamanho, BigDecimal peso, int idade, String obs, String ocorrencias, Tutor tutor) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.tamanho = tamanho;
        this.peso = peso;
        this.idade = idade;
        this.obs = obs;
        this.ocorrencias = ocorrencias;
        this.tutor = tutor;
    }
    
    public Pet() {
    	
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

	public String getRaca() {
		return raca;
	}

	public void setRaca(String raca) {
		this.raca = raca;
	}

	public TamanhoPetEnum getTamanho() {
		return tamanho;
	}

	public void setTamanho(TamanhoPetEnum tamanho) {
		this.tamanho = tamanho;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getOcorrencias() {
		return ocorrencias;
	}

	public void setOcorrencias(String ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

}
