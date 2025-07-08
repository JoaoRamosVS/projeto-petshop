package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import dao.AgendamentoDAO;
import entities.Agendamento;
import entities.Usuario;

public class MeusAgendamentos extends JFrame {
    private static final long serialVersionUID = 1L;
    private Usuario usuarioLogado;

    public MeusAgendamentos(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("Meus Agendamentos");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        List<Agendamento> agendamentos = agendamentoDAO.listarAgendamentosPorUsuario(usuarioLogado.getId());

        if (agendamentos.isEmpty()) {
            mainPanel.add(new JLabel("Você não possui nenhum agendamento."));
        } else {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

            for (Agendamento agendamento : agendamentos) {
                mainPanel.add(criarCardAgendamento(agendamento, formatador));
                mainPanel.add(new JLabel(" "));
            }
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
    
    private JPanel criarCardAgendamento(Agendamento agendamento, DateTimeFormatter formatador) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
            new EmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel lblServico = new JLabel(agendamento.getServico().getDescricao() + " para " + agendamento.getPet().getNome());
        lblServico.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(lblServico);
        
        LocalDateTime dataHora = agendamento.getDataAgendamento().toLocalDateTime();
        infoPanel.add(new JLabel("Data: " + dataHora.format(formatador)));

        JLabel lblStatus = new JLabel("Status: " + agendamento.getStatus());
        configurarCorStatus(lblStatus, agendamento.getStatus(), dataHora);
        infoPanel.add(lblStatus);

        card.add(infoPanel, BorderLayout.CENTER);
        
        if ("AGENDADO".equalsIgnoreCase(agendamento.getStatus()) && dataHora.isAfter(LocalDateTime.now())) {
            JButton btnCancelar = new JButton("Cancelar");
            btnCancelar.addActionListener((ActionEvent e) -> {
                int resposta = JOptionPane.showConfirmDialog(this, 
                    "Tem certeza que deseja cancelar este agendamento?", 
                    "Confirmar Cancelamento", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);

                if (resposta == JOptionPane.YES_OPTION) {
                    AgendamentoDAO dao = new AgendamentoDAO();
                    if (dao.cancelarAgendamento(agendamento.getId())) {
                        JOptionPane.showMessageDialog(this, "Agendamento cancelado com sucesso!");
                        this.dispose();
                        new MeusAgendamentos(usuarioLogado);
                    } else {
                        JOptionPane.showMessageDialog(this, "Ocorreu um erro ao cancelar.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            card.add(btnCancelar, BorderLayout.EAST);
        }

        return card;
    }

    private void configurarCorStatus(JLabel label, String status, LocalDateTime dataHora) {
        label.setFont(new Font("Arial", Font.ITALIC, 12));
        if ("AGENDADO".equalsIgnoreCase(status)) {
            if (dataHora.isBefore(LocalDateTime.now())) {
                label.setText("Status: Realizado");
                label.setForeground(Color.BLUE);
            } else {
                label.setForeground(new Color(0, 128, 0));
            }
        } else if ("CANCELADO".equalsIgnoreCase(status)) {
            label.setForeground(Color.RED);
        } else {
            label.setForeground(Color.BLACK);
        }
    }
}