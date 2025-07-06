package screens;

import java.awt.Font;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.PetDAO;
import dao.TutorDAO;
import entities.Pet;
import entities.Tutor;

public class CadastroPet extends JFrame {
    private static final long serialVersionUID = 1L;

    private JComboBox<Tutor> comboTutores;
    private JComboBox<String> comboTamanho;
    private JTextField txtRaca = new JTextField();
    private JTextField txtIdade = new JTextField();
    private JTextField txtPeso = new JTextField();

    public CadastroPet() {
        setTitle("Cadastro de Pets");
        setSize(500, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        
        JLabel lblTutor = new JLabel("Tutor Responsável:");
        lblTutor.setBounds(50, 30, 150, 20);
        add(lblTutor);

        comboTutores = new JComboBox<>();
        comboTutores.setBounds(50, 50, 380, 30);
        add(comboTutores);
        
        TutorDAO tutorDAO = new TutorDAO();
        List<Tutor> tutores = tutorDAO.listarTutores();
        for (Tutor tutor : tutores) {
            comboTutores.addItem(tutor);
        }

        addLabelAndField("Raça:", 90, txtRaca, 110);
        addLabelAndField("Idade (anos):", 150, txtIdade, 170);
        addLabelAndField("Peso (kg):", 210, txtPeso, 230);
        
        JLabel lblTamanho = new JLabel("Tamanho:");
        lblTamanho.setBounds(50, 270, 150, 20);
        add(lblTamanho);
        
        comboTamanho = new JComboBox<>();
        comboTamanho.setBounds(50, 290, 380, 30);
        comboTamanho.addItem("1 - Muito Pequeno");
        comboTamanho.addItem("2 - Pequeno");
        comboTamanho.addItem("3 - Médio");
        comboTamanho.addItem("4 - Grande");
        add(comboTamanho);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(180, 380, 120, 40);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnSalvar);
        
        btnSalvar.addActionListener(e -> {
            Tutor tutorSelecionado = (Tutor) comboTutores.getSelectedItem();
            String raca = txtRaca.getText();
            int idade = Integer.parseInt(txtIdade.getText());
            BigDecimal peso = new BigDecimal(txtPeso.getText());
            
            String itemTamanhoSelecionado = (String) comboTamanho.getSelectedItem();
            int tamanho = Integer.parseInt(itemTamanhoSelecionado.split(" ")[0]);

            Pet novoPet = new Pet();
            novoPet.setTutor(tutorSelecionado);
            novoPet.setRaca(raca);
            novoPet.setIdade(idade);
            novoPet.setPeso(peso);
            novoPet.setTamanho(tamanho); 
            novoPet.setObs("");
            novoPet.setOcorrencias("");
            
            PetDAO petDAO = new PetDAO();
            boolean sucesso = petDAO.cadastrarPet(novoPet);
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar pet.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
    
    private void addLabelAndField(String labelText, int labelY, JTextField textField, int fieldY) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, labelY, 150, 20);
        add(label);
        
        textField.setBounds(50, fieldY, 380, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(textField);
    }
}