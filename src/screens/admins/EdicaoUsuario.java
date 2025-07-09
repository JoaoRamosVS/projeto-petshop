package screens.admins;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.UsuarioDAO;
import entities.Usuario;

public class EdicaoUsuario extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField txtEmail = new JTextField();
    private JPasswordField txtSenha = new JPasswordField();
    private JComboBox<String> comboStatus;

    private Usuario usuarioAtual;

    public EdicaoUsuario(int usuarioId) {
        setTitle("Edição de Usuário");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        this.usuarioAtual = usuarioDAO.buscarUsuarioPorId(usuarioId);

        if (usuarioAtual == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }

        addLabelAndField("E-mail (Login):", 30, txtEmail, 50, 50, 320, true);
        addLabelAndField("Nova Senha (deixe em branco para não alterar):", 90, txtSenha, 110, 50, 320, true);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(50, 150, 150, 20);
        add(lblStatus);

        comboStatus = new JComboBox<>(new String[]{"Ativo", "Inativo"});
        comboStatus.setBounds(50, 170, 320, 30);
        add(comboStatus);

        preencherCampos();

        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setBounds(125, 280, 200, 40);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
        add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            salvarAlteracoesUsuario();
        });

        setVisible(true);
    }

    private void preencherCampos() {
        txtEmail.setText(usuarioAtual.getEmail());
        if (usuarioAtual.getAtivo().equalsIgnoreCase("S")) {
            comboStatus.setSelectedItem("Ativo");
        } else {
            comboStatus.setSelectedItem("Inativo");
        }
    }

    private void salvarAlteracoesUsuario() {
        String novoEmail = txtEmail.getText();
        String novaSenha = new String(txtSenha.getPassword());
        String statusSelecionado = (String) comboStatus.getSelectedItem();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (!novoEmail.equalsIgnoreCase(usuarioAtual.getEmail()) && usuarioDAO.verificarSeEmailJaCadastrado(novoEmail)) {
            JOptionPane.showMessageDialog(this, "O e-mail informado já está em uso por outro usuário.", "E-mail Duplicado", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        usuarioAtual.setEmail(novoEmail);
        usuarioAtual.setAtivo(statusSelecionado.equals("Ativo") ? "S" : "N");
        
        if(!novaSenha.isBlank()) {
        	usuarioAtual.setSenha(novaSenha);
        } else {
        	usuarioAtual.setSenha(null);
        }

        boolean sucesso = usuarioDAO.atualizarUsuario(usuarioAtual);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
            this.dispose();
            new GerenciarUsuario();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addLabelAndField(String labelText, int labelY, JTextField textField, int fieldY, int x, int width,
            boolean editable) {
        JLabel label = new JLabel(labelText);
        label.setBounds(x, labelY, 320, 20);
        add(label);

        textField.setBounds(x, fieldY, width, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setEditable(editable);
        if (!editable) {
            textField.setBackground(new Color(230, 230, 230));
        }
        add(textField);
    }
}