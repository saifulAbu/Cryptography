/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma;

import cryptography.CharacterHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Saif
 */
public class Enigma {
    String enigmaWiringFile = "enigma.wiring";
    String generalSettingFile = "general.setting";
    TwoWiredComponent plugBoard;
    Scrambler[] scrambler;
    Reflector reflector;
    
    Scrambler scrambler0;
    Scrambler scrambler1;
    Scrambler scrambler2;

    public Enigma() throws IOException {
        loadSettingAndBuild();
    }
    
    
    
    void loadSettingAndBuild() throws IOException{
        Path wirePath = Paths.get(enigmaWiringFile);
        Path genSettingPath = Paths.get(generalSettingFile);
        
        scrambler = new Scrambler[3];
        //load scrambler and reflector setting from file
        List<String> file = Files.readAllLines(wirePath);
        for(int i = 0; i < 3; i++){
            scrambler[i] = new Scrambler(file.get(i));
        }//end for
        reflector = new Reflector(file.get(3));
        
        //get plugboard settings from file
        file = Files.readAllLines(genSettingPath);
        plugBoard = new TwoWiredComponent(file.get(0));
        
        //put plugboards in proper positon
        Scanner sc = new Scanner(file.get(1));
        scrambler0 = scrambler[sc.nextInt()];
        scrambler1 = scrambler[sc.nextInt()];
        scrambler2 = scrambler[sc.nextInt()];
        
        sc = new Scanner(file.get(2));
        //rotate the scramblers specified number of times
        for(int i = 0; i < 3; i++){
            int numRot = sc.nextInt();
            for(int rot = 0; rot < numRot; rot++){
                scrambler[i].rotate();
            }//end for
        }//end for
    }//end of method
    
    String encipher(String plainText){
        char text[] = plainText.toCharArray();
        String cipherText = "";
        for(int i = 0; i < text.length; i++){
            //input signal
            int signal = CharacterHelper.getIndexForChar(text[i]);
            //pass through enigma components
            signal = plugBoard.getFrontToBack(signal);
            //throug 3 scramblers
            signal = scrambler0.getFrontToBack(signal);
            signal = scrambler1.getFrontToBack(signal);
            signal = scrambler2.getFrontToBack(signal);
            //through the reflector
            signal = reflector.getFrontToBack(signal);
            //back through 3 scramblers
            signal = scrambler2.getBackToFront(signal);
            signal = scrambler1.getBackToFront(signal);
            signal = scrambler0.getBackToFront(signal);
            //back through the plugboard
            signal = plugBoard.getBackToFront(signal);
            
            char decrypted = CharacterHelper.getCharacterWithIndex(signal);
            cipherText += decrypted;
            
            //rotate scramblers
            scrambler0.rotate();
            //System.out.print(i+ " R0 ");
            if(scrambler0.completedOneCycle()){
                //System.out.print(" R1 ");
                scrambler1.rotate();
                if(scrambler1.completedOneCycle()){
                    //System.out.print(" R3 ");
                    scrambler2.rotate();
                }//end if
            }//end if
            //System.out.println();
        }//end for
        return cipherText;
    }//end of mehtod
    
    public String decipher(String cipherText){
        return encipher(cipherText);
    }//end of method
    
    public static void main(String args[]) throws IOException{
        //String plainText = "HELLOWORLDHOWAREYOUIAMFINESAIFHEREWHOAREYOUWHOAREYOUWHOAREYOU";
        //GREETING FROM SAIF TO THE WORLD
        String plainText = "SICBIYQZDDNFXRPOXUPPZKTRDNGWZCHLYUPRRSFAZJZIAPKUETZPDSOPHUWLX";
        Enigma enigma = new Enigma();
        String cipherText = enigma.encipher(plainText);
        System.out.println(cipherText);
    }//end of method
}//end of method


abstract class Component{
    int[] wiring;
    abstract int getFrontToBack(int i);
    //abstract int getBackToFront(int i);
}//end of class

class Reflector extends Component{

    @Override
    int getFrontToBack(int i) {
        return wiring[i];
    }

    Reflector(String setting){
        wiring = new int[CharacterHelper.ALPAHBET_SIZE];
        Scanner scaner= new Scanner(setting);
        
        for(int i = 0; i < wiring.length; i++){
            wiring[i] = scaner.nextInt();
        }//end for
    }//end of method
}//end of class

class TwoWiredComponent extends Reflector{
    int[] backWiring;


    //@Override
    int getBackToFront(int i) {
        return backWiring[i];
    }

    public TwoWiredComponent(String setting) {
        super(setting);
        backWiring = new int[CharacterHelper.ALPAHBET_SIZE];
        
        Scanner scaner= new Scanner(setting);
        for(int i = 0; i < wiring.length; i++){
            backWiring[scaner.nextInt()] = i;
        }//end for
    }//end of constructor
}//end of class

class Scrambler extends TwoWiredComponent{
    int numRotation = 0;
    
    void rotate(){
        numRotation = (numRotation + 1) % CharacterHelper.ALPAHBET_SIZE;
        int first = wiring[0];
        for(int i = 0; i < wiring.length - 1; i++){
            wiring[i] = (CharacterHelper.ALPAHBET_SIZE + wiring[i+1] - 1) % CharacterHelper.ALPAHBET_SIZE;
        }
        wiring[wiring.length - 1] = (CharacterHelper.ALPAHBET_SIZE + first - 1) % CharacterHelper.ALPAHBET_SIZE;
        
        //fix backwiring
        for(int i = 0; i < backWiring.length; i++){
            backWiring[wiring[i]] = i;
        }//end for
    }//end of method
    
    boolean completedOneCycle(){
        if(numRotation == 0)
            return true;
        return false;
    }//end of method
    
    
    public Scrambler(String setting) {
        super(setting);
    }
    
    
}//end of class