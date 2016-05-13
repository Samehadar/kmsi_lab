package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaly on 15.02.16.
 */
public abstract class Generator {

    public static byte F1;
    public static byte F2;
    public static byte F3;
    public static byte F4;
    public static byte F5;
    public static byte F6;
    public static byte F7;
    public static byte F8;

    public static byte[] F;

    public static short reg;
    public static short startReg;

    public static List<Byte> array = new ArrayList<>();
    public static List<Short> generateNumbers = new ArrayList<>();

    /**
     * Функция расчитывает новый бит
     * вход: текущая поседовательность битов
     * выход: новый бит
     * */
    public static byte getF(byte a0, byte a1, byte a2, byte a3, byte a4, byte a5, byte a6, byte a7){
        byte[] a = {a0, a1, a2, a3, a4, a5, a6, a7};
        byte[] F = {F1, F2, F3, F4, F5, F6, F7, F8};
        Generator.F = F;
        byte result = 0;
        for (int i = 0; i < F.length; i++) {
            if (F[i] != 0) {
                result = (byte)(a[i] * F[i]);
                F[i] = 0;
                break;
            }
        }
        for (int i = 0; i < F.length; i++) {
            if (F[i] != 0)
                result^= (byte)(a[i] * F[i]);
        }
        return result;
    }

    /*
    * Сдвигает reg на 1 бит вправо
    * выход: вытолкнутый бит
    * */
    public static byte shift() {
        byte oldBit = (byte) (reg % 2); //получили младший бит (гамму)
        int newBit = getF((byte) (reg & 1), (byte) ((reg & 2) / 2) , (byte) ((reg & 4) / 4), (byte) ((reg & 8) / 8),
                            (byte) ((reg & 16) / 16), (byte) ((reg & 32) / 32), (byte) ((reg & 64) / 64), (byte) ((reg & 128) / 128)); //расчет нового бита
        reg = (short)(reg / 2); //сдвинули все на 1 бит вправо
        if (newBit == 1) {
            reg = (short) ((reg & 127) | 128); //Пример: (X1110101 & 01111111) | 10000000 = 11110101
        } else {
            if ((reg & 128) == 128)
                reg = (short) ((reg & 127)); //Пример: (11110101 & 01111111) = 01110101
        }
        return oldBit;
    }

    /*
    * Получает двоичный вид числа
    * вход: число
    * выход: число в доичной СИ в форме строки
    * */
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

    /*
    * Создаем последовательность чисел регистра(нет повторений)
    * maxLength - максимально возможнная длина уникальной последовательности для регистра в 8 бит
    * */
    public static void createSet(){
        int maxLength = 2060;
        array = new ArrayList<>();
        generateNumbers = new ArrayList<>();
        generateNumbers.add(startReg);
        for (int i = 0; i < maxLength; i ++) {
            array.add(shift());
            if(findCycle())
                break;
            else
                generateNumbers.add(reg);
        }
    }

    /**
    * Создает последовательность чисел регистра(повторения позможны)
    * вход: необходимая длина
    *
    */
    public static void createSet(int length){
        array = new ArrayList<>();
        generateNumbers = new ArrayList<>();
        generateNumbers.add(startReg);
        for (int i = 0; i < length; i ++) {
            array.add(shift());
            generateNumbers.add(reg);
        }
    }


    /*
    * Найти цикл: смотрит в сгенерированных числах числа регистра текущее содержимое регистра
    * */
    public static boolean findCycle(){
        if (generateNumbers.contains(reg))
            return true;
        return false;
    }

}
