package screens.funcionarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import dao.AgendamentoDAO;
import entities.Agendamento;
import entities.Funcionario;

public class MinhaAgenda extends JFrame {
    private static final long serialVersionUID = 1L;

    public MinhaAgenda(Funcionario funcionario) {
        setTitle("Minha Agenda - " + funcionario.getNome());
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        List<Agendamento> agendamentos = agendamentoDAO.listarAgendamentosPorFuncionario(funcionario.getId());

        if (agendamentos.isEmpty()) {
            mainPanel.add(new JLabel("Você não possui nenhum agendamento futuro."));
        } else {
            DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
            for (Agendamento ag : agendamentos) {
                mainPanel.add(criarCardAgendamento(ag, formatador));
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
        
        infoPanel.add(new JLabel("Data: " + agendamento.getDataAgendamento().toLocalDateTime().format(formatador)));
        infoPanel.add(new JLabel("Tutor: " + agendamento.getPet().getTutor().getNome()));

        JLabel lblStatus = new JLabel("Status: " + agendamento.getStatus());
        
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnInfo = new JButton("Info");
        btnInfo.addActionListener(e -> new InfoAgendamento(agendamento));
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> {
            new EdicaoAgendamento(agendamento);
            this.dispose();
        });
        
        if ("CONCLUÍDO".equalsIgnoreCase(agendamento.getStatus())) {
            lblStatus.setForeground(new Color(0, 128, 0));
            card.setBackground(new Color(240, 240, 240)); 
            btnEditar.setEnabled(false); 
        } else {
            lblStatus.setForeground(new Color(0, 100, 200));
        }

        infoPanel.add(lblStatus);
        card.add(infoPanel, BorderLayout.CENTER);
        
        botoesPanel.add(btnInfo);
        botoesPanel.add(btnEditar);
        card.add(botoesPanel, BorderLayout.EAST);

        return card;
    }
}