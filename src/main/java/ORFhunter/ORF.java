package ORFhunter;

public class ORF {
	public int start;
	public int end;
	public int frame;
	public int length;
	
	public ORF(int start, int end, int frame, int length) {
		this.start = start;
		this.end = end;
		this.frame = frame;
		this.length = length;
	}

	@Override
	public String toString() {
		return "ORF [start= " + start + ", end= " + end + ", frame= " + frame + ", length= " + length + "]";
	}
	
}
