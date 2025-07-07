package screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import dao.UsuarioDAO;
import entities.Usuario;

public class Login extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField usuario = new JTextField();
    private JPasswordField senha = new JPasswordField();
    private JButton botaoEntrar = new JButton();

    public Login() {
        super("Login - Central Pet");
        
        setLayout(null);
        setResizable(false);
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240)); 

        ImageIcon logoOriginal = new ImageIcon(getClass().getResource("/assets/logo.png"));
        Image imagemRedimensionada = logoOriginal.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon logoRedimensionada = new ImageIcon(imagemRedimensionada);
        
        JLabel labelLogo = new JLabel(logoRedimensionada);
        labelLogo.setBounds(100, 20, 200, 200); 
        add(labelLogo);

        JLabel labelUsuario = new JLabel("Usuário (E-mail):");
        labelUsuario.setBounds(50, 240, 100, 20);
        add(labelUsuario);

        usuario.setBounds(50, 260, 280, 30);
        usuario.setFont(new Font("Arial", Font.PLAIN, 14));
        add(usuario);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(50, 300, 100, 20);
        add(labelSenha);

        senha.setBounds(50, 320, 280, 30);
        add(senha);

        botaoEntrar.setText("Entrar");
        botaoEntrar.setBounds(140, 380, 120, 40);
        botaoEntrar.setBackground(new Color(0, 136, 240));
        botaoEntrar.setForeground(Color.WHITE);
        botaoEntrar.setFont(new Font("Arial", Font.BOLD, 16));
        add(botaoEntrar);

        botaoEntrar.addActionListener(e -> {
            String email = usuario.getText();
            String pass = new String(senha.getPassword());

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuarioAutenticado = usuarioDAO.autenticarUsuario(email, pass);
            
            if (usuarioAutenticado != null) {
                JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
                this.dispose(); 

                String perfilDescricao = usuarioAutenticado.getPerfil().getDescricao();
                
                if (perfilDescricao.equalsIgnoreCase("Administrador")) {
                    new HomeAdmin();
                } else {
                    new HomeTutor();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        setVisible(true);
    }
}