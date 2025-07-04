package screens;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class HomeAdmin extends JFrame {
    private static final long serialVersionUID = 1L;

    public HomeAdmin() {
        setTitle("Painel do Administrador - Central Pet");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu menuCadastros = new JMenu("Cadastros");
        menuBar.add(menuCadastros);
        
        JMenuItem itemClientes = new JMenuItem("Clientes (Tutores)");
        itemClientes.addActionListener(_ -> {
            new CadastroCliente(); 
        });
        menuCadastros.add(itemClientes);
        
        JMenuItem itemPets = new JMenuItem("Pets");
        itemPets.addActionListener(_ -> {
            new CadastroPet();
        });
        menuCadastros.add(itemPets);
        
        JMenuItem itemProdutos = new JMenuItem("Produtos");
        menuCadastros.add(itemProdutos);
        
        JMenuItem itemFuncionarios = new JMenuItem("Funcionários");
        menuCadastros.add(itemFuncionarios);

        JMenu menuOperacoes = new JMenu("Operações");
        menuBar.add(menuOperacoes);
        
        JMenuItem itemAgenda = new JMenuItem("Agenda de Serviços");
        menuOperacoes.add(itemAgenda);
        
        JMenuItem itemVenda = new JMenuItem("Nova Venda (PDV)");
        menuOperacoes.add(itemVenda);
        
        setVisible(true);
    }
}