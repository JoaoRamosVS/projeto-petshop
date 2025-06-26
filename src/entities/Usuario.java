package entities;

public class Usuario {
	private Integer id;
    private String email;
    private String senha;
    private String foto;
    private char ativo; // 'S' ou 'N'
    private Perfil perfil;

    public Usuario(Integer id, String email, String senha, String foto, char ativo, Perfil perfil) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.foto = foto;
        this.ativo = ativo;
        this.perfil = perfil;
    }

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public char getAtivo() {
		return ativo;
	}

	public void setAtivo(char ativo) {
		this.ativo = ativo;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}
