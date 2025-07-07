package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;
import entities.Pet;

public class PetDAO {

    public boolean cadastrarPet(Pet novoPet) {
        String sql = "INSERT INTO TB_PETS (NOME, RACA, TAMANHO, PESO, IDADE, OBS, OCORRENCIAS, TUTOR_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (DBConnection db = new DBConnection();
             Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, novoPet.getNome());
        	ps.setString(2, novoPet.getRaca());
            ps.setInt(3, novoPet.getTamanho().getId());
            ps.setBigDecimal(4, novoPet.getPeso());
            ps.setInt(5, novoPet.getIdade());
            ps.setString(6, novoPet.getObs());
            ps.setString(7, novoPet.getOcorrencias());
            ps.setInt(8, novoPet.getTutor().getId());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}