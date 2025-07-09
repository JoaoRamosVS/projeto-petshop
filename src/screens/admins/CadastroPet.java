package screens.admins;

import java.awt.Font;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

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
    private JFormattedTextField txtDtNascimento;
    private JTextField txtPeso = new JTextField();
    private JTextArea txtObservacoes = new JTextArea();
    private Tutor tutor;

    public CadastroPet() {
        inicializarComponentes();
        
        JLabel lblTutor = new JLabel("Tutor Responsável:");
        lblTutor.setBounds(50, 30, 150, 20);
        add(lblTutor);

        comboTutores = new JComboBox<>();
        comboTutores.setBounds(50, 50, 380, 30);
        add(comboTutores);
        
        TutorDAO tutorDAO = new TutorDAO();
        List<Tutor> tutores = tutorDAO.listarTutoresComNomeECPF();
        for (Tutor t : tutores) {
            comboTutores.addItem(t);
        }
    }

    public CadastroPet(Tutor tutor) {
        this.tutor = tutor;
        inicializarComponentes();
        
        JLabel lblTutor = new JLabel("Tutor Responsável:");
        lblTutor.setBounds(50, 30, 150, 20);
        add(lblTutor);
        
        JTextField txtTutorFixo = new JTextField(tutor.getNome());
        txtTutorFixo.setBounds(50, 50, 380, 30);
        txtTutorFixo.setEditable(false);
        add(txtTutorFixo);
    }
    
    private void inicializarComponentes() {
        setTitle("Cadastro de Pet");
        setSize(500, 680);
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
        
        addLabelAndField("Nome:", 90, txtNome, 110);
        addLabelAndField("Raça:", 150, txtRaca, 170);
        addLabelAndField("Data de Nascimento (dd/mm/aaaa):", 210, txtDtNascimento, 230);
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
        
        btnSalvar.addActionListener(e -> salvarPet());
        
        setVisible(true);
    }
    
    private void salvarPet() {
        try {
            Tutor tutorSelecionado = (comboTutores != null) ? (Tutor) comboTutores.getSelectedItem() : this.tutor;

            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Pet novoPet = new Pet();
            novoPet.setTutor(tutorSelecionado);
            novoPet.setNome(txtNome.getText());
            novoPet.setRaca(txtRaca.getText());
            novoPet.setDtNascimento(LocalDate.parse(txtDtNascimento.getText(), formatador));
            novoPet.setPeso(new BigDecimal(txtPeso.getText()));
            novoPet.setTamanho((TamanhoPetEnum) comboTamanho.getSelectedItem()); 
            novoPet.setObs(txtObservacoes.getText());
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
    }
    
    private void addLabelAndField(String labelText, int labelY, JTextField textField, int fieldY) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, labelY, 250, 20);
        add(label);
        
        textField.setBounds(50, fieldY, 380, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(textField);
    }
}