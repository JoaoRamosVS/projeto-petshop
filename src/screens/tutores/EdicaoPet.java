package screens.tutores;

import java.awt.Font;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import dao.PetDAO;
import entities.Pet;
import enums.TamanhoPetEnum;

public class EdicaoPet extends JFrame {
    private static final long serialVersionUID = 1L;

    private Pet pet;
    private JComboBox<TamanhoPetEnum> comboTamanho;
    private JTextField txtNome = new JTextField();
    private JTextField txtRaca = new JTextField();
    private JFormattedTextField txtDtNascimento;
    private JTextField txtPeso = new JTextField();

    public EdicaoPet(Pet pet) {
        this.pet = pet;

        setTitle("Editando Pet: " + pet.getNome());
        setSize(500, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            txtDtNascimento = new JFormattedTextField(mascaraData);
        } catch (ParseException e) {
            e.printStackTrace();
            txtDtNascimento = new JFormattedTextField();
        }

        preencherCampos();

        addLabelAndField("Nome do Pet:", 30, txtNome, 50);
        addLabelAndField("Raça:", 90, txtRaca, 110);
        addLabelAndField("Data de Nascimento:", 150, txtDtNascimento, 170);
        addLabelAndField("Peso (kg, ex: 5.4):", 210, txtPeso, 230);
        
        JLabel lblTamanho = new JLabel("Tamanho:");
        lblTamanho.setBounds(50, 270, 150, 20);
        add(lblTamanho);
        
        comboTamanho = new JComboBox<>(TamanhoPetEnum.values());
        comboTamanho.setSelectedItem(pet.getTamanho());
        comboTamanho.setBounds(50, 290, 380, 30);
        add(comboTamanho);
        
        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setBounds(160, 350, 180, 40);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnSalvar);
        
        btnSalvar.addActionListener(e -> salvarAlteracoesPet());

        setVisible(true);
    }
    
    private void preencherCampos() {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtNome.setText(pet.getNome());
        txtRaca.setText(pet.getRaca());
        if (pet.getDtNascimento() != null) {
            txtDtNascimento.setText(pet.getDtNascimento().format(formatador));
        }
        txtPeso.setText(pet.getPeso().toString());
    }
    
    private void salvarAlteracoesPet() {
        try {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            pet.setNome(txtNome.getText());
            pet.setRaca(txtRaca.getText());
            pet.setDtNascimento(LocalDate.parse(txtDtNascimento.getText(), formatador));
            pet.setPeso(new BigDecimal(txtPeso.getText()));
            pet.setTamanho((TamanhoPetEnum) comboTamanho.getSelectedItem());
            
            PetDAO petDAO = new PetDAO();
            boolean sucesso = petDAO.atualizarPet(pet);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Dados do pet atualizados com sucesso!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addLabelAndField(String labelText, int labelY, JTextField textField, int fieldY) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, labelY, 200, 20);
        add(label);
        
        textField.setBounds(50, fieldY, 380, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(textField);
    }
}