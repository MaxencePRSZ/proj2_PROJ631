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
	
	public char getChar(){
		return (char)unicode;
	}
	
	
	
}
