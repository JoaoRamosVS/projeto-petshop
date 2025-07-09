package screens.tutores;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import dao.TutorDAO;
import entities.Tutor;
import entities.Usuario;
import screens.admins.EdicaoTutor;

public class HomeTutor extends JFrame {
    private static final long serialVersionUID = 1L;
    private Usuario usuarioLogado;

    public HomeTutor(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("Área do Cliente - Central Pet");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        JLabel lblTitulo = new JLabel("Bem-vindo(a) à sua Central Pet!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setBounds(0, 50, 800, 35);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);

        JButton btnMeusDados = new JButton("Meus Dados");
        configurarBotao(btnMeusDados, 125, 150, 250, 100);
        btnMeusDados.addActionListener(e -> {
            TutorDAO tutorDAO = new TutorDAO();
            Tutor tutor = tutorDAO.buscarTutorPorUsuarioId(usuarioLogado.getId());
            if (tutor != null) {
                new EdicaoTutor(tutor.getId());
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível encontrar os dados do tutor.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(btnMeusDados);

        JButton btnMeusPets = new JButton("Meus Pets");
        configurarBotao(btnMeusPets, 425, 150, 250, 100);
        btnMeusPets.addActionListener(e -> {
            TutorDAO tutorDAO = new TutorDAO();
            Tutor tutor = tutorDAO.buscarTutorPorUsuarioId(usuarioLogado.getId());
            if (tutor != null) {
                new GerenciarMeusPets(tutor);
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível encontrar os dados do tutor.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(btnMeusPets);

        JButton btnAgendarServico = new JButton("Agendar Serviço");
        configurarBotao(btnAgendarServico, 125, 300, 250, 100);
        btnAgendarServico.addActionListener(e -> {
            new AgendamentoServico(usuarioLogado);
        });
        add(btnAgendarServico);
        
        JButton btnMeusAgendamentos = new JButton("Meus Agendamentos");
        configurarBotao(btnMeusAgendamentos, 425, 300, 250, 100);
        btnMeusAgendamentos.addActionListener(e -> {
            new MeusAgendamentos(usuarioLogado);
        });
        add(btnMeusAgendamentos);
        
        setVisible(true);
    }
    
    private void configurarBotao(JButton botao, int x, int y, int largura, int altura) {
        botao.setBounds(x, y, largura, altura);
        botao.setFont(new Font("Arial", Font.BOLD, 18));
        botao.setBackground(new Color(0, 140, 255));
        botao.setForeground(Color.WHITE);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}