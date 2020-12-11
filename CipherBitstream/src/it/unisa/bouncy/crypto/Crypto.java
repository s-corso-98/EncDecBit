package it.unisa.bouncy.crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;

public class Crypto {
	int key;
	
	public Crypto(int key) {
		this.key = key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public int getKey() {
		return this.key;
	}
	
	public static boolean convertCharToBoolean(char input) {
	    return Character.toLowerCase(input) == '1' ? true : false;
	}
	
	public byte[] readFileAsByte(String filepath) throws IOException {
		String everything;
        File f = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(f));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } finally {
            br.close();
        }
        char[] everything_char = everything.toCharArray();
        BitSet bits= new BitSet(everything_char.length);
        for(int i = 0; i < everything_char.length; i++)
        {
        	bits.set(i,convertCharToBoolean(everything_char[i]));
        }
        byte[] help = bits.toByteArray();
        return help;
	}
	
	public void writeBitstreamToFile(byte[] bitstream, String filename) throws IOException {
		 BitSet bits = new BitSet();
		 bits = BitSet.valueOf(bitstream);
		 
	     char[] bitstream_output = new char[bits.length()];
	        
	     for(int i=0;i<bits.length();i++)
	     {
	    	 if(bits.get(i) == false){
	    		 bitstream_output[i] = '0';
	        }
	    	 else
	    		 bitstream_output[i] = '1';
	     }   
	     BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
	     writer.write(bitstream_output);
	     writer.close();
	}
	
	
}
