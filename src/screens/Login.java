package screens;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class Login extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField usuario = new JTextField();
    private JPasswordField senha = new JPasswordField();
    private JButton botaoEntrar = new JButton();

    public Login() {
        super("Login - PetShop");
        setLayout(null);
        setResizable(false);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel labelUsuario = new JLabel("Usuário:");
        labelUsuario.setBounds(50, 40, 100, 20);
        add(labelUsuario);

        usuario.setBounds(50, 60, 280, 25);
        add(usuario);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(50, 90, 100, 20);
        add(labelSenha);

        senha.setBounds(50, 110, 280, 25);
        add(senha);

        botaoEntrar.setText("Entrar");
        botaoEntrar.setBounds(140, 160, 100, 30);
        add(botaoEntrar);

        botaoEntrar.addActionListener(e -> {
            String user = usuario.getText();
            String pass = new String(senha.getPassword());

            System.out.println("Tentativa de login com:");
            System.out.println("Usuário: " + user);
            System.out.println("Senha: " + pass);

        });
        setVisible(true);
    }
}
