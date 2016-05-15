package sample.lab6;

import sample.CRChash;

import java.util.List;
import java.util.Map;

public class ElgamalSubscribe {

    private ElgamalShema elgamalShema;
    private CRChash myHash;

    private String parentFunc;  //for CRC

    public Map<String, Integer> openKey;
    public Integer closeKey;


    public ElgamalSubscribe(Integer p, Integer g, Integer x, String parentFunc){
        elgamalShema = new ElgamalShema(p, g, x);
        this.parentFunc = parentFunc;
        openKey = elgamalShema.getOpenKey();
        closeKey = elgamalShema.getCloseKey();
    }

    /*
    Подпись сообщений
Для подписи сообщения ~M выполняются следующие операции:

Вычисляется дайджест сообщения ~M: ~m = h(M).
Выбирается случайное число ~1< k < p-1 взаимно простое с ~p - 1 и вычисляется ~r = g^k\,\bmod\,p.
Вычисляется число  s \, \equiv \, (m-x r)k^{-1} \pmod{p-1}.
Подписью сообщения ~M является пара \left( r,s \right).
     */
    public Tuple3<String, Integer, Integer> subscribeMessage(String subscribingMessage){
        //m  = h(M)
        Integer messageDigest = getMessageDigest(subscribingMessage);

        //1 < k < p - 1
        Integer k;
        do {
            k = (int) (Math.random() * (openKey.get("p") - 1)) + 1;
        } while (gcd(k, openKey.get("p") - 1) != 1);

        // r = g^k mod p
        Integer r = elgamalShema.exp(openKey.get("g"), k, openKey.get("p"));

        //k^(-1)
        Integer kInvert = gcdExtended(openKey.get("p") - 1, k, new Tuple3<>(0,0,0)).y;

        //s=(m-x*r)*(k^(-1)) mod (p-1)
        Integer s = ((messageDigest - closeKey * r) * kInvert) % (openKey.get("p") - 1);

        //sub is (r, s)
        return new Tuple3<>(subscribingMessage, r, s);
    }

/*
Проверка подписи
Зная открытый ключ \left( p,g,y \right), подпись \left( r,s \right) сообщения ~M проверяется следующим образом:

Проверяется выполнимость условий: ~0<r<p и ~0<s<p-1. Если хотя бы одно из них не выполняется,то подпись считается неверной.
Вычисляется дайджест ~m = h(M).
Подпись считается верной, если выполняется сравнение:
~(y^r r^s)mod{p} \equiv g^m \pmod{p}.
 */
    /*
    Method is checked received message
    @param checkedMessage - checked message, r, s
    @return - true or false
     */
    public Boolean checkSubscribingMessage(Tuple3<String, Integer, Integer> checkedMessage){
        if (checkedMessage.y <= 0 || checkedMessage.y >= openKey.get("p"))
            return false;
        if (checkedMessage.d <= 0 || checkedMessage.d >= openKey.get("p") - 1)
            return false;

        Integer messageDigest = getMessageDigest(checkedMessage.x);

        Integer left = elgamalShema.exp(
                elgamalShema.exp(openKey.get("y"), checkedMessage.y, openKey.get("p"))
                * elgamalShema.exp(checkedMessage.y, checkedMessage.d, openKey.get("p"))
                , 1, openKey.get("p"));
        Integer right = elgamalShema.exp(openKey.get("g"), messageDigest, openKey.get("p"));

        return left.equals(right);
    }

    public Integer getMessageDigest(String message){
        myHash = new CRChash();
        myHash.setParentFunc(parentFunc);
        myHash.setMessage(message);
        String hash = myHash.getCRC();
        return Integer.parseInt(hash, 2);
    }

    //НОД(a, b)
    public int gcd(int a, int b) throws IllegalArgumentException {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    //work with d * d^(-1) = 1 (mod m) to find d^(-1): y = d => b = d^(-1)
    //ax+by - for x,y return (a, b, GCD(x,y)); p.s. b < 0
    public Tuple3<Integer, Integer, Integer> gcdExtended(int a, int b, Tuple3<Integer, Integer, Integer> tuple) {
        if (a == 0) {
            return new Tuple3<>(0, 1, b);
        }
        Tuple3<Integer, Integer, Integer> tuple1 = gcdExtended(b%a, a, new Tuple3<>(0, 0, 0));
        tuple.x = tuple1.y - (b / a) * tuple1.x;
        tuple.y = tuple1.x;
        tuple.d = tuple1.d;
        return tuple;
    }

}
