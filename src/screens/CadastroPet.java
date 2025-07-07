package screens;

import java.awt.Font;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import dao.PetDAO;
import dao.TutorDAO;
import entities.Pet;
import entities.Tutor;
import enums.TamanhoPetEnum;

public class CadastroPet extends JFrame {
    private static final long serialVersionUID = 1L;

    private JComboBox<Tutor> comboTutores;
    private JComboBox<TamanhoPetEnum> comboTamanho;
    private JTextField txtNome = new JTextField();
    private JTextField txtRaca = new JTextField();
    private JTextField txtIdade = new JTextField();
    private JTextField txtPeso = new JTextField();
    private JTextArea txtObservacoes = new JTextArea();

    public CadastroPet() {
        setTitle("Cadastro de Pets");
        setSize(500, 680);
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
        List<Tutor> tutores = tutorDAO.listarTutoresComNomeECPF();
        for (Tutor tutor : tutores) {
            comboTutores.addItem(tutor);
        }

        addLabelAndField("Nome:", 90, txtNome, 110);
        addLabelAndField("Raça:", 150, txtRaca, 170);
        addLabelAndField("Idade (anos):", 210, txtIdade, 230);
        addLabelAndField("Peso (kg):", 270, txtPeso, 290);
        
        JLabel lblTamanho = new JLabel("Tamanho:");
        lblTamanho.setBounds(50, 330, 150, 20);
        add(lblTamanho);
        
        comboTamanho = new JComboBox<>(TamanhoPetEnum.values());
        comboTamanho.setBounds(50, 350, 380, 30);
        add(comboTamanho);
        
        JLabel lblObservacoes = new JLabel("Observações:");
        lblObservacoes.setBounds(50, 390, 150, 20);
        add(lblObservacoes);
        
        txtObservacoes.setFont(new Font("Arial", Font.PLAIN, 14));
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        
        JScrollPane scrollObservacoes = new JScrollPane(txtObservacoes);
        scrollObservacoes.setBounds(50, 410, 380, 100);
        add(scrollObservacoes);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(180, 540, 120, 40);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnSalvar);
        
        btnSalvar.addActionListener(e -> {
            try {
            	Tutor tutorSelecionado = (Tutor) comboTutores.getSelectedItem();
            	String nome = txtNome.getText();
            	String raca = txtRaca.getText();
            	int idade = Integer.parseInt(txtIdade.getText());
            	BigDecimal peso = new BigDecimal(txtPeso.getText());
            	
            	TamanhoPetEnum tamanhoSelecionado = (TamanhoPetEnum) comboTamanho.getSelectedItem();
            	
            	String observacoes = txtObservacoes.getText();
            	
            	Pet novoPet = new Pet();
            	novoPet.setTutor(tutorSelecionado);
            	novoPet.setNome(nome);
            	novoPet.setRaca(raca);
            	novoPet.setIdade(idade);
            	novoPet.setPeso(peso);
            	novoPet.setTamanho(tamanhoSelecionado); 
            	novoPet.setObs(observacoes);
            	novoPet.setOcorrencias("");
            	
            	PetDAO petDAO = new PetDAO();
            	boolean sucesso = petDAO.cadastrarPet(novoPet);
            	
            	if (sucesso) {
            		JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
            		this.dispose();
            	} else {
            		JOptionPane.showMessageDialog(this, "Erro ao cadastrar pet.", "Erro", JOptionPane.ERROR_MESSAGE);
            	}
            	
            } catch (Exception ex) {
            	JOptionPane.showMessageDialog(this, "Erro ao cadastrar pet: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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