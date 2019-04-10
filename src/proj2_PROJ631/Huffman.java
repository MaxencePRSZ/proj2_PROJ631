package proj2_PROJ631;

import java.util.ArrayList;

public class Huffman {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int[] freqs = ParseFreq.fillFreq("data/montexte.txt");
		fillNodes(freqs);
		
	}
	
	private static void fillNodes(int[] freq){
		ArrayList<Node> Nodes = new ArrayList<Node>();
		
		for (int j = 0; j < freq.length; j++) {
			if(freq[j] != 0){
				Node node = new Node(j, freq[j], null, null);
				Nodes.add(node);
			}
		}
		System.out.println(Nodes);
	}

}
