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
	
	
	/**
	 * Decode huffman text from a frequencies file and a compressed text
	 * @param URLFreq string URL of the frequencies file
	 * @param URLFile string URL of the compressed text
	 */
	public void decompression(String URLFreq, String URLFile){
		ParseFreq parseF = new ParseFreq();
		parseF.freqFileToArray(URLFreq);
		String binCode = parseF.compressedToBinCode(URLFile);
		ArrayList<Node> nodes = fillNodes(parseF.getUnicodeTable());
		Node root = buildTree(nodes);
		dfSearch(root, "");
		for (Node node : nodes) {
			System.out.println("Lettre : " + node.getChar() + " - Code : " + node.getBinCode() + " Freq : " + node.getFreq());
		}
		reBuildText(nodes, binCode);
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
	 * We search for the two minimum nodes, and when found, we create the father of
	 * them, which will have the sum of the frequencies of the 2 previous node as its
	 * frequency, and the two others will be deleted, until only one node is left, the root
	 * @param nodes An Arraylist of nodes
	 * @return The root of the tree we just made
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
	
	/**
	 * Find the node with the lowest frequency
	 * @param nodes An array list of nodes
	 * @param nodeUnwanted The node we don't want to compare to
	 * @return the node with the lowest frequency among all the nodes
	 */
	private Node findMin(ArrayList<Node> nodes, Node nodeUnwanted){
		Node min = nodes.get(0) != nodeUnwanted ? nodes.get(0) : nodes.get(1);
		for (Node node : nodes) {
			if(node != nodeUnwanted)
				min = min.getMin(node);
		}
		return min;
	}
	
	/**
	 * Depth first search through the tree to fill the binary code of the leaves of the tree
	 * The left son will have the code 0 and the right son, 1. Recursive method.
	 * @param root The root of the tree
	 * @param codeBin The binary code that we will concatenate every time we call the method
	 */
	private void dfSearch(Node root, String codeBin){
		if(root.isLeaf()){
			root.setBinCode(codeBin);
//			System.out.println(root.getChar() + " : " + codeBin);
		}
		else{
			dfSearch(root.getFilsG(), codeBin + "0");
			dfSearch(root.getFilsD(), codeBin + "1");			
		}
	}
	
	/**
	 * Create a file from the freqFile filled with the frequencies of each 
	 * characters
	 */
	private void createFreqFile(){
		try {
			File file = new File("Data/freqFile.dat");
			PrintWriter writer = new PrintWriter(file, "ISO-8859-1");
			writer.write(freqFile);
			writer.close();			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Create a string of characters for each byte on the binary chain passed in parameter
	 * And put this bytes chain into a file named compressed.txt
	 * @param binCode A chain of 0 and 1
	 */
	private void bitToByte(String binCode){
		String byteString = "", res = "";
		for (int i = 0; i < binCode.length(); i++) {
			byteString += binCode.charAt(i);
			if(byteString.length()==8){
				res +=(char)Integer.parseInt(byteString, 2);
//				System.out.println("ICI : "+Integer.parseInt(byteString, 2) + " " + (char)Integer.parseInt(byteString, 2));
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
//		System.out.println(res);
	}
	
	
	/**
	 * rebuild the text from the binary code and the nodes. While the binary chain isn't
	 * empty, we iterate through every characters and test if their binary code fits with
	 * the beginning of the full binary code. If so, we remove the character binary chain
	 * from the full binary chain and we add the character itself to a variable that will
	 * be the full text. Also, since we're removing useless 0s at the end of the text.
	 * Create a file with the full decoded text at Data/Decompressed.txt
	 * @param nodes An arrayList of nodes containing characters and their binary code
	 * @param binCode The binary code of the entire compressed text
	 */
	private void reBuildText(ArrayList<Node> nodes, String binCode){
		String fullText = "";
		Node mostFrequent = getMostFrequentChar(nodes);
		int freqCount = 0;
		while(binCode.length() > 0){
			for (Node node : nodes) {
				int size = node.getBinCode().length();
				if(size>binCode.length())
					continue;				
				String reducedBinCode = binCode.substring(0, size);
				if(node.getBinCode().equals(reducedBinCode)){
					if(node == mostFrequent){
						if(freqCount == mostFrequent.getFreq()){
							binCode = "";
							break;
						}
						freqCount++;
					}
					fullText += node.getChar();
					binCode = binCode.substring(size);
					break;
				}
			}	
		}
		try {
			File file = new File("Data/Decompressed.txt");
			PrintWriter writer = new PrintWriter(file, "ISO-8859-1");
			writer.write(fullText);
			writer.close();			
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	/**
	 * Find the most frequent characters among the nodes
	 * @param nodes An array of every nodes
	 * @return The most frequent node
	 */
	private Node getMostFrequentChar(ArrayList<Node> nodes) {
		Node mostFreq = nodes.get(0);
		for (Node node : nodes) {
			if(node.getFreq() > mostFreq.getFreq())
				mostFreq = node;
		}
		return mostFreq;
	}

}
