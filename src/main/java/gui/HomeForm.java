package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import IO.ReadFasta;
import ORFhunter.ORF;
import ORFhunter.ORFhunter;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.ActionEvent;
import gui.ORFSView;
import java.awt.FlowLayout;
import javax.swing.JTextArea;

public class HomeForm extends JFrame {

	private JPanel contentPane;
	public static JProgressBar progressBar;
	private JComboBox startCodonsCombo;
	private JComboBox endCodonsCombo;
	private JComboBox minimalOrfSizeCombo;
	private ORFhunter oh;
	private StringBuilder stringBuilderPath;
	private String sequenceFilePathPicked;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeForm frame = new HomeForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public HomeForm() {
		setResizable(false);
		setTitle("JORF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 653, 499);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 64, 128));
		panel.setBounds(0, 0, 211, 459);
		contentPane.add(panel);
		panel.setLayout(null);
		
		startCodonsCombo = new JComboBox();
		startCodonsCombo.setBackground(new Color(255, 255, 255));
		startCodonsCombo.setForeground(new Color(0, 0, 0));
		startCodonsCombo.setFont(new Font("Consolas", Font.PLAIN, 14));
		startCodonsCombo.setModel(new DefaultComboBoxModel(new String[] {"ATG", "ATG,TTG"}));
		startCodonsCombo.setBounds(21, 68, 169, 22);
		panel.add(startCodonsCombo);
		
		endCodonsCombo = new JComboBox();
		endCodonsCombo.setBackground(new Color(255, 255, 255));
		endCodonsCombo.setForeground(new Color(0, 0, 0));
		endCodonsCombo.setFont(new Font("Consolas", Font.PLAIN, 14));
		endCodonsCombo.setModel(new DefaultComboBoxModel(new String[] {"TGA,TAA,TAG", "ATC,TTT", "TGA,TAA,TAG,ATC,TTT"}));
		endCodonsCombo.setBounds(21, 149, 169, 22);
		panel.add(endCodonsCombo);
		
		JLabel startCodonsLabel = new JLabel("Start codons:");
		startCodonsLabel.setForeground(new Color(255, 255, 255));
		startCodonsLabel.setVerticalAlignment(SwingConstants.TOP);
		startCodonsLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		startCodonsLabel.setBounds(21, 45, 138, 14);
		panel.add(startCodonsLabel);
		
		JLabel endCodonsLabel = new JLabel("End codons:");
		endCodonsLabel.setForeground(new Color(255, 255, 255));
		endCodonsLabel.setVerticalAlignment(SwingConstants.TOP);
		endCodonsLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		endCodonsLabel.setBounds(21, 124, 138, 14);
		panel.add(endCodonsLabel);
		
		minimalOrfSizeCombo = new JComboBox();
		minimalOrfSizeCombo.setBackground(new Color(255, 255, 255));
		minimalOrfSizeCombo.setForeground(new Color(0, 0, 0));
		minimalOrfSizeCombo.setFont(new Font("Consolas", Font.PLAIN, 14));
		minimalOrfSizeCombo.setModel(new DefaultComboBoxModel(new String[] {"75", "150", "300", "600"}));
		minimalOrfSizeCombo.setBounds(21, 225, 169, 22);
		panel.add(minimalOrfSizeCombo);
		
		JLabel minimalOrfSizeLabel = new JLabel("Minimal ORF size:");
		minimalOrfSizeLabel.setForeground(new Color(255, 255, 255));
		minimalOrfSizeLabel.setVerticalAlignment(SwingConstants.TOP);
		minimalOrfSizeLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		minimalOrfSizeLabel.setBounds(21, 200, 138, 15);
		panel.add(minimalOrfSizeLabel);
		
		JButton openSequenceBtn = new JButton("Open Fasta Sequence");
		openSequenceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				int result = chooser.showOpenDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					File sequenceFile = chooser.getSelectedFile();
					if(sequenceFile.exists()) {
						sequenceFilePathPicked = chooser.getSelectedFile().getAbsolutePath();	
					}else {
						JOptionPane.showMessageDialog(null, "File don't exists");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Sequence file not selected");
				}
			}
		});
		openSequenceBtn.setForeground(new Color(255, 255, 255));
		openSequenceBtn.setBackground(new Color(0, 128, 255));
		openSequenceBtn.setFont(new Font("Arial", Font.BOLD, 22));
		openSequenceBtn.setBounds(221, 317, 406, 37);
		contentPane.add(openSequenceBtn);
		
		JButton findOrfsBtn = new JButton("Find ORFS");
		findOrfsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(0);
				
				
				oh = new ORFhunter();
				ReadFasta rf = new ReadFasta();

				stringBuilderPath = rf.readSeq(sequenceFilePathPicked);
				if(sequenceFilePathPicked != null) {					
					Runnable runnable = new Runnable() {
						public void run() {
							ArrayList<ORF> orfsFound =  oh.searchOrfs(
									stringBuilderPath.toString(), 
									Integer.valueOf(minimalOrfSizeCombo.getSelectedItem().toString()), 
									startCodonsCombo.getSelectedItem().toString(), 
									endCodonsCombo.getSelectedItem().toString()
							);
							ORFSView orfsView = new ORFSView(orfsFound, stringBuilderPath.toString());
							orfsView.setVisible(true);
						}
					};
					
					ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

					executorService.submit(runnable);
					executorService.shutdown();
				}else {
					JOptionPane.showMessageDialog(null, "File not selected");
				}

			}
		});
		findOrfsBtn.setForeground(new Color(255, 255, 255));
		findOrfsBtn.setBackground(new Color(106, 181, 255));
		findOrfsBtn.setFont(new Font("Arial", Font.BOLD, 22));
		findOrfsBtn.setBounds(221, 365, 406, 37);
		contentPane.add(findOrfsBtn);
		
		progressBar = new JProgressBar();
		progressBar.setBackground(new Color(255, 255, 255));
		progressBar.setForeground(new Color(0, 0, 255));
		progressBar.setBounds(221, 413, 406, 24);
		contentPane.add(progressBar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 128, 255));
		panel_1.setBounds(211, 0, 426, 54);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel appTitle = new JLabel("JORF");
		appTitle.setBounds(5, 5, 73, 44);
		appTitle.setForeground(new Color(255, 255, 255));
		panel_1.add(appTitle);
		appTitle.setVerticalAlignment(SwingConstants.BOTTOM);
		appTitle.setHorizontalAlignment(SwingConstants.LEFT);
		appTitle.setFont(new Font("Calibri", Font.BOLD, 36));
		
		JLabel appSubTitle = new JLabel("Java Open Reading Frames Finder");
		appSubTitle.setForeground(new Color(255, 255, 255));
		appSubTitle.setBackground(new Color(255, 255, 255));
		appSubTitle.setBounds(5, 35, 165, 14);
		panel_1.add(appSubTitle);
		appSubTitle.setFont(new Font("Tahoma", Font.ITALIC, 11));
		
		JTextArea txtrUseOnly = new JTextArea();
		txtrUseOnly.setEnabled(false);
		txtrUseOnly.setForeground(new Color(128, 128, 128));
		txtrUseOnly.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		txtrUseOnly.setText("1. Use only fasta sequences as input.\r\n2. Only files with 1 sequence are acceptable.\r\n3. ORFs are searched for only in the direction the sequence is in.\r\n4. All probabilities of an ORF with the established start and stop codons are \r\ncalculated.\r\n\r\nAuthor: Jamir Nascimento Cardoso Junior\r\nEmail: https://github.com/jamirnasci\r\nGitHub: jamirnasci");
		txtrUseOnly.setBounds(221, 86, 406, 154);
		contentPane.add(txtrUseOnly);
	}
}
