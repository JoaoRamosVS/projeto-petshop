package screens;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HomeAdmin extends JFrame {
	private static final long serialVersionUID = 1L;

	public HomeAdmin() {
        setTitle("Painel do Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new JLabel("Bem-vindo, Administrador!"));
        setVisible(true);
    }
}