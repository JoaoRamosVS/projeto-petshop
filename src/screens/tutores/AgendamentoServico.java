package screens.tutores;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dao.AgendamentoDAO;
import dao.PetDAO;
import dao.ServicoDAO;
import entities.Agendamento;
import entities.Pet;
import entities.Servico;
import entities.Usuario;

public class AgendamentoServico extends JFrame {
    private static final long serialVersionUID = 1L;
    private Usuario usuarioLogado;

    private JComboBox<Pet> comboPets;
    private JComboBox<Servico> comboServicos;
    private JFormattedTextField txtData;
    private JPanel painelHorarios;

    public AgendamentoServico(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("Agendar Novo Serviço");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        addLabelAndComboBox("Selecione o Pet:", 20, comboPets = new JComboBox<>(), 40);
        popularComboPets();

        addLabelAndComboBox("Tipo de Serviço:", 80, comboServicos = new JComboBox<>(), 100);
        popularComboServicos();

        JLabel lblData = new JLabel("Data (dd/mm/aaaa):");
        lblData.setBounds(50, 140, 150, 20);
        add(lblData);

        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            txtData = new JFormattedTextField(mascaraData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtData.setBounds(50, 160, 150, 30);
        add(txtData);

        JButton btnVerificar = new JButton("Verificar Horários");
        btnVerificar.setBounds(220, 160, 150, 30);
        add(btnVerificar);

        JLabel lblHorarios = new JLabel("Selecione um horário disponível:");
        lblHorarios.setBounds(50, 210, 300, 20);
        add(lblHorarios);

        painelHorarios = new JPanel(new GridLayout(0, 4, 10, 10));
        painelHorarios.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollHorarios = new JScrollPane(painelHorarios);
        scrollHorarios.setBounds(50, 230, 480, 250);
        add(scrollHorarios);

        btnVerificar.addActionListener(e -> atualizarHorariosDisponiveis());

        setVisible(true);
    }
    
    private void atualizarHorariosDisponiveis() {
        painelHorarios.removeAll();

        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataSelecionada;
        try {
            dataSelecionada = LocalDate.parse(txtData.getText(), formatadorData);
            
            if(dataSelecionada.isBefore(LocalDate.now())) {
            	JOptionPane.showMessageDialog(this, "Por favor, insira uma data futura ou igual a data de hoje.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } 
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Por favor, insira uma data válida no formato dd/mm/aaaa.", "Erro de Data", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        List<Timestamp> horariosOcupados = agendamentoDAO.getHorariosOcupadosPorDia(dataSelecionada);
        List<LocalTime> horariosPossiveis = gerarHorariosDoDia();

        for (LocalTime horario : horariosPossiveis) {
            boolean ocupado = false;
            for (Timestamp horarioOcupado : horariosOcupados) {
                if (horarioOcupado.toLocalDateTime().toLocalTime().equals(horario)) {
                    ocupado = true;
                    break;
                }
            }
            painelHorarios.add(criarBotaoHorario(horario, !ocupado));
        }

        painelHorarios.revalidate();
        painelHorarios.repaint();
    }

    private List<LocalTime> gerarHorariosDoDia() {
        List<LocalTime> horarios = new ArrayList<>();
        for (int i = 9; i <= 17; i++) {
            horarios.add(LocalTime.of(i, 0));
        }
        return horarios;
    }

    private JButton criarBotaoHorario(LocalTime horario, boolean disponivel) {
        JButton botao = new JButton(horario.format(DateTimeFormatter.ofPattern("HH:mm")));
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if (disponivel) {
            botao.setBackground(new Color(210, 255, 210));
            botao.addActionListener(e -> confirmarAgendamento(horario));
        } else {
            botao.setBackground(new Color(255, 200, 200));
            botao.setEnabled(false);
        }
        return botao;
    }
    
    private void confirmarAgendamento(LocalTime horario) {
        Pet petSelecionado = (Pet) comboPets.getSelectedItem();
        Servico servicoSelecionado = (Servico) comboServicos.getSelectedItem();
        LocalDate dataSelecionada = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        Timestamp dataHoraAgendamento = Timestamp.valueOf(dataSelecionada.atTime(horario));

        Agendamento novoAgendamento = new Agendamento();
        novoAgendamento.setPet(petSelecionado);
        novoAgendamento.setCriador(usuarioLogado);
        novoAgendamento.setServico(servicoSelecionado);
        novoAgendamento.setDataAgendamento(dataHoraAgendamento);

        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        boolean sucesso = agendamentoDAO.agendarServico(novoAgendamento);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Serviço agendado com sucesso!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao confirmar o agendamento.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void popularComboPets() {
        PetDAO petDAO = new PetDAO();
        List<Pet> pets = petDAO.listarPetsPorUsuario(usuarioLogado.getId()); 
        for (Pet pet : pets) {
            comboPets.addItem(pet);
        }
    }
    
    private void popularComboServicos() {
        ServicoDAO servicoDAO = new ServicoDAO();
        List<Servico> servicos = servicoDAO.listarServicos();
        for (Servico servico : servicos) {
            comboServicos.addItem(servico);
        }
    }
    
    private void addLabelAndComboBox(String labelText, int labelY, JComboBox<?> comboBox, int comboY) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, labelY, 150, 20);
        add(label);
        comboBox.setBounds(50, comboY, 380, 30);
        add(comboBox);
    }
}