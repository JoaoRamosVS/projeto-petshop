package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import entities.Tutor;
import entities.Usuario;

public class TutorDAO {
	
	public List<Tutor> listarTutores() {
        List<Tutor> listaDeTutores = new ArrayList<>();
        
        try (DBConnection db = new DBConnection();
             Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT ID, NOME, CPF FROM TB_TUTORES ORDER BY NOME");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tutor tutor = new Tutor();
                tutor.setId(rs.getInt("ID"));
                tutor.setNome(rs.getString("NOME"));
                tutor.setCpf(rs.getString("CPF"));
                listaDeTutores.add(tutor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeTutores;
    }

    public boolean cadastrarNovoTutor(Tutor tutor, Usuario usuario) {
        Connection conn = null;
        UsuarioDAO usuarioDAO = new UsuarioDAO();
    	try (DBConnection db = new DBConnection()) {
            conn = db.getConnection();
            
            conn.setAutoCommit(false);
            
            if(!usuarioDAO.verificarSeEmailJaCadastrado(usuario.getEmail())) {
            	
            	String sqlUsuario = "INSERT INTO TB_USUARIOS (EMAIL, SENHA, PERFIL_ID) VALUES (?, ?, ?)";
            	
            	try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
            		
            		psUsuario.setString(1, usuario.getEmail());
            		psUsuario.setString(2, usuario.getSenha());
            		psUsuario.setInt(3, 2);
            		
            		int linhasAfetadasUsuario = psUsuario.executeUpdate();
            		
            		if (linhasAfetadasUsuario > 0) {
            			try (ResultSet generatedKeys = psUsuario.getGeneratedKeys()) {
            				if (generatedKeys.next()) {
            					int idNovoUsuario = generatedKeys.getInt(1);
            					
            					String sqlTutor = "INSERT INTO TB_TUTORES (NOME, CPF, TELEFONE, CEP, USUARIO_ID) VALUES (?, ?, ?, ?, ?)";
            					try (PreparedStatement psTutor = conn.prepareStatement(sqlTutor)) {
            						psTutor.setString(1, tutor.getNome());
            						psTutor.setString(2, tutor.getCpf());
            						psTutor.setString(3, tutor.getTelefone());
            						psTutor.setString(4, tutor.getCep());
            						psTutor.setInt(5, idNovoUsuario);
            						
            						psTutor.executeUpdate();
            					}
            				} else {
            					throw new SQLException("Falha ao obter o ID do usuário, nenhuma chave gerada.");
            				}
            			}
            		} else {
            			throw new SQLException("Falha ao inserir usuário, nenhuma linha afetada.");
            		}
            	}
            	
            	conn.commit();
            	return true;
            }
            else {
            	throw new SQLException("E-mail já cadastrado, tente com outro.");
            }


        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar tutor: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao fazer rollback: " + ex.getMessage());
            }
            return false;
        }
    }
}