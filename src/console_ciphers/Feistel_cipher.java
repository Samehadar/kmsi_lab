package main.com.gmail.vitaly.lyutarevich.lab8;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by vitaly on 24.12.15.
 */
public class Feistel_cipher {

    public static void main(String[] args) throws FileNotFoundException, IOException
    {

        byte[] mas = readFromFile("text.txt");
        String result = rebuild(mas, (byte)4, true);
        System.out.println(result);
        writeToFile("end.txt", result);

        byte[] mas2 = readFromFile("end.txt");
        String start = rebuild(mas2, (byte)4, false);
        writeToFile("text.txt", start.substring(0, result.length()-2));



    }

    public static String rebuild(byte[] s, byte key, boolean ch)
    {
        int end = s.length % 2;
        byte R0, L0, L1, R1;
        boolean full = end == 0 ? true : false;

        byte x1 = 0, x2 = 0;
        byte a = 6, b = 7;



        if(!full)
        {
            byte[] temp = new byte[s.length + 1];
            for(int i = 0; i < s.length; i++)
                temp[i] = s[i];
            temp[s.length] = 32; //32 это пробел
            s = temp;
        }

//пробег по блокам
//ключ в раунде один и тот же
//шифрование = дешифрованию, т.к. ключ не менялся(в дешифровке они просто идут наоборот)

        for (int i = 0; i < s.length; i += 2)
        {
            if (ch)
            {
                L0 = s[i];
                R0 = s[i+1];
            }
            else
            {
                L0 = s[i+1];
                R0 = s[i];
            }

            L1 = R0;

            for (int j = 0; j < 3; j++)
            {
                R1 = (byte)(L0 ^ (L1 << a) ^ (L1 << b) ^ key);
                if (ch)
                {
                    x1 = L1;
                    x2 = R1;
                }
                else
                {
                    x2 = L1;
                    x1 = R1;
                }
                L0 = L1;
                L1 = R1;
            }
            s[i] = x1;
            s[i + 1] = x2;
        }
        String str = new String(s, Charset.defaultCharset());
        //return s.toString();
        return str;
    }

    public static byte[] readFromFile(String file) throws IOException
    {
        char[] buffer;
        String result;
        File f=new File(file);
        FileReader reader = new FileReader(f);

        buffer = new char[(int)f.length()];
        // считаем файл полностью
        reader.read(buffer);
        result = new String(buffer);
        System.out.println(result);
        //byte[] array = DatatypeConverter.parseBase64Binary(new String(buffer));
        byte[] temp = result.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < temp.length; i++)
            System.out.println(temp[i]);
        return temp;
    }

    public static void writeToFile(String fileName, String text) {
        //Определяем файл
        File file = new File(fileName);

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(text);
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }




}
