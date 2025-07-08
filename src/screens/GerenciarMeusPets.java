package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import dao.PetDAO;
import entities.Pet;
import entities.Tutor;

public class GerenciarMeusPets extends JFrame {
    private static final long serialVersionUID = 1L;
    private Tutor tutorLogado;

    public GerenciarMeusPets(Tutor tutor) {
        this.tutorLogado = tutor;

        setTitle("Meus Pets - " + tutorLogado.getNome());
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("Meus Companheiros");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelSuperior.add(lblTitulo, BorderLayout.WEST);

        JButton btnAdicionarPet = new JButton("+ Adicionar Novo Pet");
        btnAdicionarPet.setFont(new Font("Arial", Font.BOLD, 12));
        btnAdicionarPet.addActionListener(_ -> {
        	new CadastroPet(tutorLogado);
            this.dispose(); 
        });
        painelSuperior.add(btnAdicionarPet, BorderLayout.EAST);
        
        add(painelSuperior, BorderLayout.NORTH);

        JPanel painelLista = new JPanel();
        painelLista.setLayout(new BoxLayout(painelLista, BoxLayout.Y_AXIS));
        painelLista.setBorder(new EmptyBorder(10, 10, 10, 10));

        PetDAO petDAO = new PetDAO();
        List<Pet> pets = petDAO.listarPetsPorTutor(tutorLogado.getId());

        if (pets.isEmpty()) {
            painelLista.add(new JLabel("Você ainda não cadastrou nenhum pet."));
        } else {
            for (Pet pet : pets) {
                painelLista.add(criarCardPet(pet));
                painelLista.add(new JLabel(" "));
            }
        }

        JScrollPane scrollPane = new JScrollPane(painelLista);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel criarCardPet(Pet pet) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1, true),
            new EmptyBorder(10, 10, 10, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        JLabel lblNome = new JLabel(pet.getNome());
        lblNome.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(lblNome);
        infoPanel.add(new JLabel("Raça: " + pet.getRaca()));
        int idade = 0;
        if (pet.getDtNascimento() != null) {
            idade = Period.between(pet.getDtNascimento(), LocalDate.now()).getYears();
        }
        infoPanel.add(new JLabel("Idade: " + idade + " anos | Peso: " + pet.getPeso() + "kg"));
        infoPanel.add(new JLabel("Tamanho: " + pet.getTamanho().getDescricao()));
        
        card.add(infoPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(_ -> {
            new EdicaoPet(pet);
            GerenciarMeusPets.this.dispose();
        });
        actionPanel.add(btnEditar);
        
        card.add(actionPanel, BorderLayout.SOUTH);

        return card;
    }
}