package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import dao.UsuarioDAO;
import entities.Usuario;

public class GerenciarUsuario extends JFrame {
    private static final long serialVersionUID = 1L;

    public GerenciarUsuario() {
        setTitle("Gerenciar Usuários");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JFrame frameAtual = this;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();

        if (usuarios.isEmpty()) {
            mainPanel.add(new JLabel("Nenhum usuário cadastrado."));
        } else {
            for (Usuario usuario : usuarios) {
                JPanel usuarioPanel = new JPanel(new BorderLayout(10, 10));
                usuarioPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1, true),
                    new EmptyBorder(10, 10, 10, 10)
                ));

                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

                JLabel lblEmail = new JLabel("Email: " + usuario.getEmail());
                lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
                infoPanel.add(lblEmail);
                infoPanel.add(new JLabel("Perfil: " + usuario.getPerfil().getDescricao()));
                
                String status = "Ativo";
                Color corStatus = new Color(0, 128, 0); // verde para Ativo
                if (usuario.getAtivo().equalsIgnoreCase("N")) {
                    status = "Inativo";
                    corStatus = Color.RED;
                }
                JLabel lblStatus = new JLabel("Status: " + status);
                lblStatus.setForeground(corStatus);
                infoPanel.add(lblStatus);
                
                JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                
                String textoBotao = usuario.getAtivo().equalsIgnoreCase("S") ? "Inativar" : "Reativar";
                JButton btnMudarStatus = new JButton(textoBotao);
                
                btnMudarStatus.addActionListener(e -> {
                    String acao = usuario.getAtivo().equalsIgnoreCase("S") ? "inativar" : "reativar";
                    int confirma = JOptionPane.showConfirmDialog(frameAtual, 
                            "Tem certeza que deseja " + acao + " o usuário '" + usuario.getEmail() + "'?",
                            "Confirmar Ação",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                            
                    if (confirma == JOptionPane.YES_OPTION) {
                        boolean sucesso;
                        if (usuario.getAtivo().equalsIgnoreCase("S")) {
                            sucesso = usuarioDAO.inativarUsuario(usuario.getEmail());
                        } else {
                            sucesso = false;
                            JOptionPane.showMessageDialog(frameAtual, "A funcionalidade 'Reativar' ainda não foi implementada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                        if (sucesso) {
                            JOptionPane.showMessageDialog(frameAtual, "Usuário " + (acao.equals("inativar") ? "inativado" : "reativado") + " com sucesso!");
                            frameAtual.dispose();
                            new GerenciarUsuario(); 
                        } else if (!acao.equals("reativar")) {
                            JOptionPane.showMessageDialog(frameAtual, "Ocorreu um erro ao " + acao + " o usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                
                actionPanel.add(btnMudarStatus);

                usuarioPanel.add(infoPanel, BorderLayout.CENTER);
                usuarioPanel.add(actionPanel, BorderLayout.SOUTH);
                
                mainPanel.add(usuarioPanel);
                mainPanel.add(new JLabel(" ")); 
            }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
        
        revalidate();
        repaint();
        
        setVisible(true);
    }
}