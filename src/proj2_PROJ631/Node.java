package proj2_PROJ631;

public class Node {
	private int unicode;
	private int freq;
	private String binCode;
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
		this.binCode = "-1";
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

	/**
	 * Transform the characters code to the characters itself
	 * @return The character as a char
	 */
	public char getChar(){
		return (char)unicode;
	}
	
	public String getBinCode() {
		return binCode;
	}
	
	public void setBinCode(String codeBin) {
		this.binCode = codeBin;
	}

	/**
	 * Compare two nodes ans return the one with the lowest frequency
	 * @param node The node to compare (Node)
	 * @return The lowest frequency node (Node)
	 */
	public Node getMin(Node node){
		return this.freq<=node.freq ? this : node; 
	}
	
	/**
	 * Test if the node is leaf, that means if the node has no
	 * right son or left son
	 * @return A boolean, true if leaf, false otherwise
	 */
	public boolean isLeaf(){
		return ((this.filsG == null) && (this.filsD == null));
	}
	
}
