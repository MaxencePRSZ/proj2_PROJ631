package proj2_PROJ631;
import java.io.*;

public class ParseFreq {
	
	private static int[] textFileToArray(String url) throws IOException{
		FileReader file = new FileReader(url); 
	    int i; 
	    int[] unicodeTable = new int[255];
	    while ((i=file.read()) != -1) 
	    {
			unicodeTable[i]++;
		}
		for (int j = 0; j < unicodeTable.length; j++) {
			System.out.println(j + " : " + unicodeTable[j]);
		}
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
}
