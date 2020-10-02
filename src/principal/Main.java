package principal;

import java.awt.EventQueue;

import javax.swing.*;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;


public class Main {

	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnNewButton_1; 

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ArrayList<Proceso> listaProcesos = new ArrayList<Proceso> ();
		
		model = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Procesos", "Llegada", "Tiempo Servicio"
				}
			);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 457, 327);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel title = new JPanel();
		frame.getContentPane().add(title, BorderLayout.NORTH);
		
		JLabel lblRounRobin = new JLabel("ROUN ROBIN");
		lblRounRobin.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
		title.add(lblRounRobin);
		
		JPanel body = new JPanel();
		frame.getContentPane().add(body, BorderLayout.CENTER);
		body.setLayout(new BorderLayout(0, 0));
		
		JPanel buttons = new JPanel();
		body.add(buttons, BorderLayout.NORTH);
		buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblProcesos = new JLabel("PROCESOS");
		buttons.add(lblProcesos);
		
		JButton btnAadir = new JButton("A\u00F1adir");
		btnAadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tiempoProceso = JOptionPane.showInputDialog("Ingrese tiempo del proceso");
				String tiempoLLegada = JOptionPane.showInputDialog("Ingrese tiempo de llegada del proceso","0");
				try {
					int tiempo = Integer.parseInt(tiempoProceso);
					int llegada = Integer.parseInt(tiempoLLegada);
					Proceso pAux = new Proceso(((char)(65+listaProcesos.size())),tiempo, llegada);
					listaProcesos.add(pAux);
					model.addRow(new Object[]{pAux.getID(), pAux.getTiempoLlegada(),pAux.getTiempo()});
					table.setModel(model);
					JOptionPane.showMessageDialog(null,"Proceso "+pAux.getID()+" AÑADIDO");
					btnNewButton_1.setVisible(true);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null,"DEBE INGRESAR UN NUMERO");
				}
			}
		});
		buttons.add(btnAadir);
		
		JLabel label = new JLabel("            ");
		buttons.add(label);
		
		JCheckBox chckSJF = new JCheckBox("SJF");
		buttons.add(chckSJF);
		
		JButton btnNewButton = new JButton("Ejecutar Round Robin");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listaProcesos.isEmpty()) {
					JOptionPane.showMessageDialog(null, "NO HAY PROCESOS EN LA LISTA");
				}else {
					RoundRobin ventana = new RoundRobin(listaProcesos, chckSJF.isSelected());
					ventana.setVisible(true);
				}
			}
		});
		buttons.add(btnNewButton);
		
		
		
		JPanel tablePanel = new JPanel();
		body.add(tablePanel, BorderLayout.CENTER);
		tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel tableProcesosGUI = new JPanel();
		tablePanel.add(tableProcesosGUI);
		tableProcesosGUI.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Cola de Procesos");
		tableProcesosGUI.add(lblNewLabel, BorderLayout.NORTH);
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(); 
		renderer.setPreferredSize(new Dimension(12, 12)); 
		
		
		table = new JTable();
		table.setModel(model);
		table.getTableHeader().setDefaultRenderer(renderer);
		table.setPreferredScrollableViewportSize(new Dimension(350,130));
        table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		tableProcesosGUI.add(scrollPane, BorderLayout.CENTER);
		
		btnNewButton_1 = new JButton("Eliminar todo");
		btnNewButton_1.setVisible(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model = new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
							"Procesos", "Llegada", "Tiempo Servicio"
						}
					);
				table.setModel(model);
				listaProcesos.clear();
				btnNewButton_1.setVisible(false);
			}
		});
		tablePanel.add(btnNewButton_1);
	}

}
