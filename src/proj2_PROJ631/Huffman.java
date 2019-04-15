package proj2_PROJ631;
import java.io.*;
import java.util.ArrayList;

public class Huffman {
	
	private static String freqFile = "";
	
	public void compression(String URL) {
		ParseFreq parseF = new ParseFreq();
		parseF.textFileToArray("C:/Users/maxen/Documents/Polytech/proj2_PROJ631/Data/montexte.txt");
		ArrayList<Node> nodes = fillNodes(parseF.getUnicodeTable());
		Node root = buildTree(nodes);
		dfSearch(root, "");
		createFreqFile();
		String binCode = parseF.createBinCode(nodes);
		System.out.println(bitToByte(binCode));
	}
	
	//Insert des nodes dans un arraylist dans l'ordre : En fonction des fréquences puis de l'alphabet
	private ArrayList<Node> fillNodes(int[] freq){
		ArrayList<Node> nodes = new ArrayList<Node>();
		int min = 0;
		//Parcourt le tableau de frequence et trouve le minimum dans l'ordre Ascii
		//Puis lui met la valeur 0, jusqu'à que toutes les valeurs soient passées à 0
		while((min = getMinFromFreqs(freq)) != -1){
			Node node = new Node(min, freq[min], null, null);
			freq[min] = 0;
			freqFile+= node.getChar() + ":" + node.getFreq() + "\n";
			nodes.add(node);
		}
		return nodes;
	}
	
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
		if(root.isLeaf())
			root.setBinCode(codeBin);
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
			PrintWriter writer = new PrintWriter(file, "UTF-8");
			writer.write(res);
			writer.close();			
		} catch (Exception e) {
			System.out.println(e);
		}
		return res;
	}

}
