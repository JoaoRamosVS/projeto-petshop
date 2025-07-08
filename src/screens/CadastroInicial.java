package screens;

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

import dao.TutorDAO;
import entities.Pet;
import entities.Tutor;
import entities.Usuario;
import enums.TamanhoPetEnum;

public class CadastroInicial extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField txtNomeTutor = new JTextField();
    private JFormattedTextField txtCpf;
    private JFormattedTextField txtTelefone;
    private JFormattedTextField txtCep;
    private JTextField txtEndereco = new JTextField();
    private JTextField txtBairro = new JTextField();
    private JTextField txtCidade = new JTextField();
    private JTextField txtUf = new JTextField();
    
    private JTextField txtEmail = new JTextField();
    private JTextField txtSenha = new JTextField();
    
    private JTextField txtNomePet = new JTextField();
    private JTextField txtRacaPet = new JTextField();
    private JFormattedTextField txtDtNascPet;
    private JTextField txtPesoPet = new JTextField();
    private JComboBox<TamanhoPetEnum> comboTamanhoPet;

    public CadastroInicial() {
        setTitle("Crie sua Conta e Cadastre seu Pet");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        setLayout(null);
        
        try {
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            txtCpf = new JFormattedTextField(mascaraCpf);
            MaskFormatter mascaraTelefone = new MaskFormatter("(##) #####-####");
            txtTelefone = new JFormattedTextField(mascaraTelefone);
            MaskFormatter mascaraCep = new MaskFormatter("#####-###");
            txtCep = new JFormattedTextField(mascaraCep);
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            txtDtNascPet = new JFormattedTextField(mascaraData);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JLabel lblDadosTutor = new JLabel("Seus Dados");
        lblDadosTutor.setFont(new Font("Arial", Font.BOLD, 18));
        lblDadosTutor.setBounds(50, 20, 200, 25);
        add(lblDadosTutor);
        
        addLabelAndField("Nome Completo:", 50, txtNomeTutor, 70, 50, 380);
        addLabelAndField("CPF:", 110, txtCpf, 130, 50, 380);
        addLabelAndField("Telefone:", 170, txtTelefone, 190, 50, 380);
        addLabelAndField("CEP:", 230, txtCep, 250, 50, 150);
        addLabelAndField("Endereço (Rua e Nº):", 290, txtEndereco, 310, 50, 380);
        addLabelAndField("Bairro:", 350, txtBairro, 370, 50, 200);
        addLabelAndField("Cidade:", 350, txtCidade, 370, 270, 160);
        addLabelAndField("UF:", 410, txtUf, 430, 50, 80);

        JLabel lblDadosPet = new JLabel("Cadastre seu Pet");
        lblDadosPet.setFont(new Font("Arial", Font.BOLD, 18));
        lblDadosPet.setBounds(480, 20, 300, 25);
        add(lblDadosPet);

        addLabelAndField("Nome do Pet:", 50, txtNomePet, 70, 480, 350);
        addLabelAndField("Raça:", 110, txtRacaPet, 130, 480, 350);
        addLabelAndField("Data de Nascimento:", 170, txtDtNascPet, 190, 480, 200);
        addLabelAndField("Peso (kg):", 170, txtPesoPet, 190, 690, 140);

        JLabel lblTamanho = new JLabel("Tamanho:");
        lblTamanho.setBounds(480, 230, 150, 20);
        add(lblTamanho);

        comboTamanhoPet = new JComboBox<>(TamanhoPetEnum.values());
        comboTamanhoPet.setBounds(480, 250, 350, 30);
        add(comboTamanhoPet);
        
        JLabel lblDadosLogin = new JLabel("Dados de Acesso");
        lblDadosLogin.setFont(new Font("Arial", Font.BOLD, 18));
        lblDadosLogin.setBounds(480, 320, 200, 25);
        add(lblDadosLogin);
        
        addLabelAndField("E-mail:", 350, txtEmail, 370, 480, 350);
        addLabelAndField("Senha:", 410, txtSenha, 430, 480, 350);
        
        JButton btnSalvar = new JButton("Criar Conta");
        btnSalvar.setBounds(380, 500, 120, 40);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnSalvar);
        
        btnSalvar.addActionListener(e -> {
            try {
                Usuario novoUsuario = new Usuario();
                novoUsuario.setEmail(txtEmail.getText());
                novoUsuario.setSenha(txtSenha.getText());
                
                Tutor novoTutor = new Tutor();
                novoTutor.setNome(txtNomeTutor.getText());
                novoTutor.setCpf(txtCpf.getText());
                novoTutor.setTelefone(txtTelefone.getText());
                novoTutor.setCep(txtCep.getText());
                novoTutor.setEndereco(txtEndereco.getText());
                novoTutor.setBairro(txtBairro.getText());
                novoTutor.setCidade(txtCidade.getText());
                novoTutor.setUf(txtUf.getText());

                Pet novoPet = new Pet();
                novoPet.setNome(txtNomePet.getText());
                novoPet.setRaca(txtRacaPet.getText());
                novoPet.setTamanho((TamanhoPetEnum) comboTamanhoPet.getSelectedItem());
                
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                novoPet.setDtNascimento(LocalDate.parse(txtDtNascPet.getText(), formatador));
                novoPet.setPeso(new BigDecimal(txtPesoPet.getText()));

                TutorDAO tutorDAO = new TutorDAO();
                boolean sucesso = tutorDAO.cadastrarTutorComPet(novoTutor, novoUsuario, novoPet);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao criar conta.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private void addLabelAndField(String labelText, int labelY, JTextField textField, int fieldY, int x, int width) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, labelY, 250, 20);
        add(label);
        
        textField.setBounds(x, fieldY, width, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(textField);
    }
}