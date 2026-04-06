package sample;

/**
 * Created by Vitaly on 03.04.2016.
 */
public abstract class Cipher {

    public abstract void encryption();

    public abstract void decryption();

    public abstract void setOpenText(String text);

    public abstract void setCloseText(String text);
}
