package com.example.a345793.androidcryptography;


/*
Project: The following project is a cryptography project that has a user interface and implements the vinegere, caesar, and scytale cipher
By: Deyvik Bhan
date:3/15/2019


 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

// Imports required

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button encryptButton;
    Button decryptButton;
    RadioButton scytale;
    RadioButton vinegere;
    RadioButton caesar;
    String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    EditText input;
    EditText key;
    TextView output;
    String stringInput;
    String stringKey;
    String secondString;
    // Initialization of required variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        encryptButton = (Button) this.findViewById(R.id.Button1);
        encryptButton.setOnClickListener(this);
        decryptButton = (Button) this.findViewById(R.id.Button2);
        decryptButton.setOnClickListener(this);
        scytale = (RadioButton) this.findViewById(R.id.scytale);
        scytale.setOnClickListener(this);
        vinegere = (RadioButton) this.findViewById(R.id.vinegere);
        vinegere.setOnClickListener(this);
        caesar = (RadioButton) this.findViewById(R.id.caesar);
        caesar.setOnClickListener(this);
        // Sets button to vriable and adds on click listener
        input = (EditText) this.findViewById(R.id.input);
        key = (EditText) this.findViewById(R.id.key);
        output = (TextView) this.findViewById(R.id.output);
        // Initializes edit texts and textviews



    }

    @Override
    public void onClick(View v) {
        stringInput = input.getText().toString().toUpperCase();
        // Gets string of input
        stringKey = key.getText().toString();
        // Gets string of key
        String secondString = finalInput(stringInput);
        // Takes all numbers and spaces out of strings



        Button b = (Button) v;
        if (b.equals(encryptButton)) {
            if (scytale.isChecked()) {
                // If encrypt and scytale
                try {
                    output.setText("Output: "  + sEncrypt(secondString, stringKey));
                } catch (Exception E) {
                    Toast.makeText(this, "For the scytale encryption, make sure the key is an integer!", Toast.LENGTH_SHORT).show();


                }




            }

            else if (caesar.isChecked()) {
                // If encrypt and caesar
                try {
                    output.setText("Output: " + encrypt(secondString,stringKey));
                } catch (Exception E) {
                    Toast.makeText(this, "For the Caesar encryption, make sure the key is an integer between 0 and 26!", Toast.LENGTH_SHORT).show();


                }




            }
            else if (vinegere.isChecked()) {
                // If encrypt and vinegere
                try {
                    output.setText("Output: " + finalInput(vEncrypt(secondString, stringKey)));

                } catch(Exception E) {
                    Toast.makeText(this, "For the Vinegere encryption, make sure the key is an string!", Toast.LENGTH_SHORT).show();

                }



            }
        }
        if (b.equals(decryptButton)) {
            // If decrypt and scytale
            if (scytale.isChecked()) {
                try {
                    output.setText("Output: "  + sDecrypt(secondString, stringKey));

                } catch(Exception E) {
                    Toast.makeText(this, "For the scytale decryption, make sure the key is an integer!", Toast.LENGTH_SHORT).show();

                }

            }

            if (caesar.isChecked()) {
                // If decrypt and caesar
                try {
                    output.setText("Output: " + decrypt(secondString, stringKey));
                } catch(Exception E) {
                    Toast.makeText(this, "For the Caesar decryption, make sure the key is an integer between 0 and 16!", Toast.LENGTH_SHORT).show();

                }


            }
            if (vinegere.isChecked()) {
                // If decrypt and vinegere
                try {
                    output.setText("Output: " + vDecrypt(secondString, stringKey));
                } catch(Exception E) {
                    Toast.makeText(this, "For the Vinegere decryption, make sure the key is an string!", Toast.LENGTH_SHORT).show();

                }



            }

        }
    }

    // Function used to find numeric index of certain character
    public int findValue(char a) {
        if (Character.isLowerCase(a)) {
            // Loops through alphabet string to find index of character
            for (int i = 0; i < lowerCase.length(); i++) {
                if (lowerCase.charAt(i) == a) {
                    return i + 1;
                }
            }
        }
        if (Character.isUpperCase(a)) {
            // Loops through alphabet string to find index of character
            for (int i = 0; i < upperCase.length(); i++) {
                if (upperCase.charAt(i) == a) {
                    return i + 1;
                }
            }
            return a;
        }
        return a;
        // Returns numeric index of alphabet passed in
    }

    // Caesar encryption
    public String encrypt(String input, String key) {
        String output = "";
        int intKey = Integer.parseInt(key);
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int index = findValue(charArray[i]) - 1;
            if (charArray[i] != ' ') {
                if (index + intKey <= 25) {
                    // If character does not go over z
                    output += upperCase.charAt(index + intKey);

                } else if (index + intKey > 25) {
                    // If new character goes above z, go back to the start of the alphabet
                    output += upperCase.charAt((index + intKey) - 26);
                }
            } else {
                output += " ";

            }
        }
        return output;
        // Returns encrypted string using caesar cipher
    }

    // Decrypt for caesar, uses parameter input as input and uses string key as the key to decrypt the input
    public String decrypt(String input, String key) {
       String output = "";
        int intKey = Integer.parseInt(key);
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int index = findValue(charArray[i]);
            if (charArray[i] != ' ') {
                if (index - intKey > 0) {
                    output += upperCase.charAt((index - intKey) - 1);
                } else if (index - intKey <= 0) {
                    output += upperCase.charAt((index - intKey) + 25);
                }
            } else {
                output += " ";
            }
        }
        return output;
        // Returns decrypted string using caesar

    }
    // Uses the string input, and uses the key to form a string that will determine the amount of shift for each letter in the input for vinegere
    public StringBuilder replaced(String input, String key) {
        StringBuilder str = new StringBuilder();
        str.append(input);
        String index = "";
        String output = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                str.deleteCharAt(i);
                index += i + ",";

            }
        }
        int i = 0;
        while (i < str.length()) {
            int j = 0;
            for (j = 0; j < key.length(); j++) {
                if (i < str.length()) {
                    output += key.charAt(j);
                }
                i++;
            }
        }
        str.replace(0, str.length(), output);
        int t = 0;
        String[] stringArray = index.split(",");
        if (!stringArray[0].equals("")) {
            for (int i1 = 0; i1 < stringArray.length; i1++) {

                int string = Integer.parseInt(stringArray[i1]);

                str.insert(string + t, ' ');

                t++;
            }
        }
        return str;
        /*
        Returns string of repeated key used for vinegere,
        Example: String Hello my name is Deyvik
        Key: ab
        Output: ababa ba baba ba bababa


         */


    }
    // Encrypt for vinegere, uses parameter input as input and uses string key as the key to encrypt the input
    public String vEncrypt(String Input, String key) {
        String output = "";
        StringBuilder aI = new StringBuilder();
        aI.append(Input);
        StringBuilder vinegere = replaced(Input,key);
        for (int i = 0; i < aI.length(); i++) {
            if(aI.charAt(i) != ' ') {
                int newKey = findValue(vinegere.charAt(i))-1;
                String finalKey = Integer.toString(newKey);
                String enc = Character.toString(aI.charAt(i));
                output+=encrypt(enc,finalKey);
            } else {
                output+=" ";
            }

        }
        return output;
        // Returns encrypted string using vinegere cipher

    }

    // Decrypt for vinegere, uses parameter input as input and uses string key as the key to decrypt the input
    public String vDecrypt(String Input, String key) {
        String output = "";
        StringBuilder aI = new StringBuilder();
        aI.append(Input);
        StringBuilder vinegere = replaced(Input,key);
        for (int i = 0; i < aI.length(); i++) {
            if(aI.charAt(i) !=  ' ')  {
                int newKey = findValue(vinegere.charAt(i))-1;
                String finalKey = Integer.toString(newKey);
                String enc = Character.toString(aI.charAt(i));
                output+=decrypt(enc, finalKey);
            } else {
                output+=" ";
            }
        }

        return output;
        //Returns decrypted string from the vinegere cipher
    }
    // Encrypt for scytale, uses parameter input as input and uses string key as the key as the amount of rows to encrypt the input
    public String sEncrypt(String input, String key) {
        String output = "";
        int rows = Integer.parseInt(key);
        int collumns = input.length()/rows;
        int total = rows * collumns;
        int extra = input.length() % total;

        int k = 0;
        int l = 0;

        if(input.length()%total >= 1) {

            collumns +=1;
        }
        char[][] charArray = new char[rows][collumns];
        for (int i = 0; i < charArray.length; i++) {
            for (int j = 0; j < charArray[0].length; j++) {
                if(j != collumns -1) {
                    charArray[i][j] = input.charAt(l);
                    l++;
                } else if(k < extra && j == collumns -1) {
                    charArray[i][j] = input.charAt(l);
                    k ++;
                    l++;
                } else {
                    if(j == collumns - 1 && extra > 0) {
                        charArray[i][j] = '@';
                    } else {
                        charArray[i][j] = input.charAt(l);
                        l++;
                    }
                }
            }
        }
        for (int i = 0; i < charArray[0].length; i++) {
            for (int j = 0; j < charArray.length; j++) {
                output += charArray[j][i];
            }
        }

        return output;
        // Returns encrypted string from the vinegere cipher
    }


    //Function that removes everything but characters that are alphabets from the string
    public String finalInput(String input) {
        String output = "";
        for (int i = 0; i < input.length(); i ++) {
            for (int j = 0; j < upperCase.length(); j ++) {
                if(input.charAt(i) == upperCase.charAt(j)) {
                    output+=upperCase.charAt(j);
                }
            }
        }
        return output;
        // Returns edited string(Only characters in the alphabet
    }


    // Decrypt for scytale, uses parameter input as input and uses string key as the key as the amount of rows to decrypt the input
    public String sDecrypt(String input, String key) {
        String output = "";


        int rows = Integer.parseInt(key);
        int collumns = input.length()/rows;
        int total = rows * collumns;
        int extra = input.length() % total;

        int k = 0;
        int l = 0;

        if(input.length()%total > 0) {
            collumns +=1;
        }
        for (int i = 0;  i < rows*collumns; i ++) {
            if(i > input.length() - 1) {
                input+= " ";
            }
        }

        System.out.println(collumns);
        char[][] charArray = new char[rows][collumns];
        for (int i = 0; i < charArray[0].length; i++) {
            for (int j = 0; j < charArray.length; j++) {
                charArray[j][i] = input.charAt(l);
                l++;
            }
        }
        for (int i = 0; i < charArray.length; i++) {
            for (int j = 0; j < charArray[0].length; j++) {
                output += charArray[i][j];
            }
        }

        return output;
        //Return decrypted string
    }
}
