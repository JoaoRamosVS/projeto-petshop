package screens.funcionarios;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import entities.Funcionario;
import screens.Login;

public class HomeFuncionario extends JFrame {
    private static final long serialVersionUID = 1L;
    private Funcionario funcionarioLogado;

    public HomeFuncionario(Funcionario funcionario) {
        this.funcionarioLogado = funcionario;

        setTitle("Área do Funcionário - " + funcionario.getCargo());
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        JLabel lblTitulo = new JLabel("Olá, " + funcionario.getNome().split(" ")[0] + "!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setBounds(0, 50, 800, 35);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);

        JButton btnMinhaAgenda = new JButton("Minha Agenda");
        configurarBotao(btnMinhaAgenda, 125, 200, 250, 150);
        btnMinhaAgenda.addActionListener(e -> {
            new MinhaAgenda(funcionarioLogado);
        });
        add(btnMinhaAgenda);

        JButton btnMeusDados = new JButton("Meus Dados");
        configurarBotao(btnMeusDados, 425, 200, 250, 150);
        btnMeusDados.addActionListener(e -> {
            new EdicaoMeusDados(funcionarioLogado);
        });
        add(btnMeusDados);

        JButton btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Arial", Font.BOLD, 14));
        btnSair.setBackground(new Color(220, 53, 69));
        btnSair.setForeground(Color.WHITE);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.setBounds(350, 450, 100, 40);
        btnSair.addActionListener(e -> {
            dispose();
            new Login();
        });
        add(btnSair);

        setVisible(true);
    }

    private void configurarBotao(JButton botao, int x, int y, int largura, int altura) {
        botao.setBounds(x, y, largura, altura);
        botao.setFont(new Font("Arial", Font.BOLD, 18));
        botao.setBackground(new Color(20, 160, 100));
        botao.setForeground(Color.WHITE);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}