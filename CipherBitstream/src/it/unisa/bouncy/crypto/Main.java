/*
 Encrypt and decrypt using the DES private key algorithm
*/
package it.unisa.bouncy.crypto;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.security.*;
import java.sql.DriverManager;

import javax.crypto.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;







public class Main {	
	static String file_to_encrypt;
	static KeyGenerator keyGen = null;
	static Cipher cipher;
	static Key key;

	
    public static void main (String[] args) throws Exception {
    	Crypto des = new Crypto(0);
    	
    	
    	JFrame frame = new JFrame();    	
    	
    	JPanel top = new JPanel();
    	top.setLayout(new GridLayout(2,3));
    	top.setSize(100,50);
    	JTextField choice_field = new JTextField("Select a file");
    	JLabel key_label = new JLabel("Insert key into text field");
    	JTextField key_value = new JTextField();
    	JButton enter_key = new JButton("Enter");
        choice_field.setPreferredSize( new Dimension( 400, 30 ) );
        key_label.setPreferredSize( new Dimension( 200, 10 ) );
        key_value.setPreferredSize( new Dimension( 50, 10 ) );
        enter_key.setPreferredSize(new Dimension(100,10));
    	top.add(new JPanel());
    	top.add(choice_field);
    	top.add(new JPanel());
    	top.add(key_label);
    	top.add(key_value);
    	top.add(enter_key);

    	JPanel center = new JPanel(new FlowLayout());
    	JButton butt_encrypt = new JButton("Encrypt");
    	butt_encrypt.setPreferredSize( new Dimension(100,30));
    	JButton butt_decrypt = new JButton("Decrypt");
    	butt_decrypt.setPreferredSize( new Dimension(100,30));
    	center.add(butt_encrypt);
    	center.add(butt_decrypt);
    	
    	frame.setLayout(new BorderLayout());
    	frame.setTitle("Encrypter/Decrypter bitstream tool");
    	frame.add(top,BorderLayout.NORTH);
    	frame.add(center,BorderLayout.SOUTH);
    	frame.setSize(400, 300);
    	

        frame.setVisible(true);
    	
    	
        choice_field.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            	JFileChooser choice = new JFileChooser();
		        int option = choice.showOpenDialog(frame);
		        File choosen = choice.getSelectedFile();
		        choice_field.setText(choosen.getName());
				JOptionPane.showMessageDialog(null,"Input file obtained!");
		        try {
					file_to_encrypt = choosen.getCanonicalPath();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        enter_key.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent event) {
        		des.setKey(Integer.parseInt(key_value.getText()));
        		// Get a DES private key
		        System.out.println( "\nStart generating DES key" );
		        try {
					keyGen = KeyGenerator.getInstance("DES");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
		        // If you do not initialize the KeyGenerator, each provider supply a default initialization.
		        keyGen.init(des.getKey());
		        key = keyGen.generateKey();
		        System.out.println( "Finish generating DES key" );
		        // Creates the DES Cipher object (specifying the algorithm, mode, and padding).
		        try {
					cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
				} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null,"Key entered successfully");
			}
        });
        
        
        butt_encrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
						        System.out.println( "\nStart encryption" );
		        // Initializes the Cipher object.
		        try {
					cipher.init(Cipher.ENCRYPT_MODE, key);
				} catch (InvalidKeyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        // Encrypt the plaintext using the public key
		        byte[] cipherText = null;
				try {
					cipherText = cipher.doFinal(des.readFileAsByte(file_to_encrypt));
				} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        //Bitstream cifrato
		        try {
					des.writeBitstreamToFile(cipherText, "output_encrypted.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null,"Encryption completed and saved into output_encrypted.txt");
		        System.out.println( "Finish encryption:");
			}

		});
        
        
        
        butt_decrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println( "\nStart decryption" );
		        // Initializes the Cipher object.
		        try {
					cipher.init(Cipher.DECRYPT_MODE, key);
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        // Decrypt the ciphertext using the same key
		        byte[] newPlainText = null;
				try {
					newPlainText = cipher.doFinal(des.readFileAsByte("output_encrypted.txt"));
				} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        //Bitstream decifrato
		        try {
					des.writeBitstreamToFile(newPlainText, "output_decrypted.txt");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(null,"Decryption completed and saved into output_decrypted.txt");
		        System.out.println( "Finish decryption");
			}

		});
    	
    	
    }
}