package screens;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

public class HomeAdmin extends JFrame {
    private static final long serialVersionUID = 1L;

    public HomeAdmin() {
        setTitle("Painel do Administrador - Central Pet");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu menuCadastros = new JMenu("Cadastros");
        menuBar.add(menuCadastros);
        
        JMenuItem itemTutores = new JMenuItem("Tutores");
        itemTutores.addActionListener(e -> {
            new CadastroTutor(); 
        });
        menuCadastros.add(itemTutores);
        
        JMenuItem itemPets = new JMenuItem("Pets");
        itemPets.addActionListener(e -> {
            new CadastroPet();
        });
        menuCadastros.add(itemPets);
        
        JMenuItem itemFuncionarios = new JMenuItem("Funcionários");
        itemFuncionarios.addActionListener(e -> {
        	new CadastroFuncionario();
        });
        menuCadastros.add(itemFuncionarios);
        
        JMenu menuGerenciar = new JMenu("Gerenciar");
        menuBar.add(menuGerenciar);
        
        JMenuItem itemGerenciarTutores = new JMenuItem("Tutores");
        itemGerenciarTutores.addActionListener(e -> {
        	new GerenciarTutor();
        });
        menuGerenciar.add(itemGerenciarTutores);
        
        JMenuItem itemGerenciarFuncionarios = new JMenuItem("Funcionários");
        itemGerenciarFuncionarios.addActionListener(e -> {
        	new GerenciarFuncionario();
        });
        menuGerenciar.add(itemGerenciarFuncionarios);

        JMenuItem itemGerenciarUsuarios = new JMenuItem("Usuários");
        itemGerenciarUsuarios.addActionListener(e -> {
        	new GerenciarUsuario();
        });
        menuGerenciar.add(itemGerenciarUsuarios);
        
        JMenu menuOperacoes = new JMenu("Operações");
        menuBar.add(menuOperacoes);
        
        JMenuItem itemAgenda = new JMenuItem("Agenda de Serviços");
        menuOperacoes.add(itemAgenda);
        
        JMenuItem itemVenda = new JMenuItem("Nova Venda (PDV)");
        menuOperacoes.add(itemVenda);
        
        JMenuItem itemLogoff = new JMenuItem("Sair");
        itemLogoff.setForeground(Color.RED);
        itemLogoff.addActionListener(e -> {
        	dispose();
        	new Login();
        });
        menuBar.add(itemLogoff);
        
        ImageIcon logoOriginal = new ImageIcon(getClass().getResource("/assets/logo.png"));
        Image imagemRedimensionada = logoOriginal.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon logoRedimensionada = new ImageIcon(imagemRedimensionada);
        
        JLabel labelLogo = new JLabel(logoRedimensionada);
        add(labelLogo, BorderLayout.CENTER);
        
        setVisible(true);
    }
}