package main.com.gmail.vitaly.lyutarevich.lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by vitaly on 03.10.15.
 */
public class Transposition_cipher {
    public static int count;
    public static void main(String[] args) throws IOException
    {

        int[] a = {4, 5, 1, 2, 3};
        Circuit cycle = new Circuit(a);
        for (int i = 0; i < cycle.getOp().length; i++)
            System.out.println(cycle.getOp()[i]);
        menu();
    }

    static String encryption(String str, Circuit cycle)
    {
        char[] _str = str.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < _str.length; i++)
        {
            int new_number = (i/cycle.getP().length)*cycle.getP().length + cycle.getP()[i % cycle.getP().length] - 1;
            result.append(_str[new_number]);
        }
        return result.toString();
    }

    static String decryption(String str, Circuit cycle)
    {
        char[] _str = str.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < _str.length; i++)
        {
            int new_number = cycle.getOp()[i%cycle.getOp().length]- 1 + (i/cycle.getOp().length) * cycle.getOp().length;
            result.append(_str[new_number]);
        }
        return result.toString();
    }

    public static String correct_String(int key, String str, Circuit cycle)
    {
        StringBuilder result = new StringBuilder(str);
        if (key == 0)
        {
            int cur_key = str.length() % cycle.getP().length;
            if (cur_key == 0)
            {
                count = 0;
                return result.toString();
            }
            if (cur_key != 0)
                for (int i = 0; i < cycle.getP().length - cur_key; i++)
                {
                    result.append('+');
                }
            count = cycle.getP().length - cur_key;

            return result.toString();
        }
        else
        {
            result.delete(result.length() - count, result.length());
            count = 0;
            return result.toString();
        }
    }

    public static void menu() throws IOException
    {
        while(true)
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите ваш текст: ");
            String text = buffer.readLine();

            System.out.println("Введите перестановку через запятую: ");
            String keys = buffer.readLine();
            String[] keys_mas = keys.split(",");
            int[] key = new int[keys_mas.length];
            for (int i = 0; i < key.length; i++)
                key[i] = Integer.parseInt(keys_mas[i]);
            Circuit cycle = new Circuit(key);

            //System.out.println(correct_String(0, text, cycle));
            System.out.println("Выберите нужное действие: \n" +
                    "1 - Шифровать текст.\n2 - Расшифровать текст.\n" +
                    "347 - Выход.");
            int a = Integer.parseInt(buffer.readLine());

            switch (a)
            {
                case 1:
                    text = correct_String(0, text, cycle);
                    String cipher_text = encryption(text, cycle);
                    System.out.println("Шифротекст: " + cipher_text);
                    break;
                case 2:
                    String open_text = decryption(text, cycle);
                    open_text = correct_String(1, open_text, cycle);
                    System.out.println("Результат дешифрования: " + open_text);
                    break;
                default:
                    return;
            }
        }
    }
}

class Circuit
{
    private int[] p;
    private int[] op;

    Circuit(int[] mas)
    {
        p = mas;
        op = new int[p.length];
        inverse();
    }

    /*Circuit(int[] mas1, int[] mas2) //вариант с циклами, не придумал как разделить на несколько циклов
    {
        p = new int[mas2.length];
        p[0] = mas1[0];
        for (int i = 1; i < p.length; i++)
        {
            p[i] = mas2[p[i - 1] - 1];
            for (int j = 0; j < i; j++)
                if (p[i] == p[j])
                    p[i] = i + 1;

        }
        length = p.length;
    }

    Circuit(int[] mas)
    {
        p = new int[mas.length];
        p[0] = 1;
        for (int i = 1; i < p.length; i++)
        {
            p[i] = mas[p[i - 1] - 1];
            for (int j = 0; j < i; j++)
                if (p[i] == p[j])
                    p[i] = i + 1;
        }
        length = p.length;
    }
    */

    int[] getP(){ return p; }
    int[] getOp(){ return op; }

    private void inverse()
    {
        for(int i = 0; i < p.length; i++)
        {
            for(int j = 0; j < p.length; j++)
            {
                if(p[j] == i + 1)
                {
                    op[i] = j + 1;
                }
            }
        }
    }

}