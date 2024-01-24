package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFasta {
	public StringBuilder readSeq(String path) {
		File file = new File(path);
		StringBuilder sequence = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line="";
			while((line = br.readLine()) != null) {
				if(line.charAt(0) != '>') {
					sequence.append(line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sequence;
	}
}
