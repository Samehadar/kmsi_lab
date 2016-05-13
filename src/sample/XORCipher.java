package sample;

import java.util.List;

/**
 * Created by Vitaly on 20.03.2016.
 */
public class XORCipher {

    private String decryptedText;
    private String encryptedText;
    private List<Short> gammaSequence;
    private Short key;

    public XORCipher(){
        this.encryptedText = "";
        this.decryptedText = "";
        askGammaSequence();
        askKey();
    }

    public XORCipher(String decryptedText){
        this.encryptedText = "";
        this.decryptedText = decryptedText;
        askGammaSequence();
        askKey();
    }

    public String getEncryptedText(){ return encryptedText; }

    public String getDecryptedText() { return decryptedText; }

    public void setEncryptedText(String encryptedText){
        this.encryptedText = encryptedText;
    }

    public void setDecryptedText(String decryptedText){
        this.decryptedText = decryptedText;
    }

    /**
     * Запросить гамма последовательность у Generator
     * Если последовательность пуста - то исключение
     * */
    public void askGammaSequence() throws IllegalArgumentException{
        gammaSequence = Generator.generateNumbers;
        if (gammaSequence == null) throw new IllegalArgumentException("GammaSequence is null");
    }

    public void askKey()throws IllegalArgumentException{
        key = Generator.startReg;
        if (key == 0) throw new IllegalArgumentException("Key equals 0");
    }

    public void decryption(){
        StringBuilder result = new StringBuilder();
        char[] enc = encryptedText.toCharArray();
        for (int i = 0; i < enc.length; i++) {
            result.append((char)((enc[i]) ^ gammaSequence.get(i%enc.length)));
        }
        decryptedText = result.toString();
    }

    public void encryption(){
        StringBuilder result = new StringBuilder();
        char[] dec = decryptedText.toCharArray();
        for (int i = 0; i < dec.length; i++) {
            result.append((char)((dec[i]) ^ gammaSequence.get(i%dec.length)));
        }
        encryptedText = result.toString();
    }


}
