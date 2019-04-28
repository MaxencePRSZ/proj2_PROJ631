package proj2_PROJ631;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ParseFreq {
	
	public static String fullText = "";
	private int[] unicodeTable = new int[255];
	
	public int[] getUnicodeTable() {
		return unicodeTable;
	}

	/**
	 * Read the text, count the characters and place the frequencies into an array
	 * of 255 integers. Increments the character's ASCII code every time we find it.
	 * Also create a frequencies file based on the array
	 * @param url The URL of the text we want to compress
	 */
	public void textFileToArray(String url){
		try {
			FileReader file = new FileReader(url); 
			int i; 
			while ((i=file.read()) != -1) 
			{
				unicodeTable[i]++;
				fullText+=(char)i;
			}
			for (int j = 0; j < unicodeTable.length; j++) {
				if(unicodeTable[j] != 0)
					Huffman.freqFile+= (char)j + " " + unicodeTable[j] + "\n";
//				System.out.println(j + " " + unicodeTable[j]);
			}
			file.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Create a binary chain. Every time we find a character in the text saved in 
	 * the variable @fullText, we look at the node related to this character and 
	 * add its binCode to the binary chain. We then normalize the binary chain so 
	 * the chain will be divisible by eight. 
	 * @param nodes The array of all nodes
	 * @return The binary chain
	 */
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
	
	/**
	 * Add 0s to the binary chain to make the chain divisible by eight.
	 * @param binCode The binCode we want to extend
	 * @return The extended binCode
	 */
	private String normalizeBinCode(String binCode){
		int diff = 8-binCode.length()%8;
		for (int i = 0; i < diff; i++) {
			binCode+="0";	
		}
		return binCode;
	}
	
	/**
	 * Read the frequencies file and insert the data read into a 255 integer array
	 * which represents characters from the text
	 * @param URL The URL of the frequencies file
	 */
	public void freqFileToArray(String URL){
		try {
			FileReader file = new FileReader(URL); 
			int i;
			fullText = "";
			while ((i=file.read()) != -1) 
			{
				fullText += (char)i;
			}
			for (int j = 0; j < fullText.length(); j++) {
				if(fullText.charAt(j) == ' ' && fullText.charAt(j+1) != ' '){
					char charac = fullText.charAt(j-1);
					j++;
					String freq = "";
					while(fullText.charAt(j) != '\n'){
						freq += fullText.charAt(j);
						j++;
						if(j == fullText.length()-1)
							break;
					}
					unicodeTable[(int)charac] = Integer.parseInt(freq);
				}
			}
			file.close();
//			for (int j = 0; j < unicodeTable.length; j++) {
//				System.out.println(j + " " + unicodeTable[j] );
//			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Read every characters of the compressed file and convert them
	 * into bytes, then add them to a Binary chain which is returned.
	 * @param URL The URL of the compressed file
	 * @return The binary chain
	 */
	public String compressedToBinCode(String URL){
		try {
			File file = new File(URL);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(file), Charset.forName("ISO-8859-1")));
			int i;
			String binCode = "";
			while ((i=reader.read()) != -1) 
			{
				binCode += normalizeByte(Integer.toBinaryString(i));
			}
			reader.close();
			return binCode;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Normalize binary chains so it is coded on 8 bits.
	 * Basically, it adds 0 at the front the binary code 
	 * until it has 8 bits
	 * @param binCode The binary code to normalize as a string
	 * @return The normalized binary code as a string
	 */
	private String normalizeByte(String binCode){
		while(binCode.length() < 8)
			binCode = "0" + binCode;
		return binCode;
	}
}
