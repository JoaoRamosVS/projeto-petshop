package screens;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.FuncionarioDAO;
import dao.UsuarioDAO;
import entities.Funcionario;
import entities.Usuario;
import screens.admins.HomeAdmin;
import screens.funcionarios.HomeFuncionario;
import screens.tutores.HomeTutor;

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
        
        JLabel lblCadastreSe = new JLabel("Não tem uma conta? Cadastre-se");
        lblCadastreSe.setBounds(90, 450, 220, 20);
        lblCadastreSe.setForeground(new Color(0, 102, 204));
        lblCadastreSe.setFont(new Font("Arial", Font.BOLD, 12));
        lblCadastreSe.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(lblCadastreSe);

        lblCadastreSe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CadastroInicial();
            }
        });

        botaoEntrar.addActionListener(e -> {
            String email = usuario.getText();
            String pass = new String(senha.getPassword());

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuarioAutenticado = usuarioDAO.autenticarUsuario(email, pass);
            
            if (usuarioAutenticado != null) {
                this.dispose(); 
                String perfil = usuarioAutenticado.getPerfil().getDescricao();
                
                switch (perfil.toLowerCase()) {
                    case "administrador":
                        JOptionPane.showMessageDialog(this, "Login de Administrador realizado com sucesso!");
                        new HomeAdmin();
                        break;
                    case "tutor":
                        JOptionPane.showMessageDialog(this, "Login de Tutor realizado com sucesso!");
                        new HomeTutor(usuarioAutenticado);
                        break;
                    case "funcionario":
                        FuncionarioDAO funcDAO = new FuncionarioDAO();
                        Funcionario funcLogado = funcDAO.buscarFuncionarioPorUsuarioId(usuarioAutenticado.getId());
                        
                        if (funcLogado != null) {
                            funcLogado.setUsuario(usuarioAutenticado);
                            new HomeFuncionario(funcLogado);
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro: dados de funcionário não encontrados para este usuário.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                            new Login();
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Perfil de usuário desconhecido.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                        new Login();
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        setVisible(true);
    }
}