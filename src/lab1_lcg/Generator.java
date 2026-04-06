package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaly on 14.02.16.
 */
public class Generator {

    public static int[] mas;
    public static char[] bitmas;

    public static int countOdd8Bit = 0; // нечетные
    public static int countEven8Bit = 0;
    public static int countOdd1Bit= 0; // единицы
    public static int countEven1Bit = 0;


    private Generator(){}

    public static int[] createSet(int a, int c, int m, int x0){
        List<Integer> array = new ArrayList<>();
        List<String> arrayTo2 = new ArrayList<>();
        array.add(x0);
        arrayTo2.add(to2(x0));
        int x1;
        while(true) {
            //countBit1(arrayTo2); // мне так нравится больше, но пришлось добавить метод countOne
            countBit8(arrayTo2);
            x1 = (a * x0 + c) % m;
            if (array.contains(x1))
                break;
            array.add(x1);
            arrayTo2.add(to2(x1));
            countOdd1Bit += countOne(to2(x1));
            countEven1Bit += to2(x1).length() - countOne(to2(x1));
            x0 = x1;
        }
        int[] mas = new int[array.size()];
        for (int i = 0; i < mas.length; i++) {
            mas[i] = array.get(i);
        }
        Generator.mas = mas;
        Generator.bitmas = getBitSet(mas);

        return mas;
    }

    public static int countOne(String str){
        char[] mas = str.toCharArray();
        int result = 0;
        for (int i = 0; i < mas.length; i++) {
            if (mas[i] == '1')
                result++;
        }
        return result;
    }

    public static void countBit8(List<String> list){
        StringBuilder strings = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            strings.append(list.get(i));
        }
        List<String> bit8 = new ArrayList<>();
        while(true) {
            if (strings.length() < 8){
                int length = strings.length();
                StringBuilder result = strings.reverse();
                for (int j = 0; j < 8 - length; j++) {
                    result.append("0");
                }
                bit8.add(result.reverse().toString());
                break;
            }
            bit8.add(strings.substring(0, 8));
            strings = new StringBuilder(strings.substring(8));
        }

        int countOdd = 0;
        int countEven = 0;
        for (String s : bit8){
            if (s.substring(7).equals("1"))
                countOdd++;
            else
                countEven++;
        }
        countOdd8Bit = countOdd;
        countEven8Bit = countEven;
    }

    public static void countBit1(List<String> list){
        StringBuilder strings = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            strings.append(list.get(i));
        }
        char[] bits = strings.toString().toCharArray();

        int countOdd = 0;
        int countEven = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == '1')
                countOdd++;
            else
                countEven++;
        }
        countOdd1Bit = countOdd;
        countEven1Bit = countEven;
    }

    public static char[] getBitSet(int[] mas){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < mas.length; i++) {
            result.append(to2(mas[i]));
        }
        return result.toString().toCharArray();
    }

    public static String to2(int x){
        if (x == 0)
            return "0";
        StringBuilder result = new StringBuilder();
        int a = x;
        while(a != 0){
            int b = a%2;
            result.append(b);
            a = a/2;
        }
        return result.reverse().toString();
    }

}
