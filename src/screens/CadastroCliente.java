package screens;

import java.awt.Font;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import dao.TutorDAO;
import entities.Tutor;
import entities.Usuario;

public class CadastroCliente extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField txtNome = new JTextField();
    private JFormattedTextField txtCpf;
    private JFormattedTextField txtTelefone;
    private JFormattedTextField txtCep;
    private JTextField txtEmail = new JTextField();
    private JTextField txtSenha = new JTextField();

    public CadastroCliente() {
        setTitle("Cadastro de Clientes");
        setSize(500, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        setLayout(null);
        
        try {
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            txtCpf = new JFormattedTextField(mascaraCpf);

            MaskFormatter mascaraTelefone = new MaskFormatter("(##) #####-####");
            mascaraTelefone.setPlaceholderCharacter('_');
            txtTelefone = new JFormattedTextField(mascaraTelefone);
            
            MaskFormatter mascaraCep = new MaskFormatter("#####-###");
            mascaraCep.setPlaceholderCharacter('_');
            txtCep = new JFormattedTextField(mascaraCep);

        } catch (ParseException e) {
            e.printStackTrace();
            txtCpf = new JFormattedTextField();
            txtTelefone = new JFormattedTextField();
            txtCep = new JFormattedTextField();
        }

        addLabelAndField("Nome Completo:", 30, txtNome, 50);
        addLabelAndField("CPF:", 90, txtCpf, 110);
        addLabelAndField("Telefone:", 150, txtTelefone, 170);
        addLabelAndField("E-mail (Login):", 210, txtEmail, 230);
        addLabelAndField("Senha de Acesso:", 270, txtSenha, 290);
        addLabelAndField("CEP:", 330, txtCep, 350);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(180, 410, 120, 40);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnSalvar);
        
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText();
            String cpf = txtCpf.getText();
            String telefone = txtTelefone.getText();
            String cep = txtCep.getText();
            String email = txtEmail.getText();
            String senha = txtSenha.getText();

            Usuario novoUsuario = new Usuario();
            novoUsuario.setEmail(email);
            novoUsuario.setSenha(senha);
            
            Tutor novoTutor = new Tutor();
            novoTutor.setNome(nome);
            novoTutor.setCpf(cpf);
            novoTutor.setTelefone(telefone);
            novoTutor.setCep(cep);

            TutorDAO tutorDAO = new TutorDAO();
            boolean sucesso = tutorDAO.cadastrarNovoTutor(novoTutor, novoUsuario);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente. Verifique os dados e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
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