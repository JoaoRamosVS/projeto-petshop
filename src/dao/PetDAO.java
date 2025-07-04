package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBConnection;
import entities.Pet;
import entities.Tutor;

public class PetDAO {

    public List<Tutor> listarTutores() {
        List<Tutor> listaDeTutores = new ArrayList<>();
        String sql = "SELECT ID, NOME FROM TB_TUTORES ORDER BY NOME";

        try (DBConnection db = new DBConnection();
             Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tutor tutor = new Tutor();
                tutor.setId(rs.getInt("ID"));
                tutor.setNome(rs.getString("NOME"));
                listaDeTutores.add(tutor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDeTutores;
    }

    public boolean cadastrarPet(Pet pet) {
        String sql = "INSERT INTO TB_PETS (RACA, TAMANHO, PESO, IDADE, OBS, OCORRENCIAS, TUTOR_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (DBConnection db = new DBConnection();
             Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, pet.getRaca());
            ps.setInt(2, pet.getTamanho());
            ps.setBigDecimal(3, pet.getPeso());
            ps.setInt(4, pet.getIdade());
            ps.setString(5, pet.getObs());
            ps.setString(6, pet.getOcorrencias());
            ps.setInt(7, pet.getTutor().getId());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}