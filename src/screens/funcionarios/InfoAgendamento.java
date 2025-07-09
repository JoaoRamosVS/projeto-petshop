package screens.funcionarios;

import java.awt.Font;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import entities.Agendamento;
import entities.Pet;

public class InfoAgendamento extends JFrame {
    private static final long serialVersionUID = 1L;

    public InfoAgendamento(Agendamento agendamento) {
        Pet pet = agendamento.getPet();
        
        setTitle("Informações: " + pet.getNome());
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        int y = 20;
        addInfoLabel("Nome do Pet:", pet.getNome(), y);
        y += 35;
        addInfoLabel("Raça:", pet.getRaca(), y);
        y += 35;
        
        String idade = "Não informada";
        if (pet.getDtNascimento() != null) {
            int anos = Period.between(pet.getDtNascimento(), LocalDate.now()).getYears();
            int meses = Period.between(pet.getDtNascimento(), LocalDate.now()).getMonths();
            if (anos > 0) {
                idade = anos + (anos == 1 ? " ano" : " anos");
            } else {
                idade = meses + (meses == 1 ? " mês" : " meses");
            }
        }
        addInfoLabel("Idade:", idade, y);
        y += 35;
        
        addInfoLabel("Tamanho:", pet.getTamanho().getDescricao(), y);
        y += 35;
        addInfoLabel("Peso:", pet.getPeso().toString() + " kg", y);
        y += 35;
        
        addInfoLabel("Tutor:", pet.getTutor().getNome(), y);
        y += 35;
        addInfoLabel("Telefone do Tutor:", pet.getTutor().getTelefone(), y);
        y += 45;
        
        addTextArea("Observações:", pet.getObs(), y);
        y += 110;
        addTextArea("Ocorrências:", pet.getOcorrencias(), y);

        setVisible(true);
    }
    
    private void addInfoLabel(String titulo, String valor, int y) {
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setBounds(20, y, 150, 25);
        this.add(lblTitulo);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 14));
        lblValor.setBounds(180, y, 250, 25);
        this.add(lblValor);
    }
    
    private void addTextArea(String titulo, String valor, int y) {
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setBounds(20, y, 150, 25);
        this.add(lblTitulo);
        
        JTextArea areaTexto = new JTextArea(valor);
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBounds(20, y + 25, 390, 80);
        this.add(scroll);
    }
}