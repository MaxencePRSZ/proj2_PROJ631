package proj2_PROJ631;
import java.io.*;
import java.util.ArrayList;

public class ParseFreq {
	
	public static String fullText = "";
	
	private static int[] textFileToArray(String url) throws IOException{
		FileReader file = new FileReader(url); 
	    int i; 
	    int[] unicodeTable = new int[255];
	    while ((i=file.read()) != -1) 
	    {
			unicodeTable[i]++;
			fullText+=(char)i;
		}
		for (int j = 0; j < unicodeTable.length; j++) {
			if(unicodeTable[j] != 0)
				System.out.println(j + " : " + unicodeTable[j]);
		}
	    file.close();
		return unicodeTable;
	}

	public static int[] fillFreq(String url){
		try {
			return textFileToArray(url);			
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public static String createBinCode(ArrayList<Node> nodes) {
		String binCode = "";
		for (int i = 0; i < fullText.length(); i++) {
			char letter = fullText.charAt(i);
			for (Node node : nodes) {
				if(letter == node.getChar()){
					binCode += node.getBinCode();
				}
			}
		}
		return binCode;
	}
}
