package proj2_PROJ631;
import java.io.*;
import java.util.ArrayList;

public class ParseFreq {
	
	public static String fullText = "";
	private int[] unicodeTable = new int[255];
	
	public int[] getUnicodeTable() {
		return unicodeTable;
	}

	public String textFileToArray(String url){
		try {
			String freqFile = "";
			FileReader file = new FileReader(url); 
			int i; 
			while ((i=file.read()) != -1) 
			{
				unicodeTable[i]++;
				fullText+=(char)i;
			}
			for (int j = 0; j < unicodeTable.length; j++) {
				if(unicodeTable[j] != 0){
					freqFile += (char)j + " " + unicodeTable[j] + "\n";
					System.out.println(j + " : " + unicodeTable[j]);
				}
			}
			file.close();
			return freqFile;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	public String createBinCode(ArrayList<Node> nodes) {
		String binCode = "";
		for (int i = 0; i < fullText.length(); i++) {
			char letter = fullText.charAt(i);
			for (Node node : nodes) {
				if(letter == node.getChar())
					binCode += node.getBinCode();
			}
		}
		return normalizeBinCode(binCode);
	}
	
	private String normalizeBinCode(String binCode){
		int diff = 8-binCode.length()%8;
		for (int i = 0; i < diff; i++) {
			binCode+="0";	
		}
		return binCode;
	}
}
