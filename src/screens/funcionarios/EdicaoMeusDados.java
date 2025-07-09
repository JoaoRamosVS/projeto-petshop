package screens.funcionarios;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import dao.FuncionarioDAO;
import dao.UsuarioDAO;
import entities.Funcionario;

public class EdicaoMeusDados extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtCargo;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtCep;
    private JTextField txtEndereco;
    private JTextField txtBairro;
    private JTextField txtCidade;
    private JTextField txtUf;
    private JPasswordField txtNovaSenha;
    private JPasswordField txtConfirmarNovaSenha;

    private Funcionario funcionarioAtual;

    public EdicaoMeusDados(Funcionario funcionario) {
        this.funcionarioAtual = funcionario;

        setTitle("Meus Dados Pessoais - " + funcionario.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        int y = 50;
        addInfoLabel("Nome Completo:", funcionario.getNome(), y);
        y += 60;
        addInfoLabel("CPF:", funcionario.getCpf(), y);
        y += 60;
        addInfoLabel("Cargo:", funcionario.getCargo(), y);
        y += 60;
        
        txtEmail = new JTextField(funcionario.getUsuario().getEmail());
        addEditableField("E-mail (Login):", txtEmail, y, 50);
        y += 60;

        txtNovaSenha = new JPasswordField();
        addEditableField("Nova Senha (deixe em branco para não alterar):", txtNovaSenha, y, 50);
        y += 60;
        
        txtConfirmarNovaSenha = new JPasswordField();
        addEditableField("Confirmar Nova Senha:", txtConfirmarNovaSenha, y, 50);

        y = 50;
        txtTelefone = new JTextField(funcionario.getTelefone());
        addEditableField("Telefone:", txtTelefone, y, 420);
        y += 60;
        txtCep = new JTextField(funcionario.getCep());
        addEditableField("CEP:", txtCep, y, 420);
        y += 60;
        txtEndereco = new JTextField(funcionario.getEndereco());
        addEditableField("Endereço:", txtEndereco, y, 420);
        y += 60;
        txtBairro = new JTextField(funcionario.getBairro());
        addEditableField("Bairro:", txtBairro, y, 420);
        y += 60;
        txtCidade = new JTextField(funcionario.getCidade());
        addEditableField("Cidade:", txtCidade, y, 420);
        y += 60;
        txtUf = new JTextField(funcionario.getUf());
        addEditableField("UF:", txtUf, y, 420, 100);

        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setBounds(300, 500, 200, 40);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnSalvar);

        btnSalvar.addActionListener(e -> salvarAlteracoes());

        setVisible(true);
    }

    private void salvarAlteracoes() {
        String novaSenha = new String(txtNovaSenha.getPassword());
        String confirmarSenha = new String(txtConfirmarNovaSenha.getPassword());

        if (!novaSenha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem.", "Erro na Senha", JOptionPane.ERROR_MESSAGE);
            return;
        }

        funcionarioAtual.setTelefone(txtTelefone.getText());
        funcionarioAtual.setCep(txtCep.getText());
        funcionarioAtual.setEndereco(txtEndereco.getText());
        funcionarioAtual.setBairro(txtBairro.getText());
        funcionarioAtual.setCidade(txtCidade.getText());
        funcionarioAtual.setUf(txtUf.getText());
        
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        boolean sucessoFuncionario = funcionarioDAO.atualizarFuncionario(funcionarioAtual);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        String novoEmail = txtEmail.getText();
        boolean sucessoCredenciais = usuarioDAO.atualizarCredenciais(funcionarioAtual.getUsuario().getId(), novoEmail, novaSenha);

        if (sucessoFuncionario && sucessoCredenciais) {
            JOptionPane.showMessageDialog(this, "Dados atualizados com sucesso!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao atualizar os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addInfoLabel(String labelText, String infoText, int y) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, y, 150, 20);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        add(label);

        JLabel info = new JLabel(infoText);
        info.setBounds(50, y + 20, 320, 30);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        add(info);
    }

    private void addEditableField(String labelText, JTextField textField, int y, int x) {
        addEditableField(labelText, textField, y, x, 320);
    }

    private void addEditableField(String labelText, JTextField textField, int y, int x, int width) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, y, 400, 20);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        add(label);

        textField.setBounds(x, y + 20, width, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        add(textField);
    }
}