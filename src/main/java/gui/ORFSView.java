package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import IO.WriteFasta;
import ORFhunter.ORF;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ORFSView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField idField;
	private JLabel totalOrfsLabel;
	private JButton exportFastaBtn;
	public ArrayList<ORF> orfsList;
	public String seq;
	private DefaultTableModel model;

	public ORFSView(ArrayList<ORF> orfsList, String seq) {
		setResizable(false);
		this.orfsList = orfsList;
		this.seq = seq;
		setTitle("ORFSView");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 753, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		totalOrfsLabel = new JLabel("Total ORFS found:");
		totalOrfsLabel.setBounds(10, 99, 147, 14);
		contentPane.add(totalOrfsLabel);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 124, 717, 236);
		contentPane.add(scrollPane);
		
		model = new DefaultTableModel();
		model.addColumn("ORF");
		model.addColumn("Start");
		model.addColumn("End");
		model.addColumn("Frame");
		model.addColumn("Length");
		
		table = new JTable();
		table.setModel(model);
		for(int i = 0; i < orfsList.size(); i++) {			
			model.addRow(new Object[] {i + 1, orfsList.get(i).start, orfsList.get(i).end, orfsList.get(i).frame, orfsList.get(i).length});
		}
		scrollPane.setViewportView(table);
		totalOrfsLabel.setText(totalOrfsLabel.getText() + orfsList.size());
		
		lblNewLabel = new JLabel("ORFSView");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(10, 11, 252, 55);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Get Fasta by ORF Nº");
		lblNewLabel_1.setBounds(10, 371, 121, 20);
		contentPane.add(lblNewLabel_1);
		
		idField = new JTextField();
		idField.setBounds(141, 371, 86, 20);
		contentPane.add(idField);
		idField.setColumns(10);
		
		JButton getByIdBtn = new JButton("GET");
		getByIdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<Vector> vector = model.getDataVector();
				int id = Integer.valueOf(idField.getText());
				for(Vector<Object> row : vector) {
					if(row.get(0).equals(id)) {
						SingleSequenceView ssv = new SingleSequenceView(
								ORFSView.this.seq, 
								Integer.valueOf(row.get(1).toString()), 
								Integer.valueOf(row.get(2).toString())
						);
						ssv.setVisible(true);
						break;
					}
				}
			}
		});
		getByIdBtn.setBounds(237, 371, 89, 20);
		contentPane.add(getByIdBtn);
		
		exportFastaBtn = new JButton("Fasta Export");
		exportFastaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int result = chooser.showOpenDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					if(ORFSView.this.orfsList != null && ORFSView.this.seq != null) {
						String dirpath = chooser.getSelectedFile().toString();
						WriteFasta wf = new WriteFasta();
						wf.writeMultipleSeq(ORFSView.this.orfsList, dirpath, ORFSView.this.seq);
						JOptionPane.showMessageDialog(null, "Exported with success !");
					}else {
						JOptionPane.showMessageDialog(null, "Error to export fasta");
					}
				}
			}
		});
		exportFastaBtn.setBounds(10, 399, 109, 23);
		contentPane.add(exportFastaBtn);
	}
}
