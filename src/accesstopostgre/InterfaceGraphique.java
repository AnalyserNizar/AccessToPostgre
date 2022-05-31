package accesstopostgre;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class InterfaceGraphique extends JFrame {

	static JPanel contentPane;
	private JTextField textField_username;
	private JTextField textField_pwd;
	private JLabel lblNewLabel_username;
	private JLabel lblNewLabel_pwd;
	static String username;
	static String pwd;

	/**
	 * Create the frame.
	 */
	public InterfaceGraphique() {

		setTitle("Outil de migration de Access a Postgres");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 316);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNewLabel_pwd = new JLabel("Nom d'utilisateur postgres");
		lblNewLabel_pwd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_pwd.setBounds(37, 11, 169, 29);
		contentPane.add(lblNewLabel_pwd);

		lblNewLabel_username = new JLabel("Mot de passe");
		lblNewLabel_username.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_username.setBounds(37, 100, 100, 36);
		contentPane.add(lblNewLabel_username);

		textField_username = new JTextField();
		textField_username.setBounds(37, 63, 208, 20);
		contentPane.add(textField_username);
		textField_username.setColumns(10);

		textField_pwd = new JTextField();
		textField_pwd.setBounds(37, 147, 208, 20);
		contentPane.add(textField_pwd);
		textField_pwd.setColumns(10);

		JButton btnNewButton = new JButton("Importer votre Base de Donn\u00E9e");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				username = textField_username.getText();
				pwd = textField_pwd.getText();
				FileChooser.createFilePicker();
				try {
					new EmptyPostgresDB();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {

					new MicrosoftAccessConnection();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				new PostgreSQLConnection();
				JOptionPane.showMessageDialog(null, "Base de donnée crée!");
			}
		});
		btnNewButton.setBounds(118, 203, 216, 36);
		contentPane.add(btnNewButton);
		this.setVisible(true);


	}
}
