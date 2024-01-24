import java.util.ArrayList;

import IO.ReadFasta;
import ORFhunter.ORF;
import ORFhunter.ORFhunter;

public class Main {

	public static void main(String[] args) {
		String filePath = "C:\\Users\\Felipe\\Documents\\Circovírus\\GCA_000892615.1\\circovirus.fna";
		ORFhunter oh = new ORFhunter();
		ReadFasta rf = new ReadFasta();
		
		StringBuilder sb = rf.readSeq(filePath);

		ArrayList<ORF> orfsFoud =  oh.searchOrfs(sb.toString(), 600, "ATG", "TGA,TAA,TAG");
	}

}
