package proj2_PROJ631;

public class Main {

	public static void main(String[] args) {
		Huffman huffComp = new Huffman();
		huffComp.compression("./Data/alice29.txt");
		huffComp.decompression("./Data/freqFile.dat", "./Data/Compressed.txt");
	}

}
