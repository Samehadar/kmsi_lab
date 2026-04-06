package main.com.gmail.vitaly.lyutarevich.lab2;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.MathContext;

/**
 * Created by vitaly on 27.09.15.
 */
public class Affine_cipher {
    public static void main(String[] args) throws IOException {
       // char[] alpha = new char[256];
       // for (int i = 0; i < alpha.length; i++)
       // {
        //    alpha[i] = (char)i;
       // }
        char[] alpha = "абвгдежзийклмнопрстуфхцчшщъыьэюя".toCharArray();
        Affine_cipher A = new Affine_cipher();

        System.out.println("Введите строку для шифрования: ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String str = bufferedReader.readLine();

        System.out.println("Введите а:");
        int a = Integer.parseInt(bufferedReader.readLine());
        int b = Integer.parseInt(bufferedReader.readLine());

        Key key = new Key(a, b);

        String res = A.encryption(alpha, str, key);
        System.out.println("Шифротекст: " + res );
        System.out.println("Открытый текст: " + A.decryption(alpha, res, key));

    }



    public String encryption(char[] alpha, String str, Key key)
    {
        String result = "";
        char[] current_str = str.toCharArray();

        for (int i = 0; i < current_str.length; i++)
        {
            if (current_str[i] < alpha.length && current_str[i] >= 0)
            {
                int number = (key.getA() * current_str[i] + key.getB()) % alpha.length;
                result += alpha[number];
            }
            else result += current_str[i];
        }

        return result;
    }

    public String decryption(char[] alpha, String str, Key key)
    {
        String result = "";
        char[] current_str = str.toCharArray();

        int inverse_key_a = multiplicative_inverse(key.getA(), alpha.length);

        for (int i = 0; i < current_str.length; i++)
        {
            if (current_str[i] < alpha.length && current_str[i] >= 0)
            {
                try
                {
                    int number = ((current_str[i] - key.getB()) * inverse_key_a) % alpha.length;
                    result += alpha[number];
                }
                catch (ArrayIndexOutOfBoundsException e) // если стало отрицательным после вычитания
                {
                    int number = ((current_str[i] - key.getB() + alpha.length) * inverse_key_a) % alpha.length;
                    result += alpha[number];
                }
            }
            else result += current_str[i];
        }

        return result;
    }

    public int multiplicative_inverse(int a, int m) // поиск обратного элемента
            // m - размер кольца (модуль кольца)
            // a - элемент, к которому ищем обратный в кольце по модулю m
    {

        MyStruct g = GCD(a, m);
        int x = g.getX();
        int y = g.getY();
        if (g.getD() != 1)
            throw new IllegalArgumentException();
        return (x % m + m) % m;
    }

    private MyStruct GCD(int a, int b) // алгоритм Евклида (находим последний ненулевой остаток
                                                // затем восстанавливаем уравнения до ax-by = 1
    {
        if (a == 0)
        {
            return new MyStruct(b, 0 , 1); // возвращаем последний ненулевой остаток
        }
        int x = 0;
        int y = 0;

        int x1 = 0;
        int y1 = 0;

        MyStruct A = GCD(b % a, a);
        int d = A.getD();
        x1 = A.getX();
        y1 = A.getY();

        x = y1 - (b / a) * x1;
        y = x1;

        return new MyStruct(d, x, y);
    }


}
class MyStruct
{
    private int d;
    private int x;
    private int y;

    MyStruct(int d, int x, int y)
    {
        this.d = d;
        this.x = x;
        this.y = y;
    }

    public void setD(int d)
    {
        this.d = d;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }

    public int getD()
    {
        return d;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
}

class Key
{
    private int a;
    private int b;

    Key(int a, int b)
    {
        this.a = a;
        this.b = b;
    }

    public void  setA(int a)
    {
        this.a = a;
    }
    public void setB(int b)
    {
        this.b = b;
    }

    public int getA()
    {
        return a;
    }
    public int getB()
    {
        return b;
    }
}