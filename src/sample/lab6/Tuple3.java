package sample.lab6;

/**
 * Created by Vitaly on 03.04.2016.
 */
public class Tuple3<T1,T2,T3> {

    public T1 x;
    public T2 y;
    public T3 d;

    Tuple3(T1 key1, T2 key2, T3 key3){
        this.x = key1;
        this.y = key2;
        this.d = key3;
    }

}
