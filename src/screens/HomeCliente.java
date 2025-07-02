package screens;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HomeCliente extends JFrame {
	private static final long serialVersionUID = 1L;

	public HomeCliente() {
        setTitle("Área do Cliente");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new JLabel("Bem-vindo, Cliente!"));
        setVisible(true);
    }
}