package screens.admins;

import java.awt.Font;
import java.math.BigDecimal;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import dao.FuncionarioDAO;
import entities.Funcionario;
import entities.Usuario;

public class CadastroFuncionario extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txtNome = new JTextField();
    private JFormattedTextField txtCpf;
    private JFormattedTextField txtTelefone;
    private JFormattedTextField txtCep;
    private JTextField txtEmail = new JTextField();
    private JPasswordField txtSenha = new JPasswordField();
    private JComboBox<String> comboCargo;
    private JTextField txtSalario = new JTextField();
    
    public CadastroFuncionario() {
        setTitle("Cadastro de Funcionários");
        setSize(500, 640);
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
        
        JLabel lblCargo = new JLabel("Cargo:");
        lblCargo.setBounds(50, 390, 150, 20);
        add(lblCargo);

        comboCargo = new JComboBox<>(new String[]{"Tosador", "Veterinário", "Atendente"});
        comboCargo.setBounds(50, 410, 380, 30);
        add(comboCargo);
        
        addLabelAndField("Salário (R$):", 450, txtSalario, 470);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(180, 530, 120, 40);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnSalvar);
        
        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                String telefone = txtTelefone.getText();
                String cep = txtCep.getText();
                String email = txtEmail.getText();
                String senha = new String(txtSenha.getPassword());
                String cargo = (String) comboCargo.getSelectedItem();
                BigDecimal salario = new BigDecimal(txtSalario.getText());

                Usuario novoUsuario = new Usuario();
                novoUsuario.setEmail(email);
                novoUsuario.setSenha(senha);
                
                Funcionario novoFuncionario = new Funcionario();
                novoFuncionario.setNome(nome);
                novoFuncionario.setCpf(cpf);
                novoFuncionario.setTelefone(telefone);
                novoFuncionario.setCep(cep);
                novoFuncionario.setCargo(cargo);
                novoFuncionario.setSalario(salario);

                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                boolean sucesso = funcionarioDAO.cadastrarNovoFuncionario(novoFuncionario, novoUsuario);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário. Verifique os dados e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                 JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário: Salário inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                 JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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