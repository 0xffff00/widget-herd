package hzk.trial;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JSlider;

public class SwingFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingFrame frame = new SwingFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SwingFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"uy", null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, "uyuuy", null, null, null, null},
				{null, null, "uy", "tyy", null, "uyuyyyu"},
				{"uuuuyy", null, null, "uyuuuy", "uuyu", null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"uucolumny", "New column", "New column", "New column", "Nyt", "New column"
			}
		));
		table.setBounds(44, 154, 152, -111);
		contentPane.add(table);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(10, 200, 93, 23);
		contentPane.add(btnNewButton);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(233, 194, 60, -88);
		contentPane.add(desktopPane);
		
		JSlider slider = new JSlider();
		slider.setBounds(152, 28, 200, 23);
		contentPane.add(slider);
	}
}
