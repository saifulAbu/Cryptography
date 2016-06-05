# Cryptography
different cryptography implementation

#using the Enigma cipher

Enigma machines have the following main components

1. plugboard
2. scrambler 1, 2 and 3
3. reflector

Enigma supports only 26 upper case english alphabet and nothing else which means no space, no numeric or punctuation symbols.
The wiring of the scramblers and reflectors are set by factory and cannot be re-configured. The wiring of the plugboard can be modified by 
the operator leading to 26! combinations. The more about the working of Enigma can be found in the excellent book "The Code Book" by Simon 
Singh. 

To make a Enigma Machine, load the project using Netbeans IDE. Open the Enigma package, and run the GenerateEnigmaSetting.java file. It will
create the file enigma.wiring file. This file contains the wiring information of the scramblers and the reflector. The file contains four
lines. 

Open the the general.setting file. in the first line enter the configuration of the plugboard. The second line, specifiy which scrambler 
goes which position. For example, 2 1 0 means in the 1st position put the 3rd scrambler, second position put the second one and in the third 
position put the first scrambler. The 3rd line specify how many rotations you need for each of the scramblers. 4 3 21 would mean rotate the first scrambler 4 times, the second 
one 3 times and the third one for 21 times. This file is the key your key. Share this file securely with the people you want to communicate.

Now you are all set with the enigma setting. Open the Enigma.Java file. take a look at the main file. follow the example to cipher and 
decipher your texts. 

Good luck!
