package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;
import entities.Funcionario;
import entities.Usuario;

public class FuncionarioDAO {

	public boolean cadastrarNovoFuncionario(Funcionario novoFuncionario, Usuario novoUsuario) {
		Connection conn = null;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		try (DBConnection db = new DBConnection()) {
			conn = db.getConnection();
			conn.setAutoCommit(false);
			
			if(!usuarioDAO.verificarSeEmailJaCadastrado(novoUsuario.getEmail())) {
				String sqlUsuario = "INSERT INTO TB_USUARIOS (EMAIL, SENHA, PERFIL_ID) VALUES (?, ?, ?)";
				
				try (PreparedStatement psUsuario =conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
					psUsuario.setString(1, novoUsuario.getEmail());					
					psUsuario.setString(2, novoUsuario.getSenha());
					psUsuario.setInt(3, 3);

					int linhasAfetadasUsuario = psUsuario.executeUpdate();
					if (linhasAfetadasUsuario > 0) {
						try (ResultSet generatedKeys = psUsuario.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								int idNovoUsuario = generatedKeys.getInt(1);
								String sqlFuncionario = "INSERT INTO TB_FUNCIONARIOS (NOME, CPF, TELEFONE, CEP, CARGO, SALARIO, USUARIO_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
								
								try (PreparedStatement psFuncionario = conn.prepareStatement(sqlFuncionario)) {
									psFuncionario.setString(1, novoFuncionario.getNome());
									psFuncionario.setString(2, novoFuncionario.getCpf());
									psFuncionario.setString(3, novoFuncionario.getTelefone());
									psFuncionario.setString(4, novoFuncionario.getCep());
									psFuncionario.setString(5, novoFuncionario.getCargo());
									psFuncionario.setBigDecimal(6, novoFuncionario.getSalario());
									psFuncionario.setInt(7, idNovoUsuario);
									
									psFuncionario.executeUpdate();
								}
							}
							else {
								throw new SQLException("Falha ao obter o ID do usuário, nenhuma chave gerada.");
							}
						}
					}
					else {
						throw new SQLException("Falha ao inserir usuário, nenhuma linha afetada.");
					}
				}
				conn.commit();
				return true;
			}
			else {
				throw new SQLException("E-mail já cadastrado, tente com outro.");
			}
			
		} 
		catch (SQLException e) {
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
