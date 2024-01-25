package ORFhunter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gui.HomeForm;

public class ORFhunter {
	public ArrayList<ORF> searchOrfs(String seq, int minOrfSize, String startCodons, String endCodons) {
		ArrayList<Integer> starts = new ArrayList<Integer>();
		List<String> endCodonsList = Arrays.asList(endCodons.split(","));
		List<String> startCodonsList = Arrays.asList(startCodons.split(","));
		
		for(int i = 0; i < seq.length() - 3; i++) {
			String codon = seq.substring(i, i + 3);
			if(startCodonsList.contains(codon)) {
				starts.add(i);
			}
		}
		HomeForm.progressBar.setMaximum(starts.size());
		
		int frame = 0;
		ArrayList<ORF> orfsFound = new ArrayList<ORF>();
		for(Integer start : starts) {
			while(frame < 3) {
				for(int i = start + frame; i < seq.length() - 3 && frame < 3; i += 3) {
					String endCodon = seq.substring(i, i + 3);
					if(endCodonsList.contains(endCodon)) {
						int orfLength = (i - start) + 3;
						if( orfLength > minOrfSize && orfLength % 3 == 0) {
							ORF orf = new ORF(start, i + 3, frame + 1, orfLength);
							System.out.println(frame);
							orfsFound.add(orf);
							frame++;
						}
					}
				}
				frame++;
			}
			frame = 0;
			HomeForm.progressBar.setValue(HomeForm.progressBar.getValue() + 1);
		}
		
	return orfsFound;
	}
}
