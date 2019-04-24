package proj2_PROJ631;
import java.io.*;
import java.util.ArrayList;


public class Huffman {
	
	public static String freqFile = "";
	
	
	/**
	 * This method is meant to compress a text using huffman coding.
	 * It's just a call to other methods in the right order, it will then create
	 * a freqfile.dat and a compressed.txt in the Data repository 
	 * 
	 * @param URL The URL containing the text intended to be compressed
	 */
	public void compression(String URL) {
		ParseFreq parseF = new ParseFreq();
		parseF.textFileToArray(URL);
		ArrayList<Node> nodes = fillNodes(parseF.getUnicodeTable());
		Node root = buildTree(nodes);
		dfSearch(root, "");
		createFreqFile();
		String binCode = parseF.createBinCode(nodes);
		bitToByte(binCode);
	}
	
	
	public void decompression(String URLFreq, String URLFile){
		ParseFreq parseF = new ParseFreq();
		
		
	}
	
	
	/**
	 * Create an array of independent nodes containing a code from 0 to 254 and a frequency.
	 * Order them in a specific way : find the minimum of frequencies and the minimum comparing 
	 * ASCII code
	 * 
	 * @param freq An Array of the frequencies of each characters appearing in the text to compress.
	 * 			   It's a sized 255 array
	 * @return fillNodes an ArrayList of Nodes ordered and filled with information
	 */
	private ArrayList<Node> fillNodes(int[] freq){
		ArrayList<Node> nodes = new ArrayList<Node>();
		int min = 0;
		//Parcourt le tableau de frequence et trouve le minimum dans l'ordre Ascii
		//Puis lui met la valeur 0, jusqu'à que toutes les valeurs soient passées à 0
		while((min = getMinFromFreqs(freq)) != -1){
			Node node = new Node(min, freq[min], null, null);
			freq[min] = 0;
			nodes.add(node);
		}
		return nodes;
	}
	
	/**
	 * Get the minimum from an Array of 255 int, 0 excluded 
	 * @param freqs Array of 255 int
	 * @return res the minimum found
	 */
	private int getMinFromFreqs(int[] freqs){
		int min = (int)Double.POSITIVE_INFINITY;
		int res = -1;
		for (int i = 0; i < freqs.length; i++) {
			if(freqs[i] != 0 && freqs[i]<min){
				min = freqs[i];
				res=i;
			}
		}
		return res;
	}
	
	//Création de l'arbre, suppression des noeuds dans la liste, et ajout du noeud père
	//S'il reste plus qu'1 noeud, on a notre arbre
	/**
	 * Create a tree from the ArrayList of Nodes we previously created.
	 * Work on a clone of the ArrayList because as we advance in the algorithm
	 * we'll remove each node of the array so we won't have duplicate.
	 * We search for the two minimum nodes, and when found, we 
	 * @param nodes
	 * @return
	 */
	private Node buildTree(ArrayList<Node> nodes){
		@SuppressWarnings("unchecked")
		ArrayList<Node> nodes2 = (ArrayList<Node>) nodes.clone();
		while(nodes2.size() > 1){
			Node min1 = findMin(nodes2, null);
			Node min2 = findMin(nodes2, min1);
			Node sup = new Node(256, min1.getFreq()+min2.getFreq(), min1, min2); 
			nodes2.add(0, sup);
			nodes2.remove(min1);
			nodes2.remove(min2);
		}
		return nodes2.get(0); //Retourne la racine de l'arbre
	}
	
	private Node findMin(ArrayList<Node> nodes, Node nodeUnwanted){
		Node min = nodes.get(0) != nodeUnwanted ? nodes.get(0) : nodes.get(1);
		for (Node node : nodes) {
			if(node != nodeUnwanted)
				min = min.getMin(node);
		}
		return min;
	}
	
	private void dfSearch(Node root, String codeBin){
		if(root.isLeaf()){
			root.setBinCode(codeBin);
			System.out.println(root.getChar() + " : " + codeBin);
		}
		else{
			dfSearch(root.getFilsG(), codeBin + "0");
			dfSearch(root.getFilsD(), codeBin + "1");			
		}
	}
	
	private void createFreqFile(){
		try {
			File file = new File("Data/freqFile.dat");
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.write(freqFile);
			writer.close();			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private String bitToByte(String binCode){
		String byteString = "", res = "";
		for (int i = 0; i < binCode.length(); i++) {
			byteString += binCode.charAt(i);
			if(byteString.length()==8){
				res +=(char)Integer.parseInt(byteString, 2);
				byteString = "";
			}
		}
		try {
			File file = new File("Data/Compressed.txt");
			PrintWriter writer = new PrintWriter(file, "ISO-8859-1");
			writer.write(res);
			writer.close();			
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}

}
