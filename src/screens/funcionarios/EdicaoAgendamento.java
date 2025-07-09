package screens.funcionarios;

import java.awt.Font;
import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import dao.AgendamentoDAO;
import dao.PetDAO;
import entities.Agendamento;
import entities.Pet;

public class EdicaoAgendamento extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private Agendamento agendamento;
    private JTextField txtPeso;
    private JTextArea txtObservacoes;
    private JTextArea txtOcorrencias;
    private JComboBox<String> comboStatus;

    public EdicaoAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
        Pet pet = agendamento.getPet();

        setTitle("Editar Atendimento: " + pet.getNome());
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        int y = 20;
        txtPeso = new JTextField(pet.getPeso().toString());
        addLabelAndField("Peso (kg):", txtPeso, y);
        y += 60;

        txtObservacoes = new JTextArea(pet.getObs());
        addLabelAndTextArea("Observações:", txtObservacoes, y);
        y += 120;
        
        txtOcorrencias = new JTextArea(pet.getOcorrencias());
        addLabelAndTextArea("Ocorrências:", txtOcorrencias, y);
        y += 120;

        JLabel lblStatus = new JLabel("Status do Agendamento:");
        lblStatus.setBounds(50, y, 200, 25);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblStatus);
        y += 25;
        
        comboStatus = new JComboBox<>(new String[]{"AGENDADO", "EM ANDAMENTO", "CONCLUÍDO"});
        comboStatus.setSelectedItem(agendamento.getStatus());
        comboStatus.setBounds(50, y, 380, 30);
        add(comboStatus);
        y += 60;
        
        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        btnSalvar.setBounds(150, y, 200, 40);
        add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarAlteracoes());

        setVisible(true);
    }
    
    private void addLabelAndField(String titulo, JTextField campo, int y) {
        JLabel label = new JLabel(titulo);
        label.setBounds(50, y, 150, 25);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        add(label);
        
        campo.setBounds(50, y + 25, 380, 30);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        add(campo);
    }
    
    private void addLabelAndTextArea(String titulo, JTextArea area, int y) {
        JLabel label = new JLabel(titulo);
        label.setBounds(50, y, 150, 25);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        add(label);
        
        area.setFont(new Font("Arial", Font.PLAIN, 12));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(50, y + 25, 380, 80);
        add(scroll);
    }

    private void salvarAlteracoes() {
        try {
            Pet pet = agendamento.getPet();
            pet.setPeso(new BigDecimal(txtPeso.getText()));
            pet.setObs(txtObservacoes.getText());
            pet.setOcorrencias(txtOcorrencias.getText());
            
            PetDAO petDAO = new PetDAO();
            boolean sucessoPet = petDAO.atualizarInfoServico(pet);
            
            String novoStatus = (String) comboStatus.getSelectedItem();
            AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
            boolean sucessoAgendamento = agendamentoDAO.atualizarStatusAgendamento(agendamento.getId(), novoStatus);
            
            if (sucessoPet && sucessoAgendamento) {
                JOptionPane.showMessageDialog(this, "Alterações salvas com sucesso!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro ao salvar as alterações.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O campo 'Peso' deve ser um número válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}