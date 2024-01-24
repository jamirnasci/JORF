package IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import SeqUtils.Sequence;

import ORFhunter.ORF;

public class WriteFasta {

	
	public void writeMultipleSeq(ArrayList<ORF> orfsList, String path, String seq) {
		File file = new File(path+"\\orfsfound.fasta");
		BufferedWriter bw = null;
		Sequence sequence = new Sequence();
		try {
			bw = new BufferedWriter(new FileWriter(file));
			int orfsCount = 1;
			for(ORF orf : orfsList) {
				bw.write(">ORF "+orfsCount+" location="+orf.start+".."+orf.end+" length="+orf.length+" frame="+orf.frame+"\n");
				bw.write(sequence.formatSeq(seq.substring(orf.start, orf.end)));
				bw.write("\n");
				orfsCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
