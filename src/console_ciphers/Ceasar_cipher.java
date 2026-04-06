package main.com.gmail.vitaly.lyutarevich.lab1;

import java.io.*;

/**
 * Created by vitaly on 20.09.15.
 */
public class Ceasar_cipher {
    public static void main(String[] args) throws IOException
    {
        char[] alfavit = "абвгдежзийклмнопрстуфхцчшщъыьэюя".toCharArray();
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        char[] str;

        while(true) {
        System.out.println("Выберите нужное действие: \n" +
                "1 - Шифровать текст.\n2 - Расшифровать текст.\n" +
                "347 - Выход.");
        int a = Integer.parseInt(bufferedReader.readLine());


            switch (a) {
                case 1:

                    System.out.println("Введите текст:");
                    str = bufferedReader.readLine().toCharArray();


                    //str = "етхйкфзвсзнюукпэгрщмвок".toCharArray();
                    //System.out.println(str);
                    String result = "";
                    System.out.println("Введите ключ");
                    int key = Integer.parseInt(bufferedReader.readLine());

                    for (int i = 0; i < str.length; i++) {
                        int currentResultLegth = result.length();
                        for (int j = 0; j < alfavit.length; j++) {
                            if (str[i] == alfavit[j])
                                result += alfavit[(j + key) % alfavit.length];
                        }
                        if (currentResultLegth == result.length())
                            result += str[i];
                    }
                    System.out.println(result);

                    break;
                case 2:

                    System.out.println("Введите текст:");
                    str = bufferedReader.readLine().toCharArray();

                    //char[] str = "етхйкфзвсзнюукпэгрщмвок".toCharArray();
                    //System.out.println(str);
                    result = "";
                    System.out.println("Введите ключ:");
                    key = Integer.parseInt(bufferedReader.readLine());

                    for (int i = 0; i < str.length; i++) {
                        int currentResultLegth = result.length();
                        for (int j = 0; j < alfavit.length; j++) {
                            if (str[i] == alfavit[j]) {
                                try {
                                    result += alfavit[(j - key) % alfavit.length];
                                }
                                catch (ArrayIndexOutOfBoundsException e)
                                {
                                    result += alfavit[(j - key + alfavit.length) % alfavit.length];
                                }
                            }
                        }
                        if (currentResultLegth == result.length())
                            result += str[i];
                    }
                    System.out.println(result);
                    break;
                default:
                    return;
            }
        }

    }


}
