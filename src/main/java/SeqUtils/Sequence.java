package SeqUtils;

public class Sequence {
	
	public String formatSeq(String seq) {
		String formatedSeq = "";
		int limit = 0;
		for(int i = 0; i < seq.length(); i++) {
			if(limit == 60) {
				formatedSeq += "\n";
				limit = 0;
			}
			formatedSeq += seq.substring(i, i + 1);
			limit++;
		}	
		
		return formatedSeq;
	}
	
	public int count(String seq, char nucl) {
		int nuclCount = 0;
		for(int i = 0; i < seq.length(); i++) {
			if(seq.charAt(i) == nucl) {
				nuclCount++;
			}
		}
		return nuclCount;
	}
	
	public double getGCContent(String seq) {
		int gcCount = 0;

        for (char nucleotide : seq.toCharArray()) {
            if (nucleotide == 'G' || nucleotide == 'C') {
                gcCount++;
            }
        }

        if (seq.length() > 0) {
            return ((double) gcCount / seq.length()) * 100.0;
        } else {
            return 0.0;
        }
    }
	
}
