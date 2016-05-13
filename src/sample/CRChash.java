package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vitaly on 10.04.2016.
 */
public class CRChash {
    List<Byte> message;
    List<Byte> parentFunc;
    int lengthParentFunc;
    public Map<String, Integer> counts;

    public CRChash(byte[] messageBits){
        message = new ArrayList<>();
        for (int i = 0; i < messageBits.length; i++) {
            message.add(messageBits[i]);
        }
    }

    public void setMessage(byte[] messageBits){
        message = new ArrayList<>();
        for (int i = 0; i < messageBits.length; i++) {
            message.add(messageBits[i]);
        }
    }
    public void setMessage(List<Byte> messageBits){
        message = new ArrayList<>();
        for (Byte b : messageBits)
            message.add(b);
    }
    public void setMessage(String messageBits){
        char[] set = messageBits.toCharArray();
        message = new ArrayList<>();
        for (int i = 0; i < set.length; i++) {
            if (set[i] == '0')
                message.add((byte)0);
            else message.add((byte)1);
        }
    }

    public void setParentFunc(List<Byte> parentFunc){
        this.parentFunc = parentFunc;
        lengthParentFunc = parentFunc.size();
    }
    public void setParentFunc(String parentFunc){
        char[] set = parentFunc.toCharArray();
        this.parentFunc = new ArrayList<>();
        for (int i = 0; i < set.length; i++) {
            if (set[i] == '0')
                this.parentFunc.add((byte)0);
            else this.parentFunc.add((byte)1);
        }
        lengthParentFunc = parentFunc.length();
    }

    public Byte getBit(){
        Byte x = message.get(0);
        message.remove(0);
        return x;
    }

    public void multiplyMessage(int lengthParentPolynomial){
        for (int i = 0; i < lengthParentPolynomial; i++) {
            message.add((byte)0);
        }
    }

    public List<Byte> divideMessage(){
        List<Byte> rest = new ArrayList<>();
        for (int i = 0; i < lengthParentFunc; i++) {
            rest.add((byte)(message.get(0) ^ parentFunc.get(i)));
            message.remove(0);
        }
        return rest;
    }

    public List<Byte> divideMessage(List<Byte> oldRest){
        int key = lengthParentFunc - 1;
        while(true){
            if (key >= 0) {
                if (oldRest.get(0) == 0)
                    oldRest.remove(0);
                else break;
            } else break;
            key--;
        }


        int x = oldRest.size();
        for (int i = 0; i < lengthParentFunc - x; i++) {

            //костыль на возвращение результата
            if (message.size() == 0)
                return oldRest;


            if (oldRest.size() == 0 && message.get(0) == 0) {
                message.remove(0);
                i--;
            }
            else {
                oldRest.add(message.get(0));
                message.remove(0);
            }
        }


        List<Byte> rest = new ArrayList<>();
        for (int i = 0; i < lengthParentFunc; i++) {
            rest.add((byte)(oldRest.get(0) ^ parentFunc.get(i)));
            oldRest.remove(0);
        }
        return rest;
    }

    public String getCRC(){
        multiplyMessage(parentFunc.size());
        List<Byte> rest = divideMessage();

        while(true){
            rest = divideMessage(rest);
            if (rest.size() < lengthParentFunc)
                break;
        }
        StringBuilder result = new StringBuilder();
        for (Byte b : rest)
            result.append(b);
        for (Byte b : message)
            result.append(b);

        return result.toString();
    }

    public Map<String, String> getCollision(){
        Map<String, String> doc = new HashMap<>();
        counts = new HashMap<>();
        byte[] mas = new byte[8];
        char[] chars;
        for (int i = 0; i < 256; i++) {
            StringBuilder s = new StringBuilder();
            int x = i;
            int y = -1;
            while ( x != 0) {
                y = x % 2;
                x = x / 2;
                s.append(y);
            }
            StringBuilder str = s.reverse();
            int length = str.length();
            for (int j = 0; j < 8 - length; j++) {
                str.append("0");
            }
            s = str.reverse();
            chars = s.toString().toCharArray();
            for (int j = 0; j < chars.length; j++) {
                mas[j] = (byte)(chars[j] - 48);
            }
            setMessage(mas);
            doc.put(s.toString(), getCRC());
            if (counts.containsKey(s.toString()))
                counts.put(s.toString(), counts.get(s.toString()) + 1);
            else counts.put(s.toString(), 1);
        }
        return doc;
    }
}
