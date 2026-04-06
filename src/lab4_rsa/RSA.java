package sample;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Vitaly on 03.04.2016.
 */
public class RSA extends Cipher {
    List<String> openText = new ArrayList<>();
    List<String> closeText = new ArrayList<>();

    int p; //простое число
    int q; //простое число 2
    int n; //модуль
    int m; // (p-1)*(q-1)
    int d; //открытый ключ(ключ зашифрования)
    int e; //закрытый ключ(ключ расшифрования)

    public RSA(int p, int q) {
        if (gcd(p, q) != 1)
            throw new IllegalArgumentException("Числа p и q не взаимно простые");
        this.p = p;
        this.q = q;
        this.n = p * q;
        this.m = (p - 1) * (q - 1);
        this.d = 17;
        if (gcd(m, d) != 1)
            throw new IllegalArgumentException("Число D не взаимно просто с M");
        Tuple3<Integer, Integer, Integer> tuple = gcdExtended(m, d, new Tuple3<>(0, 0, 0));
        this.e = m + (tuple.y);
    }

    @Override
    public void encryption() {
        closeText = new ArrayList<>();
        for (String s : openText){
            closeText.add(exp(Integer.parseInt(s), e).toString());
        }
    }

    @Override
    public void decryption() {
        openText = new ArrayList<>();
        for (String s : closeText){
            openText.add(exp(Integer.parseInt(s), d).toString());
        }
    }

    //Вычисление e^x (mod n)
    private Integer exp(Integer e, Integer x){
            BigInteger result = new BigInteger(e.toString());
            result = result.modPow(new BigInteger(x.toString()), new BigInteger(n + ""));

            return result.intValue();

        /*NOOBS METHOD
        double result = 1;
        for (int i = 0; i < x; i++) {
            result *= e;
            result %= n;
        }
        return (int)result;
        */
    }

    //to work with another's strings
    // 1. delete "s = rusString(s);
    // 2. uncomment strings
    public void setOpenText(String s){
        openText = new ArrayList<>();
        s = rusString(s);
        //char[] set = s.toCharArray();
        StringBuilder str = new StringBuilder(s);
        //for (int i = 0; i < set.length; i++) {
        //    str.append((int)set[i]);
        //}
        while(true){
            if (str.length() < 5) {
                if (str.toString().equals(""))
                    break;
                Integer x = Integer.parseInt(str.toString());
                str.delete(0, str.length());
                openText.add(x.toString());
                break;
            } else {
                Integer x = Integer.parseInt(str.substring(0, 4));
                    str.delete(0, 4);
                openText.add(x.toString());
            }
        }
    }

    /**
     * Text must be see "blablaб,mtmt,ola,fkeo"
     */
    public void setCloseText(String s){
        closeText = new ArrayList<>();
        String[] mas = s.split(", ");
        for (String str : mas){
            closeText.add(str);
        }
    }

    public List<String> getOpenText(){ return openText; }
    public List<String> getCloseText() { return closeText; }

    public Object getKey() {
        return null;
    }

    public boolean setKey(Object key) {
        return false;
    }

    public int gcd(int a, int b) throws IllegalArgumentException {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    public Tuple3<Integer, Integer, Integer> gcdExtended(int a, int b, Tuple3<Integer, Integer, Integer> tuple) {
        if (a == 0) {
            return new Tuple3<Integer, Integer, Integer>(0, 1, b);
        }
        Tuple3<Integer, Integer, Integer> tuple1 = gcdExtended(b%a, a, new Tuple3<>(0, 0, 0));
        tuple.x = tuple1.y - (b / a) * tuple1.x;
        tuple.y = tuple1.x;
        tuple.d = tuple1.d;
        return tuple;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + ":\n" +
                "p=" + p + " q=" + q + "\nn=" + n + " m=" + m + "\nd=" + d + " e=" + e;
    }

    private String rusString(String str){
        str = str.toUpperCase();
        char[] mas = str.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] == ' ')
                result.append("99");
            else result.append((int)mas[i] - 1030);
        }
        return result.toString();
    }

    public static String toRusString(List<String> strings){
        StringBuilder str = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for (String s : strings){
            str.append(s);
        }
        while (true){
            if (str.toString().equals(""))
                break;
            Integer x = Integer.parseInt(str.substring(0, 2));
            if (x == 99)
                result.append(" ");
            else result.append((char)(x + 1030));
            str.delete(0, 2);
        }
        return result.toString();
    }

}