package proj2_PROJ631;

import java.util.ArrayList;

public class Huffman {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] freqs = ParseFreq.fillFreq("data/montexte.txt");
		ArrayList<Node> nodes = fillNodes(freqs);
		System.out.println(findMin(nodes, nodes.get(0)).getUnicode());
		
	}
	
	//Insert des nodes dans un arraylist dans l'ordre : En fonction des fréquences puis de l'alphabet
	private static ArrayList<Node> fillNodes(int[] freq){
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
	
	private static int getMinFromFreqs(int[] freqs){
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
	private static Node buildTree(ArrayList<Node> nodes){
		while(nodes.size() > 1){
			Node min1 = findMin(nodes, null);
			Node min2 = findMin(nodes, min1);
			Node sup = new Node(256, min1.getFreq()+min2.getFreq(), min1, min2); 
			nodes.add(0, sup);
			nodes.remove(min1);
			nodes.remove(min2);
		}
		return nodes.get(0); //Retourne la racine de l'arbre
	}
	
	private static Node findMin(ArrayList<Node> nodes, Node nodeUnwanted){
		Node min = nodes.get(0) != nodeUnwanted ? nodes.get(0) : nodes.get(1);
		for (Node node : nodes) {
			if(node != nodeUnwanted)
				min = min.getMin(node);
		}
		return min;
	}
	
	//TODO Parcourir l'arbre 

}
