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
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;

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
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
		totalOrfsLabel = new JLabel("Total ORFS found:");
		totalOrfsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		totalOrfsLabel.setBounds(10, 99, 231, 14);
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
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.setModel(model);
		for(int i = 0; i < orfsList.size(); i++) {			
			model.addRow(new Object[] {i + 1, orfsList.get(i).start + 1, orfsList.get(i).end, orfsList.get(i).frame, orfsList.get(i).length});
		}
		scrollPane.setViewportView(table);
		totalOrfsLabel.setText(totalOrfsLabel.getText() + orfsList.size());
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 255));
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel.setBounds(0, 0, 737, 52);
		contentPane.add(panel);
		
		lblNewLabel = new JLabel("ORFSView");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBackground(new Color(0, 0, 160));
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 36));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 64, 128));
		panel_1.setBounds(0, 401, 737, 65);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		lblNewLabel_1 = new JLabel("Get Fasta by ORF Nº");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(5, 22, 146, 19);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		
		idField = new JTextField();
		idField.setBounds(156, 18, 126, 26);
		panel_1.add(idField);
		idField.setFont(new Font("Calibri", Font.PLAIN, 16));
		idField.setColumns(10);
		
		exportFastaBtn = new JButton("Fasta Export");
		exportFastaBtn.setBounds(564, 18, 163, 27);
		panel_1.add(exportFastaBtn);
		exportFastaBtn.setForeground(new Color(255, 255, 255));
		exportFastaBtn.setBackground(new Color(0, 128, 255));
		exportFastaBtn.setFont(new Font("Arial", Font.PLAIN, 16));
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
		
		JButton getByIdBtn = new JButton("GET");
		getByIdBtn.setBounds(292, 18, 86, 27);
		panel_1.add(getByIdBtn);
		getByIdBtn.setForeground(new Color(255, 255, 255));
		getByIdBtn.setBackground(new Color(255, 128, 64));
		getByIdBtn.setFont(new Font("Arial", Font.PLAIN, 16));
		getByIdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<Vector> vector = model.getDataVector();
				int id = Integer.valueOf(idField.getText());
				for(Vector<Object> row : vector) {
					if(row.get(0).equals(id)) {
						SingleSequenceView ssv = new SingleSequenceView(
								ORFSView.this.seq, 
								Integer.valueOf(row.get(1).toString()) - 1, 
								Integer.valueOf(row.get(2).toString())
						);
						ssv.setVisible(true);
						break;
					}
				}
			}
		});
	}
}
