/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptography;

public class CharacterHelper{
   public static int ALPAHBET_SIZE = 26;
   public static int getIndexForChar(char ch){
        if(!Character.isAlphabetic(ch)){
            throw new RuntimeException("Character shoudl be alpahbedic");
        }//end of method
        ch = Character.toUpperCase(ch);
        return ch - 'A';
    }//end of method
   
   public static char getCharacterWithIndex(int index){
       if(index < 0 || index >= ALPAHBET_SIZE){
           throw new RuntimeException("Index of character out of bound");
       }//end if
       return (char) ('A' + index);
   }//end of method
   
   public static void main(String args[]){
       System.out.println(getCharacterWithIndex(26));
   }//end of method
}//end of class