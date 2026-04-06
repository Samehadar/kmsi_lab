package main.com.gmail.vitaly.lyutarevich.lab9;

import java.util.ArrayList;

/**
 * Created by vitaly on 25.12.15.
 */
public class OpenText_Model
{
    public static void main(String[] args)
    {
        Analyse(1);
    }

    public static String rebuildString(String str)
    {
        String result;
        result = str.toLowerCase().replace(" ","");
        result = result.replace("!", "");
        result = result.replace(".","");
        result = result.replace(",","");
        result = result.replace("-","");
        result = result.replace("?","");

        System.out.println(result);
        return result;
    }

    public static int[] count(String str, int key)
    {

            char[] ch = str.toCharArray();
            int[] res = new int[33];
            int k = 0;
            for (int i = 'а'; i <= 'я'; i++) {
                for (int j = 0; j < ch.length; j++) {
                    if (ch[j] == i)
                        res[k]++;
                }
                k++;
            }
            return res;
    }

    public static void Analyse(int src)
    {
        char[] template = new char[33];
        for (char i = 'а'; i <= 'я'; i ++)
            template[i -'а'] = i;
        int[] count = new int[32];
        char[] s = ("Мильоны - вас. Нас - тьмы, и тьмы, и тьмы. " +
                "Попробуйте, сразитесь с нами! Да, скифы - мы! " +
                "Да, азиаты - мы, С раскосыми и жадными очами! Для вас - века, для нас - единый час. " +
                "Мы, как послушные холопы, Держали щит меж двух враждебных рас Монголов и Европы!").toLowerCase().toCharArray();
        StringBuilder str1 = new StringBuilder("");

        for (int i = 0; i < s.length; i++)
        {
            if (s[i] >= 'а' && s[i] <= 'я')
            {
                str1.append(s[i]);
            }
        }

        String str = str1.toString();
        System.out.println(str);
        String result = "";
        double[] Hk_k = new double[5];
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Double> p_x = new ArrayList<Double>();
        boolean add;

        System.out.println();
        for (int k = 1; k < 6; k++)
        {
            for (int i = 0; i < str.length() - k; i++)
            {
                add = true;
                for (int j = 0; j < x.size(); j++)
                {
                    if (str.substring(i, k).equals(x.get(j)))
                    {
                        add = false;
                        Double a = p_x.get(j);
                        p_x.remove(j);
                        a++;
                        p_x.set(j, a);
                        break;
                    }
                }

                if (add)
                {
                    x.add(str.substring(i, k));
                    p_x.add((double)1);
                }

            }

            for (int i = 0; i < x.size(); i++)
            {
                Double a = p_x.get(i);
                p_x.remove(i);
                a /= str.length()-k-1;
                p_x.set(i, a);
                Hk_k[k - 1] += p_x.get(i) * Math.log(p_x.get(i));
            }
            Hk_k[k - 1] /= k;
            result += k + " " + Hk_k[k - 1] + "\r\n";
            System.out.println("k:" + k + " Hk/k:" + Hk_k[k - 1]);
            x.clear();
            p_x.clear();
        }
    }

}
