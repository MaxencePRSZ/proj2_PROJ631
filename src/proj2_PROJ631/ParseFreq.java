package proj2_PROJ631;
import java.io.*;

public class ParseFreq {
	int[] unicodeTable = new int[255];
	
	public ParseFreq() {
		super();
	}


	private void fileToStr(String url) throws IOException{
		FileReader file = new FileReader(url); 
	    int i; 
	    while ((i=file.read()) != -1) 
	    {
	    	System.out.println(i);
			unicodeTable[i]++;
		}
		for (int j = 0; j < unicodeTable.length; j++) {
			System.out.println(j + " : " + unicodeTable[j]);
		}
	}

	public void test(String url){
		try {
			this.fileToStr(url);			
		} catch (Exception e) {
			System.out.println(e);
		}
	}


}
