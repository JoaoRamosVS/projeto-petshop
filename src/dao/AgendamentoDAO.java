package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import database.DBConnection;
import entities.Agendamento;
import entities.Funcionario;
import entities.Pet;
import entities.Servico;

public class AgendamentoDAO {

	public boolean agendarServico(Agendamento agendamento) {
		String sql = "INSERT INTO TB_AGENDAMENTO (SERVICO_ID, DT_AGENDAMENTO, CRIADOR_ID, PET_ID, FUNC_ID) VALUES (?, ?, ?, ?, ?)";

		try (DBConnection db = new DBConnection();
				Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, agendamento.getServico().getId());
			ps.setTimestamp(2, agendamento.getDataAgendamento());
			ps.setInt(3, agendamento.getCriador().getId());
			ps.setInt(4, agendamento.getPet().getId());
			ps.setInt(5, agendamento.getFuncionario().getId());

			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Timestamp> getHorariosOcupadosPorDiaEFuncionario(LocalDate data, int funcionarioId) {
		List<Timestamp> horariosOcupados = new ArrayList<>();
		String sql = "SELECT DT_AGENDAMENTO FROM TB_AGENDAMENTO WHERE DATE(DT_AGENDAMENTO) = ? AND FUNC_ID = ? AND STATUS = 'AGENDADO'";

		try (DBConnection db = new DBConnection();
				Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setDate(1, java.sql.Date.valueOf(data));
			ps.setInt(2, funcionarioId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					horariosOcupados.add(rs.getTimestamp("DT_AGENDAMENTO"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return horariosOcupados;
	}

	public List<Agendamento> listarAgendamentosPorUsuario(int usuarioId) {
		List<Agendamento> agendamentos = new ArrayList<>();
		String sql = "SELECT a.ID, a.DT_AGENDAMENTO, a.STATUS, p.NOME as PET_NOME, s.DESCRICAO as SERVICO_DESC, f.NOME as FUNC_NOME " + 
				"FROM TB_AGENDAMENTO a " + 
				"JOIN TB_PETS p ON a.PET_ID = p.ID " + 
				"JOIN TB_SERVICOS s ON a.SERVICO_ID = s.ID " + 
				"LEFT JOIN TB_FUNCIONARIOS f ON a.FUNC_ID = f.ID " + "WHERE a.CRIADOR_ID = ? " + 
				"ORDER BY a.DT_AGENDAMENTO DESC";

		try (DBConnection db = new DBConnection();
				Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, usuarioId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Agendamento agendamento = new Agendamento();
				agendamento.setId(rs.getInt("ID"));
				agendamento.setDataAgendamento(rs.getTimestamp("DT_AGENDAMENTO"));
				agendamento.setStatus(rs.getString("STATUS"));

				Pet pet = new Pet();
				pet.setNome(rs.getString("PET_NOME"));
				agendamento.setPet(pet);

				Servico servico = new Servico();
				servico.setDescricao(rs.getString("SERVICO_DESC"));
				agendamento.setServico(servico);
				
				Funcionario funcionario = new Funcionario();
                funcionario.setNome(rs.getString("FUNC_NOME"));
                agendamento.setFuncionario(funcionario);

				agendamentos.add(agendamento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return agendamentos;
	}

	public boolean cancelarAgendamento(int agendamentoId) {
		String sql = "UPDATE TB_AGENDAMENTO SET STATUS = 'CANCELADO' WHERE ID = ?";

		try (DBConnection db = new DBConnection();
				Connection conn = db.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, agendamentoId);
			int linhasAfetadas = ps.executeUpdate();
			return linhasAfetadas > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}