package proj2_PROJ631;

public class Node {
	private int unicode;
	private int freq;
	private Node filsG;
	private Node filsD;
	
	
	public Node(){};
	
	/**
	 * @param caract
	 * @param freq
	 * @param filsG
	 * @param filsD
	 */
	public Node(int unicode, int freq, Node filsG, Node filsD) {
		super();
		this.unicode = unicode;
		this.freq = freq;
		this.filsG = filsG;
		this.filsD = filsD;
	}
	
	public int getUnicode() {
		return unicode;
	}

	public int getFreq() {
		return freq;
	}

	public Node getFilsG() {
		return filsG;
	}

	public Node getFilsD() {
		return filsD;
	}

	public char getChar(){
		return (char)unicode;
	}
	
	public Node getMin(Node node){
		return this.freq<=node.freq ? this : node; 
	}
	
}
