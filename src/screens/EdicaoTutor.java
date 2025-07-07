package screens;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dao.TutorDAO;
import entities.Tutor;

public class EdicaoTutor extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txtNome = new JTextField();
	private JTextField txtCpf = new JTextField();
	private JTextField txtEmail = new JTextField();
	private JTextField txtTelefone = new JTextField();
	private JTextField txtCep = new JTextField();
	private JTextField txtEndereco = new JTextField();
	private JTextField txtBairro = new JTextField();
	private JTextField txtCidade = new JTextField();
	private JTextField txtUf = new JTextField();

	private Tutor tutorAtual;

	public EdicaoTutor(int tutorId) {
		setTitle("Edição de Tutor");
		setSize(800, 550); 
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(null);

		TutorDAO tutorDAO = new TutorDAO();
		this.tutorAtual = tutorDAO.buscarTutorPorId(tutorId);

		if (tutorAtual == null) {
			JOptionPane.showMessageDialog(this, "Tutor não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
			this.dispose();
			return;
		}

		addLabelAndField("Nome Completo:", 30, txtNome, 50, 50, 320, false);
		addLabelAndField("CPF:", 90, txtCpf, 110, 50, 320, false);
		addLabelAndField("E-mail (Login):", 150, txtEmail, 170, 50, 320, false);

		addLabelAndField("Telefone:", 30, txtTelefone, 50, 420, 320, true);
		addLabelAndField("CEP:", 90, txtCep, 110, 420, 320, true);
		addLabelAndField("Endereço:", 150, txtEndereco, 170, 420, 320, true);
		addLabelAndField("Bairro:", 210, txtBairro, 230, 420, 320, true);
		addLabelAndField("Cidade:", 270, txtCidade, 290, 420, 320, true);
		addLabelAndField("UF:", 330, txtUf, 350, 420, 100, true);

		preencherCampos();

		JButton btnSalvar = new JButton("Salvar Alterações");
		btnSalvar.setBounds(300, 410, 200, 40);
		btnSalvar.setFont(new Font("Arial", Font.BOLD, 16));
		add(btnSalvar);

		btnSalvar.addActionListener(e -> {
			salvarAlteracoesTutor();
		});

		setVisible(true);
	}

	private void preencherCampos() {
		txtNome.setText(tutorAtual.getNome());
		txtCpf.setText(tutorAtual.getCpf());
		txtEmail.setText(tutorAtual.getUsuario().getEmail());
		txtTelefone.setText(tutorAtual.getTelefone());
		txtCep.setText(tutorAtual.getCep());
		txtEndereco.setText(tutorAtual.getEndereco());
		txtBairro.setText(tutorAtual.getBairro());
		txtCidade.setText(tutorAtual.getCidade());
		txtUf.setText(tutorAtual.getUf());
	}

	private void salvarAlteracoesTutor() {
		tutorAtual.setTelefone(txtTelefone.getText());
		tutorAtual.setCep(txtCep.getText());
		tutorAtual.setEndereco(txtEndereco.getText());
		tutorAtual.setBairro(txtBairro.getText());
		tutorAtual.setCidade(txtCidade.getText());
		tutorAtual.setUf(txtUf.getText());

		TutorDAO tutorDAO = new TutorDAO();
		boolean sucesso = tutorDAO.atualizarTutor(tutorAtual);

		if (sucesso) {
			JOptionPane.showMessageDialog(this, "Tutor atualizado com sucesso!");
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Erro ao atualizar tutor.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addLabelAndField(String labelText, int labelY, JTextField textField, int fieldY, int x, int width,
			boolean editable) {
		JLabel label = new JLabel(labelText);
		label.setBounds(x, labelY, 150, 20);
		add(label);

		textField.setBounds(x, fieldY, width, 30);
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setEditable(editable);
		if (!editable) {
			textField.setBackground(new java.awt.Color(230, 230, 230));
		}
		add(textField);
	}
}
