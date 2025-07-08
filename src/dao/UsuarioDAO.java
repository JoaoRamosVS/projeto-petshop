package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import entities.Perfil;
import entities.Usuario;

public class UsuarioDAO {

	public UsuarioDAO() {}
	
	public Usuario autenticarUsuario(String email, String senha) {
		try(DBConnection db = new DBConnection()) {
			Connection conn = db.getConnection();
			PreparedStatement query = conn.prepareStatement("SELECT * FROM TB_USUARIOS INNER JOIN TB_PERFIS ON TB_USUARIOS.PERFIL_ID = TB_PERFIS.ID WHERE TB_USUARIOS.EMAIL = ? AND TB_USUARIOS.SENHA = ?");
			query.setString(1, email);
			query.setString(2, senha);
			
			ResultSet result = query.executeQuery();
			if(result.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(result.getInt("ID"));
				usuario.setEmail(result.getString("EMAIL"));
				usuario.setSenha(result.getString("SENHA"));
				usuario.setFoto(result.getString("FOTO"));
				usuario.setAtivo(result.getString("ATIVO"));
				usuario.setPerfil(new Perfil());
				usuario.getPerfil().setDescricao(result.getString("DESCRICAO"));
				return usuario;
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			System.err.println("Ocorreu um erro: " + e.getMessage());
			return null;
		}
	}
	
	public List<Usuario> listarUsuarios() {
		List<Usuario> listaDeUsuarios = new ArrayList<>();
		
		try(DBConnection db = new DBConnection();
				Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement("SELECT TU.ID, TU.EMAIL, TU.ATIVO, TP.DESCRICAO FROM TB_USUARIOS TU JOIN TB_PERFIS TP ON TU.PERFIL_ID = TP.ID WHERE NOT EMAIL = 'admin' ORDER BY ATIVO DESC, EMAIL");
				ResultSet rs = ps.executeQuery()) {
			
			while(rs.next()) {
				Usuario usuario = new Usuario();
				Perfil perfil = new Perfil();
				usuario.setId(rs.getInt("ID"));
				usuario.setEmail(rs.getString("EMAIL"));
				usuario.setAtivo(rs.getString("ATIVO"));
				
				perfil.setDescricao(rs.getString("DESCRICAO"));
				usuario.setPerfil(perfil);
				listaDeUsuarios.add(usuario);
			}
		}
		catch (SQLException e) {
			System.err.println("Erro ao buscar usuários: " + e.getMessage());
			e.printStackTrace();
		}
		return listaDeUsuarios;
	}
	
	public boolean inativarUsuario(String email) {
	    try (DBConnection db = new DBConnection()) {
	    	Connection conn = db.getConnection();
	    	PreparedStatement query = conn.prepareStatement("UPDATE TB_USUARIOS SET ATIVO = 'N' WHERE EMAIL = ?");
	        query.setString(1, email);

	        int linhasAfetadas = query.executeUpdate();
	        return linhasAfetadas > 0;

	    } catch (Exception e) {
	        System.err.println("Ocorreu um erro: " + e.getMessage());
	        return false;
	    }
	}
	
	public Boolean verificarSeEmailJaCadastrado(String email) {
		try(DBConnection db = new DBConnection()) {
			Connection conn = db.getConnection();
			PreparedStatement query = conn.prepareStatement("SELECT ID FROM TB_USUARIOS WHERE EMAIL = ?");
			query.setString(1, email);
			
			ResultSet result = query.executeQuery();
			if(result.next()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			System.err.println("Ocorreu um erro: " + e.getMessage());
			return null;
		}
	}
}
