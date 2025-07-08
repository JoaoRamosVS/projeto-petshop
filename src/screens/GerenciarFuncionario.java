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
import dao.FuncionarioDAO;
import dao.UsuarioDAO;
import entities.Funcionario;

public class GerenciarFuncionario extends JFrame {
    private static final long serialVersionUID = 1L;

    public GerenciarFuncionario() {
        setTitle("Gerenciar Funcionários");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JFrame frameAtual = this;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        List<Funcionario> funcionarios = funcionarioDAO.listarFuncionarios();

        if (funcionarios.isEmpty()) {
            mainPanel.add(new JLabel("Nenhum funcionário cadastrado."));
        } else {
            for (Funcionario funcionario : funcionarios) {
                JPanel funcionarioPanel = new JPanel(new BorderLayout(10, 10));
                funcionarioPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1, true),
                    new EmptyBorder(10, 10, 10, 10)
                ));

                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

                JLabel lblNome = new JLabel("Nome: " + funcionario.getNome());
                lblNome.setFont(new Font("Arial", Font.BOLD, 14));
                infoPanel.add(lblNome);
                infoPanel.add(new JLabel("CPF: " + funcionario.getCpf()));
                infoPanel.add(new JLabel("Telefone: " + funcionario.getTelefone()));
                infoPanel.add(new JLabel("Cargo: " + funcionario.getCargo()));
                infoPanel.add(new JLabel("Email: " + funcionario.getUsuario().getEmail()));
                
                JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                
                JButton btnEditar = new JButton("Editar");
                btnEditar.addActionListener(e -> {
                    new EdicaoFuncionario(funcionario.getId());
                    frameAtual.dispose(); 
                });
                
                JButton btnInativar = new JButton("Inativar");
                btnInativar.addActionListener(e -> {
                    int confirmaExclusao = JOptionPane.showConfirmDialog(frameAtual, 
                            "Tem certeza que deseja inativar o funcionário " + funcionario.getNome() + "'?\nO acesso dele(a) ao sistema será bloqueado.",
                            "Confirmar Inativação",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                    if (confirmaExclusao == JOptionPane.YES_OPTION) {
                        UsuarioDAO usuarioDAO = new UsuarioDAO();
                        boolean sucesso = usuarioDAO.inativarUsuario(funcionario.getUsuario().getEmail());
                        
                        if (sucesso) {
                            JOptionPane.showMessageDialog(frameAtual, "Funcionário inativado com sucesso!");
                            frameAtual.dispose();
                            new GerenciarFuncionario(); // para atualizar a listagem
                        } else {
                            JOptionPane.showMessageDialog(frameAtual, "Ocorreu um erro ao inativar o funcionário.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                
                actionPanel.add(btnEditar);
                actionPanel.add(btnInativar);

                funcionarioPanel.add(infoPanel, BorderLayout.CENTER);
                funcionarioPanel.add(actionPanel, BorderLayout.SOUTH);
                
                mainPanel.add(funcionarioPanel);
                mainPanel.add(new JLabel(" ")); // Para criar espaço entre os funcionários
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