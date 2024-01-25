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
import javax.swing.SwingConstants;
import java.awt.Color;

public class SingleSequenceView extends JFrame {

	private JPanel contentPane;

	public SingleSequenceView(String seq, int start, int end) {
		setResizable(false);
		setTitle("Fasta View");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 635, 492);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel seqLengthLabel = new JLabel("Sequence length:");
		seqLengthLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		seqLengthLabel.setBounds(10, 11, 247, 22);
		contentPane.add(seqLengthLabel);
		
		JLabel gcContentLabel = new JLabel("%GC:");
		gcContentLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		gcContentLabel.setBounds(10, 79, 154, 14);
		contentPane.add(gcContentLabel);
		
		JLabel adenine = new JLabel("A:");
		adenine.setVerticalAlignment(SwingConstants.TOP);
		adenine.setFont(new Font("Consolas", Font.PLAIN, 15));
		adenine.setBounds(450, 15, 159, 15);
		contentPane.add(adenine);
		
		JLabel timine = new JLabel("T:");
		timine.setVerticalAlignment(SwingConstants.TOP);
		timine.setFont(new Font("Consolas", Font.PLAIN, 15));
		timine.setBounds(450, 37, 159, 14);
		contentPane.add(timine);
		
		JLabel cytosine = new JLabel("C:");
		cytosine.setVerticalAlignment(SwingConstants.TOP);
		cytosine.setFont(new Font("Consolas", Font.PLAIN, 15));
		cytosine.setBounds(450, 58, 159, 14);
		contentPane.add(cytosine);
		
		JLabel guanine = new JLabel("G:");
		guanine.setVerticalAlignment(SwingConstants.TOP);
		guanine.setFont(new Font("Consolas", Font.PLAIN, 15));
		guanine.setBounds(450, 80, 159, 14);
		contentPane.add(guanine);
		
		Sequence sequence = new Sequence();
		String orfInSequence = seq.substring(start, end);
		seqLengthLabel.setText(seqLengthLabel.getText() + orfInSequence.length());
		guanine.setText("Guanine:" + sequence.count(orfInSequence, 'G'));
		cytosine.setText("Cytosine:" + sequence.count(orfInSequence, 'C'));
		adenine.setText("Adenine:" + sequence.count(orfInSequence, 'A'));
		timine.setText("Thymine:" + sequence.count(orfInSequence, 'T'));
		gcContentLabel.setText(gcContentLabel.getText() + String.format("%.2f",  sequence.getGCContent(orfInSequence)));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 105, 599, 336);
		contentPane.add(scrollPane);
		
		JTextArea seqArea = new JTextArea();
		seqArea.setForeground(new Color(0, 0, 160));
		seqArea.setBackground(new Color(221, 221, 221));
		seqArea.setEditable(false);
		scrollPane.setViewportView(seqArea);
		seqArea.setFont(new Font("Courier New", Font.PLAIN, 13));
		seqArea.setText(sequence.formatSeq(orfInSequence));
	}

}
