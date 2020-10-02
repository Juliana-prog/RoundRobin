package principal;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import colorTabla.MiRenderer;

import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class RoundRobin extends JFrame {

	private JPanel contentPane;
	private JTable table_1= new JTable();
	private DefaultTableModel model_1;
	private ArrayList<String> procesosID = new ArrayList<String>();
	private JTable table;
	private DefaultTableModel model;
	private JTable table_2;
	private DefaultTableModel model_2;
	
	/** 
	 *
	 */
	public RoundRobin(ArrayList<Proceso> procesosLista, boolean b) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200, 200, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel title = new JPanel();
		contentPane.add(title, BorderLayout.NORTH);
		
		JLabel lblRondas = new JLabel("RONDAS");
		title.add(lblRondas);
		
		JLabel label = new JLabel("                                    ");
		title.add(label);
		
		JLabel lblEstimadores = new JLabel("Estimadores");
		title.add(lblEstimadores);
		
		JPanel body = new JPanel();
		contentPane.add(body, BorderLayout.CENTER);
		body.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panelRondas = new JPanel();
		body.add(panelRondas);
		
		
		
		model_1= agregarProcesos(procesosLista);
		panelRondas.setLayout(new BorderLayout(0, 0));
		table_1.setModel(model_1);
		table_1.setPreferredScrollableViewportSize(new Dimension(450,190));
		JScrollPane scrollRondas = new JScrollPane(table_1);
		panelRondas.add(scrollRondas);
		
		
		JPanel panelEstimadores = new JPanel();
		body.add(panelEstimadores);
		panelEstimadores.setLayout(new GridLayout(2, 1, 0, 0));
		
		////TABLA TIEMPO DE ESPERA //////
		table_2 = new JTable();
		model_2= new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Proceso", "T. Espera"
				}
			);
		table_2.setModel(model_2);
		JScrollPane scrollTEspera = new JScrollPane(table_2);
		panelEstimadores.add(scrollTEspera);
		///Fin  TABLA T. ESPERA ////////
		
		///TABLA TIEMPOS DE RETORNO /////
		table = new JTable();
		model=new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Proceso", "T. Retorno"
				}
			);
		table.setModel(model);

		JScrollPane scrollTRetorno = new JScrollPane(table);
		panelEstimadores.add(scrollTRetorno);
		///FIN TABLA T.RETORNO///////
		
		int q=Integer.parseInt(JOptionPane.showInputDialog("Ingrese Q"));
		if (b){
			ejecutarSFJ(procesosLista,q);
		}else {
			ejecutar(procesosLista,q);
		}
			
		
	}
	
	
	
	private void ejecutarSFJ(ArrayList<Proceso> procesosLista, int q) {
		/////////////////INICIALIZO VARIABLES Y ESTRUCTURAS AUX///////////////////
		char pAnt = 0;
		char pActual=0;
		int ronda= 0;
		double tiempoIni=(double)q/10/2;
		double r = tiempoIni;//se inicia con la mitad del tiempo de retorno
		
		Integer []rowData =new Integer[procesosID.size()];
		rowData[0]=ronda;
		for (int i = 0; i < procesosLista.size(); i++) { //almaceno ronda 0 los tiempos iniciales de los procesos
			rowData[i+1]=procesosLista.get(i).getTiempo();
		}
		rowData[0]=ronda;
		model_1.addRow(rowData);
		ArrayList<Proceso> procesosOrdenados=new ArrayList<Proceso>();
		ArrayList<Proceso> procesosListos=new ArrayList<Proceso>();
		ArrayList<Proceso> procesosAux=new ArrayList<Proceso>();
		procesosAux.addAll(procesosLista);
		procesosID.remove(0);
		///////////////FIN INICIALIZACION /////
		
		while (procesosID.size()>0) {
		rowData[0]=ronda;
		
/////////////////SJF INICIO/////////////////
			for (Proceso proceso : procesosAux) {////////////VEO LOS PROCESOS Y SU RONDA DE LLEGADA
				if (proceso.getTiempoLlegada()<=r) {///////////SI EL TIEMPO DE LLEGADA CORRESPONDE A LA RONDA LOS ORDENO
					procesosOrdenados.add(proceso);
				}
			}
			for (Proceso proceso : procesosOrdenados) {//////////ELIMINO LOS PROCESOS YA ORDENADOS
				procesosAux.remove(proceso);
			}
			ordenar(procesosOrdenados);///ORDENO
			procesosListos.addAll(procesosOrdenados);/////LOS AGREGO A LA COLA PARA PROCESAR
			procesosOrdenados.clear();///ELIMINO A LOS PROCESOS YA AGREGADOS A LA COLA	
///////////////////FIN SJF///////////////
			
			int tamaño=procesosListos.size();
			int i=0;
			
			pAnt=procesosListos.get(0).getID();
			///////////////////////ROUND ROBIN///////////
			while ((!(procesosListos.isEmpty()))&&(tamaño>i)){
				Proceso proceso=procesosListos.get(0);
				int aux=rowData[((int)proceso.getID())-64];
				pActual=proceso.getID();

				if (!(pActual == pAnt)) {
					r=(r+(q/10));
					pAnt=pActual;
				}
				
				if (aux>q) {
					procesosListos.add(proceso);
					rowData[((int)proceso.getID())-64]=aux-q;
					r=r+q;
				}else {
					r=r+aux;
					rowData[((int)proceso.getID())-64]=0;
					procesosID.remove(Character.toString(proceso.getID()));
					if (procesosID.isEmpty()) { //si es el ultimo proceso se le agrega el tiempo de salida
						r=r+tiempoIni;
					}
					procesosLista.get(((int)proceso.getID())-65).setTiempoRetorno(r);
				}
				procesosListos.remove(0);
				i++;
			}	
			model_1.addRow(rowData);
			ronda=ronda+1;
		}
////////////////////FIN ROUND ROBIN/////
		
		mostrarTiempoRetorno(procesosLista);//cargo tabla con los tiempo de Retortno y TRP
		mostrarTiempoEspera(procesosLista);//cargo tabla con los tiempos de espera y TEP
		table_1.setModel(model_1);//cargo la tabla de Rondas
		table_1.setDefaultRenderer(Object.class, new MiRenderer()); //cambio color de las filas y celdas
	}



	
	private void ordenar(ArrayList<Proceso> procesosOrdenados) {
		for (int i = 0; i < procesosOrdenados.size(); i++) {
			Proceso array_element = procesosOrdenados.get(i);
			for (int j = i+1; j < procesosOrdenados.size(); j++) {
				 if (array_element.getTiempo()>procesosOrdenados.get(j).getTiempo()) {
					Proceso aux = procesosOrdenados.get(j);
					procesosOrdenados.set(j, array_element);
					array_element=aux;
				 }
			}
			procesosOrdenados.set(i, array_element);
		}
		
	}



	private DefaultTableModel agregarProcesos(ArrayList<Proceso> procesosLista) {
		procesosID.add("Ronda");
		for (Proceso proceso : procesosLista) {
			procesosID.add(Character.toString(proceso.getID()));
		}
		String[] item = procesosID.toArray(new String[procesosID.size()]);    
		DefaultTableModel modelo=new DefaultTableModel(new Object[][] {},item);
		return modelo;	
	}

	
