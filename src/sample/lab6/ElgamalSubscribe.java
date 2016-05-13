package sample.lab6;

import sample.CRChash;

import java.util.List;
import java.util.Map;

/**
 * Created by Vitaly on 03.05.2016.
 */
public class ElgamalSubscribe {

    private ElgamalShema elgamalShema;
    private CRChash myHash;

    private String message;
    private String parentFunc;  //for CRC

    public Map<String, Integer> openKey;
    public Integer closeKey;


    public ElgamalSubscribe(){

    }

    public void subscribeMessage(){
        //m  = h(M)
        Integer messageDigest = getMessageDigest(message);

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
    }


    public void checkSubscribingMessage(String checkedMessage){

    }

    public Integer getMessageDigest(String message){
        myHash = new CRChash(null);
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
            return new Tuple3<Integer, Integer, Integer>(0, 1, b);
        }
        Tuple3<Integer, Integer, Integer> tuple1 = gcdExtended(b%a, a, new Tuple3<>(0, 0, 0));
        tuple.x = tuple1.y - (b / a) * tuple1.x;
        tuple.y = tuple1.x;
        tuple.d = tuple1.d;
        return tuple;
    }

}
