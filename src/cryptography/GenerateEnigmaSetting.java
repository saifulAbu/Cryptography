/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptography;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *The enigma has 3 scramblers. 1 reflector and 1 connector.
 * 
 */
public class GenerateEnigmaSetting {
    public static String settingFileName = "enigma.wiring";
    
    public void makeEnigmaSetting(){
        Path path = Paths.get(settingFileName);
        try {
            //create a file
            Files.deleteIfExists(Paths.get(settingFileName));
            Files.createFile(Paths.get(settingFileName));
        } catch (IOException ex) {
            Logger.getLogger(GenerateEnigmaSetting.class.getName()).log(Level.SEVERE, null, ex);
        }//end of try catch
        String setting = "";
        //write scrambler 1, 2, 3
        for(int i = 0; i < 3; i++){
            String str = convertArrToString(getScramblerOrConnectorWiring());
            setting += str + "\n";
        }//end for
        //write reflector setting
        setting += convertArrToString(getReflectorWiring()) + "\n";
        
        try {
            //write to file and close
            System.out.println("Writing Enigma Settings to File");
            Files.write(path, setting.getBytes());
            //close file
        } catch (IOException ex) {
            Logger.getLogger(GenerateEnigmaSetting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//end of method
    public static void main(String args[]){
        GenerateEnigmaSetting genSet = new GenerateEnigmaSetting();
        genSet.makeEnigmaSetting();
        
    }//end of method
    
    static int[] getScramblerOrConnectorWiring(){
        int[] wiring = new int[CharacterHelper.ALPAHBET_SIZE];
        int[] reverseWiring = new int[CharacterHelper.ALPAHBET_SIZE];
        //initiazlize with -1 
        initializeWithNeg1(wiring);
        initializeWithNeg1(reverseWiring);
        
        Random rand = new Random();
        for(int i = 0; i < CharacterHelper.ALPAHBET_SIZE; i++){
            int r = rand.nextInt(CharacterHelper.ALPAHBET_SIZE);
            if(reverseWiring[r] != -1){
                i--;
                continue;
            }//end if
            wiring[i] = r;
            reverseWiring[r] = i;
        }//end for
        return wiring;
    }//end of method
    
    int[] getReflectorWiring(){
        int[] wiring = new int[CharacterHelper.ALPAHBET_SIZE];
        
        //initiazlize with -1 
        initializeWithNeg1(wiring);
        
        Random rand = new Random();
        for(int i = 0; i < CharacterHelper.ALPAHBET_SIZE; i++){
            int r = rand.nextInt(CharacterHelper.ALPAHBET_SIZE);
            if(wiring[i] >= 0){
                continue;
            }//end if
            if(wiring[r] != -1){
                i--;
                continue;
            }//end if
            wiring[i] = r;
            wiring[r] = i;
        }//end for
        return wiring;
    }//end of method
    
    static void initializeWithNeg1(int[] arr){
        for(int i = 0; i < CharacterHelper.ALPAHBET_SIZE; i++){
            arr[i] = -1;
        }//end for
    }//end of method
    
    String convertArrToString(int[] arr){
        String str = "";
        for(int i = 0; i < CharacterHelper.ALPAHBET_SIZE; i++){
            str += arr[i] + " ";
        }//end for
        return str;
    }//end of mehod
}//end of class