/*
 * En la implementacion uso 3 listas con procesos
 * 	1. La primera "colaListo" es para saber cuantos procesos existen (esta lista no es modificada, solamente se le agrega los resultados al finalizar el proceso)
 *  2. La segunda "procesosID " es propia de la clase solo almacena los ID de proceso, se van eliminando a medida que son procesados
 *  3. La ultima "rowData" almacena los tiempos de los procesos, se modifican, restando tiempo de servicio pero no se eliminan 
 *    												los procesos para poder mostrar en la tabla por cada ronda los resultados, 
 *    												si un proceso no tiene mas tiempo de servicio restante se marcara en gris
 *  												cada ronda va intercalando colores para diferenciar 								 
*/
	private void ejecutar(ArrayList<Proceso> colaListo, int q) {
		////INICIO Y DEF DE VARIABLES
		int ronda=0;
		double aux=(double)q/10/2;
		double r = aux;//se inicia con la mitad del tiempo de retorno
		char pAnt = 0;
		char pActual=0;
		Integer []rowData =new Integer[procesosID.size()];
		///////////////////FIN VARIABLES////////////////////
		
		for (int i = 0; i < colaListo.size(); i++) { //almaceno ronda 0 los tiempos iniciales de los procesos
			rowData[i+1]=colaListo.get(i).getTiempo();
		}
		rowData[0]=ronda;
		model_1.addRow(rowData);//muestro ronda 0
		
		procesosID.remove(0);//quito el primer elemento que era la etiqueta "RONDA" del encabezado de la tabla
				
		
//////////////////ROUND ROBIN///////////////
		pAnt=procesosID.get(0).charAt(0);
		while (procesosID.size()>0) { //Recorro proceso
			rowData[0]=++ronda;
			for (int i = 1; i < rowData.length; i++) {//"EJECUTO" los procesos
				
				if (! (rowData[i]==0)) {//tiempo de intercambio
					pActual=(char)(64+i);
					if (!(pActual == pAnt)) {
						r=(r+(q/10));
						pAnt=pActual;
					}					
					
					if ((rowData[i])>q) { //si es mayor solo le quito tiempo de servicio restante
						rowData[i]=rowData[i]-q;
						r=r+q;
					}else  {	//sino le quito el tiempo de servicio y lo elimino de la lista de procesos
						r=r+rowData[i];
						rowData[i]=0;		
						procesosID.remove(Character.toString((char)(64+i)));
						
						if (procesosID.isEmpty()) { //si es el ultimo proceso se le agrega el tiempo de salida
							r=r+aux;
						}
						colaListo.get(i-1).setTiempoRetorno(r);
					}	
				}	
			}
			model_1.addRow(rowData);//almaceno resultados de la ronda para mostrar
		}
/////////////////FIN ROUND ROBIN/////////////////////////
		
		mostrarTiempoRetorno(colaListo);//cargo tabla con los tiempo de Retortno y TRP
		mostrarTiempoEspera(colaListo);//cargo tabla con los tiempos de espera y TEP
		
		table_1.setModel(model_1);//cargo la tabla de Rondas
		table_1.setDefaultRenderer(Object.class, new MiRenderer()); //cambio color de las filas y celdas
	}

	
	
	private void mostrarTiempoEspera(ArrayList<Proceso> procesosLista) {
		DecimalFormat df = new DecimalFormat("#0.00");
		double prom=0;
		double tEspera;
		for (Proceso proceso : procesosLista) {
			tEspera = proceso.getTiempoRetorno();
			tEspera = (tEspera- proceso.getTiempo());
			prom=prom+tEspera;
			model_2.addRow(new Object[] {
					proceso.getID(),(df.format(tEspera))
			});
		}	
		prom=(prom/(procesosLista.size()));
		model_2.addRow(new Object[] {
				"T.E.P",(df.format(prom))
		});
	}

	private void mostrarTiempoRetorno(ArrayList<Proceso> procesosLista) {
		DecimalFormat df = new DecimalFormat("#.00");
		double prom=0;
		for (Proceso proceso : procesosLista) {
			prom=prom+proceso.getTiempoRetorno();
			model.addRow(new Object[] {
					proceso.getID(),df.format(proceso.getTiempoRetorno())
			});
		}
		prom=(prom/(procesosLista.size()));
		model.addRow(new Object[] {
				"T.R.P",(df.format(prom))
		});
	}
	
}


