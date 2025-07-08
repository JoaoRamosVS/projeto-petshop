package screens;

import java.awt.Font;
import java.math.BigDecimal;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import dao.FuncionarioDAO;
import entities.Funcionario;

public class EdicaoFuncionario extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField txtNome = new JTextField();
    private JTextField txtCpf = new JTextField();
    private JTextField txtEmail = new JTextField();
    private JTextField txtTelefone = new JTextField();
    private JTextField txtCep = new JTextField();
    private JTextField txtEndereco = new JTextField();
    private JTextField txtBairro = new JTextField();
    private JTextField txtCidade = new JTextField();
    private JTextField txtUf = new JTextField();
    private JTextField txtCargo = new JTextField();
    private JTextField txtSalario = new JTextField();

    private Funcionario funcionarioAtual;

    public EdicaoFuncionario(int funcionarioId) {
        setTitle("Edição de Funcionário");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        this.funcionarioAtual = funcionarioDAO.buscarFuncionarioPorId(funcionarioId);

        if (funcionarioAtual == null) {
            JOptionPane.showMessageDialog(this, "Funcionário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        addLabelAndField("Nome Completo:", 30, txtNome, 50, 50, 320, false);
        addLabelAndField("CPF:", 90, txtCpf, 110, 50, 320, false);
        addLabelAndField("E-mail (Login):", 150, txtEmail, 170, 50, 320, false);
        addLabelAndField("Cargo:", 210, txtCargo, 230, 50, 320, false);
        addLabelAndField("Salário (R$):", 270, txtSalario, 290, 50, 150, true);

        addLabelAndField("Telefone:", 30, txtTelefone, 50, 420, 320, true);
        addLabelAndField("CEP:", 90, txtCep, 110, 420, 320, true);
        addLabelAndField("Endereço:", 150, txtEndereco, 170, 420, 320, true);
        addLabelAndField("Bairro:", 210, txtBairro, 230, 420, 320, true);
        addLabelAndField("Cidade:", 270, txtCidade, 290, 420, 320, true);
        addLabelAndField("UF:", 330, txtUf, 350, 420, 100, true);

        preencherCampos();

        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setBounds(300, 480, 200, 40);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            salvarAlteracoesFuncionario();
        });

        setVisible(true);
    }

    private void preencherCampos() {
        txtNome.setText(funcionarioAtual.getNome());
        txtCpf.setText(funcionarioAtual.getCpf());
        txtEmail.setText(funcionarioAtual.getUsuario().getEmail());
        txtTelefone.setText(funcionarioAtual.getTelefone());
        txtCep.setText(funcionarioAtual.getCep());
        txtEndereco.setText(funcionarioAtual.getEndereco());
        txtBairro.setText(funcionarioAtual.getBairro());
        txtCidade.setText(funcionarioAtual.getCidade());
        txtUf.setText(funcionarioAtual.getUf());
        txtCargo.setText(funcionarioAtual.getCargo());
        txtSalario.setText(funcionarioAtual.getSalario().toString());
    }

    private void salvarAlteracoesFuncionario() {
        try {
            funcionarioAtual.setTelefone(txtTelefone.getText());
            funcionarioAtual.setCep(txtCep.getText());
            funcionarioAtual.setEndereco(txtEndereco.getText());
            funcionarioAtual.setBairro(txtBairro.getText());
            funcionarioAtual.setCidade(txtCidade.getText());
            funcionarioAtual.setUf(txtUf.getText());
            funcionarioAtual.setSalario(new BigDecimal(txtSalario.getText()));

            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            boolean sucesso = funcionarioDAO.atualizarFuncionario(funcionarioAtual);

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
                this.dispose();
                new GerenciarFuncionario(); // Atualiza a lista
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar funcionário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Salário inválido. Por favor, insira um número.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addLabelAndField(String labelText, int labelY, JTextField textField, int fieldY, int x, int width,
            boolean editable) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, labelY, 150, 20);
        add(label);

        textField.setBounds(x, fieldY, width, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setEditable(editable);
        if (!editable) {
            textField.setBackground(new java.awt.Color(230, 230, 230));
        }
        add(textField);
    }
}