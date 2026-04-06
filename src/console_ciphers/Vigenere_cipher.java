package main.com.gmail.vitaly.lyutarevich.lab3;

import java.io.*;

/**
 * Created by vitaly on 01.10.15.
 */
public class Vigenere_cipher {

    public static void main(String[] args) throws IOException
    {
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        char[] alpha = "абвгдежзийклмнопрстуфхцчшщъыьэюя".toCharArray();

        Vigenere_cipher.menu(alpha);
    }

    public static StringBuilder encryption(char[] alpha, int[] key, String str)
    {
        char[] _str = str.toCharArray();

        StringBuilder result = new StringBuilder("");

        for (int i = 0; i < _str.length; i++)
        {
            int count = 0;
            for (int j = 0; j < alpha.length; j++)
                if (_str[i] == alpha[j])
                {
                    count = 1;
                    int new_number = (j + key[i % key.length]) % alpha.length;
                    result.append(alpha[new_number]);
                }
            if (count == 0)
                result.append(_str[i]);
        }

        return result;
    }

    public static StringBuilder decryption(char[] alpha, int[] key, String str)
    {
        char[] _str = str.toCharArray();

        StringBuilder result = new StringBuilder("");

        for (int i = 0; i < _str.length; i++)
        {
            int count = 0;
            for (int j = 0; j < alpha.length; j++)
                if (_str[i] == alpha[j])
                {
                    count = 1;
                    int new_number = (j - key[i % key.length]) % alpha.length;
                    if (new_number < 0)
                        new_number = (j - key[i % key.length] + alpha.length) % alpha.length;
                    result.append(alpha[new_number]);
                }
            if (count == 0)
                result.append(_str[i]);
        }

        return result;
    }

    public static void menu(char[] alpha) throws IOException
    {
        while(true)
        {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Введите ваш текст: ");
            String text = buffer.readLine();
            System.out.println("Введите ключи через запятую: ");
            String keys = buffer.readLine();
            String[] keys_mas = keys.split(",");
            int[] key = new int[keys_mas.length];
            for (int i = 0; i < key.length; i++)
                key[i] = Integer.parseInt(keys_mas[i]);
            System.out.println("Выберите нужное действие: \n" +
                    "1 - Шифровать текст.\n2 - Расшифровать текст.\n" +
                    "347 - Выход.");
            int a = Integer.parseInt(buffer.readLine());

            switch (a)
            {
                case 1:
                    StringBuilder cipher_text = encryption(alpha, key, text);
                    System.out.println("Шифротекст: " + cipher_text);
                    break;
                case 2:
                    StringBuilder open_text = decryption(alpha, key, text);
                    System.out.println("Результат дешифрования: " + open_text);
                    break;
                default:
                    return;
            }
        }
    }


}


