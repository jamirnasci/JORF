package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import SeqUtils.Sequence;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;

public class SingleSequenceView extends JFrame {

	private JPanel contentPane;

	public SingleSequenceView(String seq, int start, int end) {
		setResizable(false);
		setTitle("Fasta View");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 635, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel seqLengthLabel = new JLabel("Sequence length:");
		seqLengthLabel.setFont(new Font("Courier New", Font.BOLD, 18));
		seqLengthLabel.setBounds(10, 11, 207, 22);
		contentPane.add(seqLengthLabel);
		
		JLabel gcContentLabel = new JLabel("%GC:");
		gcContentLabel.setFont(new Font("Courier New", Font.BOLD, 18));
		gcContentLabel.setBounds(10, 44, 154, 14);
		contentPane.add(gcContentLabel);
		
		JLabel adenine = new JLabel("A:");
		adenine.setFont(new Font("Courier New", Font.BOLD, 18));
		adenine.setBounds(499, 15, 110, 14);
		contentPane.add(adenine);
		
		JLabel timine = new JLabel("T:");
		timine.setFont(new Font("Courier New", Font.BOLD, 18));
		timine.setBounds(499, 37, 110, 14);
		contentPane.add(timine);
		
		JLabel cytosine = new JLabel("C:");
		cytosine.setFont(new Font("Courier New", Font.BOLD, 18));
		cytosine.setBounds(499, 58, 110, 14);
		contentPane.add(cytosine);
		
		JLabel guanine = new JLabel("G:");
		guanine.setFont(new Font("Courier New", Font.BOLD, 18));
		guanine.setBounds(499, 80, 110, 14);
		contentPane.add(guanine);
		
		Sequence sequence = new Sequence();
		String orfInSequence = seq.substring(start, end);
		seqLengthLabel.setText(seqLengthLabel.getText() + orfInSequence.length());
		guanine.setText(guanine.getText()   + sequence.count(orfInSequence, 'G'));
		cytosine.setText(cytosine.getText() + sequence.count(orfInSequence, 'C'));
		adenine.setText(adenine.getText()   + sequence.count(orfInSequence, 'A'));
		timine.setText(timine.getText()     + sequence.count(orfInSequence, 'T'));
		gcContentLabel.setText(gcContentLabel.getText() + String.format("%.2f",  sequence.getGCContent(orfInSequence)));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 105, 599, 336);
		contentPane.add(scrollPane);
		
		JTextArea seqArea = new JTextArea();
		seqArea.setEditable(false);
		scrollPane.setViewportView(seqArea);
		seqArea.setFont(new Font("Courier New", Font.PLAIN, 13));
		seqArea.setText(sequence.formatSeq(orfInSequence));
	}

}
