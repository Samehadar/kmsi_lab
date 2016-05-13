package sample.lab6;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vitaly on 03.05.2016.
 */
public class ElgamalShema {

    public Integer p;
    public Integer g;
    public Integer y;

    private Integer x;

    public ElgamalShema(Integer p, Integer g, Integer x){
        this.p = p;
        this.g = g;
        this.x = x;
        this.y = exp(g, x, p);
    }

    //e^x mod m
    public Integer exp(Integer e, Integer x, Integer m) {
        BigInteger result = new BigInteger(e.toString());
        result = result.modPow(new BigInteger(x.toString()), new BigInteger(m + ""));

        return result.intValue();
    }

    //Return (p, g, y) - open key
    public Map<String, Integer> getOpenKey(){
        Map<String, Integer> returnedList = new HashMap<>();
        returnedList.put("p", p);
        returnedList.put("g", g);
        returnedList.put("y", y);
        return returnedList;
    }

    public Integer getCloseKey(){
        return x;
    }

}
